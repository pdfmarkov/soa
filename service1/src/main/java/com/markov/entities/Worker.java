package com.markov.entities;

import com.markov.entities.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "worker")
public class Worker extends AbstractEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToOne(optional = false, cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @OneToOne(optional = false, cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "start_date")
    private ZonedDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;
    @Column(name = "salary")
    private Long salary;

    public Worker(String name, Coordinates coordinates, Person person, Date creationDate,
                  ZonedDateTime startDate, LocalDateTime endDate, Position position) {
        this.name = name;
        this.coordinates = coordinates;
        this.person = person;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
    }
}
