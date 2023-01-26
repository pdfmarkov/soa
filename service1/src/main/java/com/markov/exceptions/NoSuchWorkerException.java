package com.markov.exceptions;

public class NoSuchWorkerException extends Exception{
    public NoSuchWorkerException(String id){
        super("There are no user with id = " + id + "!");
    }
}
