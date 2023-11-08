package com.recycle.recycle.infra;

public class EntityNotFoundExceptions extends RuntimeException{
    public EntityNotFoundExceptions(String message){
        super(message);
    }

}
