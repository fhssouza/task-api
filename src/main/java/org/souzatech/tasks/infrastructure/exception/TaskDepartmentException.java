package org.souzatech.tasks.infrastructure.exception;

public class TaskDepartmentException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public TaskDepartmentException(String message){
        super(message);
    }

}
