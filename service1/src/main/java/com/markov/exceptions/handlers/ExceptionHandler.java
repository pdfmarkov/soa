package com.markov.exceptions.handlers;

import javax.inject.Singleton;

import com.markov.entities.dto.ErrorDto;
import com.markov.exceptions.BadRequestException;
import com.markov.exceptions.NoSuchWorkerException;
import com.sun.media.sound.InvalidFormatException;


import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
@Singleton
public class ExceptionHandler implements ExceptionMapper<Exception> {
    private final Map<Class<?>, Integer> handledErrors = new HashMap<>();

    public ExceptionHandler() {
        handledErrors.put(NoResultException.class, HttpServletResponse.SC_NOT_FOUND);
        handledErrors.put(NoSuchWorkerException.class, HttpServletResponse.SC_NOT_FOUND);
        handledErrors.put(BadRequestException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(NoSuchFieldException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(ConstraintViolationException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(IllegalArgumentException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(NotFoundException.class, HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(Exception exception) {
        Response.ResponseBuilder resp = Response.serverError().type(MediaType.APPLICATION_JSON);
        System.out.printf("GOT ERROR TO HANDLE %s%n", exception);
        if (handledErrors.containsKey(exception.getClass())) {
            resp = resp.status(handledErrors.get(exception.getClass()));
        } else if (exception.getClass().equals(PersistenceException.class)) {
            PersistenceException ex = (PersistenceException) exception;
            if (ex.getMessage().contains("ConstraintViolationException")) {
                resp = resp.status(HttpServletResponse.SC_BAD_REQUEST);
                if (ex.getCause().getCause().getMessage().contains(" is still referenced"))
                    return resp.entity(ErrorDto.builder()
                                    .error(exception.getClass().getSimpleName())
                                    .message(ex.getCause().getCause().getMessage()
                                            + "You should consider removing dependent objects first.")
                                    .build())
                            .build();
            }
        }

        if (exception.getClass().equals(ConstraintViolationException.class)) {
            ConstraintViolationException ex = (ConstraintViolationException) exception;

            StringBuilder commonErrorMessage = new StringBuilder();
            for (ConstraintViolation<?> constraint : ex.getConstraintViolations()) {
                commonErrorMessage.append(constraint.getPropertyPath()).append(" ")
                        .append(constraint.getMessage()).append("\n");
            }

            return resp.entity(ErrorDto.builder()
                            .error(exception.getClass().getSimpleName())
                            .message(commonErrorMessage.toString())
                            .build())
                    .build();
        }

        resp.entity(ErrorDto.builder().error(exception.getClass().getSimpleName())
                .message(exception.getMessage()).build());

        return resp.build();
    }
}
