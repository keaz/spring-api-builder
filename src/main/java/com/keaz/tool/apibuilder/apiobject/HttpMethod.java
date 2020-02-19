package com.keaz.tool.apibuilder.apiobject;

public enum HttpMethod {

    GET("@GetMapping"),POST("@PostMapping"),PUT("@PutMapping"),
    DELETE("@DeleteMapping");

    private final String springAnnotation;

    HttpMethod(String springAnnotation){
        this.springAnnotation = springAnnotation;
    }

    @Override
    public String toString() {
        return springAnnotation;
    }
}
