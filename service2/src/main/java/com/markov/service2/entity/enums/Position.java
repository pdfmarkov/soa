package com.markov.service2.entity.enums;

public enum Position {
    CLEANER("cleaner"),
    DEVELOPER("developer"),
    HUMAN_RESOURCES("human_resources"),
    LABORER("laborer"),
    LEAD_DEVELOPER("lead_developer");

    String position;

    Position(String position){
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

}
