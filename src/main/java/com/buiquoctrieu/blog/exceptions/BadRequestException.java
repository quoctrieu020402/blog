package com.buiquoctrieu.blog.exceptions;

public class BadRequestException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public BadRequestException(String resourceName,String fieldName, String fieldValue){
        super(String.format("%s exist with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
