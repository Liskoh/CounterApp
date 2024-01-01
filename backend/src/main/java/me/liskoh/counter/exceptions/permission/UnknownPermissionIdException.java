package me.liskoh.counter.exceptions.permission;

public class UnknownPermissionIdException extends RuntimeException {

    public UnknownPermissionIdException(int id) {
        super("Unknown permission with id " + id);
    }
    
}
