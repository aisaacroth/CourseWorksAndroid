package edu.columbia.cuit.courseworks.exceptions;

import java.io.IOException;

/**
 * Wrapper Exception for exceptions from the Reconstructor and Requester classes.
 * 
 * @author Alexander Roth
 * @date 2014-08-14
 */
public class FailedConnectionException extends IOException {

    private static final long serialVersionUID = -4192353585122771997L;

    public FailedConnectionException() {
        super();
    }
    
    public FailedConnectionException(String message) {
        super(message);

    }

}