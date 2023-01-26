package com.markov.entities.enums;

public enum EyeColor {
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow");

    String eyeColor;

    EyeColor(String position){
        this.eyeColor = position;
    }

    public String getEyeColor() {
        return eyeColor;
    }
}
