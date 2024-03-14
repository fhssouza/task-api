package org.souzatech.tasks.infrastructure.exception;

public class StatusTaskException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public StatusTaskException(String message){
        super(message);
    }

}
