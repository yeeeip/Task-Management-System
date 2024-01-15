package com.nuzhd.taskmanagementsystem.exception;

public class NotEnoughPermissionsException extends RuntimeException{

    public NotEnoughPermissionsException(String message) {
        super(message);
    }
}
