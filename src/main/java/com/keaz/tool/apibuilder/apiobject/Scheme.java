package com.keaz.tool.apibuilder.apiobject;

public enum Scheme {

    http("http"),https("https");

    private final String springAnnotation;

    Scheme(String springAnnotation){
        this.springAnnotation = springAnnotation;
    }

    @Override
    public String toString() {
        return springAnnotation;
    }
}
