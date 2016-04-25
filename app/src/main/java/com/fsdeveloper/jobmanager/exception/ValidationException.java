package com.fsdeveloper.jobmanager.exception;

/**
 *
 * @author Created by Douglas Rafael on 22/04/2016.
 * @version 1.0
 */
public class ValidationException extends Exception {

    /**
     * Class constructor method that receives an error message as a parameter and passes for the super class.
     *
     * @param message The message of error.
     */
    public ValidationException(String message) {
        super(message);
    }
}
