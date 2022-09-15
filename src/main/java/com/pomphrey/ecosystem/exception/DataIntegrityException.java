package com.pomphrey.ecosystem.exception;

public class DataIntegrityException extends Exception{

    String message;

    public DataIntegrityException(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return message;
    }

}
