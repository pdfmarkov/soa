package com.markov.entities.enums;

public enum SortingType {
    id("id"),
    name("name"),
    coordinatesX("coordinates.x"),
    coordinatesY("coordinates.y"),
    creationDate("creationDate"),
    salary("salary"),
    startDate("startDate"),
    endDate("endDate"),
    position("position"),
    personHeight("person.height"),
    personPassportID("person.passportID"),
    personLocationX("person.location.x"),
    personLocationY("person.location.y"),
    personLocationZ("person.location.z"),
    personLocationName("person.location.name"),
    personEyeColor("person.eyeColor");

    private String sortingType;

    SortingType(String sortingType){
        this.sortingType = sortingType;
    }

    public String getSortingType() {
        return sortingType;
    }
}
