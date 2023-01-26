package com.markov.repositories.impl;

import com.markov.entities.Organization;
import com.markov.entities.Worker;
import com.markov.entities.enums.Position;
import com.markov.entities.enums.SortingType;
import com.markov.exceptions.BadRequestException;
import com.markov.exceptions.NoSuchWorkerException;
import com.markov.repositories.WorkerRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.markov.configuration.HibernateSessionFactoryConfig.getSessionFactory;

@Stateless
public class WorkerRepositoryImpl implements WorkerRepository {

    private String hQLQueryText = "";

    @Override
    public Worker save(Worker worker) throws NoSuchWorkerException {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
            Root<Organization> root = criteriaQuery.from(Organization.class);
            criteriaQuery.select(root);
            if (worker.getOrganization() == null) throw new NoSuchWorkerException("Organization is necessary param to fill");
            criteriaQuery.where(criteriaBuilder.equal(root.get("name"), worker.getOrganization().getName()));
            Organization organization = null;
            try {
                organization = session.createQuery(criteriaQuery).getSingleResult();
            } catch (NoResultException ignored) {
            }
            if (organization == null)
                organization = session.get(Organization.class, session.save(worker.getOrganization()));
            worker.setOrganization(organization);
            Integer workerId = (Integer) session.save(worker);
            Worker savedWorker = session.get(Worker.class, workerId);
            if (savedWorker == null)
                throw new NoSuchWorkerException(String.valueOf(workerId));
            transaction.commit();
            return savedWorker;
        }
    }

    @Override
    public Worker get(Integer id) throws NoSuchWorkerException {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Worker worker = session.get(Worker.class, id);
            if (worker == null) {
                session.close();
                throw new NoSuchWorkerException(String.valueOf(id));
            }
            transaction.commit();
            return worker;
        }
    }

    @Override
    public Worker update(Worker worker) throws NoSuchWorkerException {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
            Root<Organization> root = criteriaQuery.from(Organization.class);
            criteriaQuery.select(root);
            if (worker.getOrganization() != null)
                criteriaQuery.where(criteriaBuilder.equal(root.get("id"), worker.getOrganization().getId()));
            else criteriaQuery.where(criteriaBuilder.equal(root.get("name"), worker.getOrganization().getName()));
            Organization organization = session.createQuery(criteriaQuery).getSingleResult();
            if (organization == null)
                organization = session.get(Organization.class, session.save(worker.getOrganization()));
            worker.setOrganization(organization);
            Worker updatedWorker = session.get(Worker.class, worker.getId());
            if (updatedWorker == null)
                throw new NoSuchWorkerException(String.valueOf(worker.getId()));
            worker.setCreationDate(updatedWorker.getCreationDate());
            worker.getCoordinates().setId(updatedWorker.getCoordinates().getId());
            worker.getPerson().setId(updatedWorker.getPerson().getId());
            if (updatedWorker.getPerson().getLocation() != null)
                worker.getPerson().getLocation().setId(updatedWorker.getPerson().getLocation().getId());
            session.merge(worker);
            updatedWorker = session.get(Worker.class, worker.getId());
            transaction.commit();
            return updatedWorker;
        }
    }

    @Override
    public void delete(Integer id) throws NoSuchWorkerException {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Worker deletedWorker = session.get(Worker.class, id);
            if (deletedWorker == null)
                throw new NoSuchWorkerException(String.valueOf(id));
            session.remove(deletedWorker);
            transaction.commit();
        }
    }


    @Override
    public Long getNumberOfWorkersWithBiggerEndDate(String endDate) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Worker> root = criteriaQuery.from(Worker.class);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDateTime.parse(endDate)));
            criteriaQuery.select(criteriaBuilder.count(root));
            criteriaQuery.where(predicates.toArray(new Predicate[]{}));
            return session.createQuery(criteriaQuery).getSingleResult();
        }
    }

    @Override
    public Long getNumberOfWorkersWithSmallerPosition(String position) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Worker> root = criteriaQuery.from(Worker.class);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("position"), Position.valueOf(position)));
            criteriaQuery.select(criteriaBuilder.count(root));
            criteriaQuery.where(predicates.toArray(new Predicate[]{}));
            return session.createQuery(criteriaQuery).getSingleResult();
        }
    }

    @Override
    public List<Worker> getWorkersWithNameContains(String name) throws BadRequestException {
        return getWithSortAndFilter(Collections.singletonList("name =" + name), null, Integer.MAX_VALUE, 0);
    }

    @Override
    public List<Worker> getWithSortAndFilter(List<String> filterParams, String sortParam, int itemsPerPage, int pageNumber) throws BadRequestException {
        List<Worker> resultList;
        hQLQueryText = "FROM Worker w";

        if (filterParams.size() > 0)
            addFilterParams(filterParams);
        if (sortParam!= null) {
            addSortParams(sortParam);
        }

        resultList = makeHQLQuery(hQLQueryText, itemsPerPage, pageNumber);

        return resultList;
    }

    private void addSortParams(String sortParam) {
        if (sortParam.startsWith("-"))
            sortParam = sortParam.substring(1) + " DESC";
        hQLQueryText += " ORDER BY ";
        hQLQueryText += "w." +sortParam + " ";
    }

    private void addFilterParams(List<String> filterParams) {

        hQLQueryText += " WHERE ";
        String AND = "";
        for (int i = 0; i  < filterParams.size(); i++){
            hQLQueryText += AND + " w." +
                    filterParams.get(i).substring(0, filterParams.get(i).indexOf("=")) + "='" +
                    filterParams.get(i).substring(filterParams.get(i).indexOf("=") + 1) + "'";
            if (i != filterParams.size() - 1) AND = " AND ";
            else AND = "";
        }
    }


    public List<Worker> makeHQLQuery(String queryTxt, int itemsPerPage, int pageNumber){
        Session session = getSessionFactory().openSession();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        List<Worker> workers = em.createQuery(queryTxt)
                .setFirstResult(itemsPerPage * pageNumber)
                .setMaxResults(itemsPerPage)
                .getResultList();
        session.close();
        return workers;
    }
}
