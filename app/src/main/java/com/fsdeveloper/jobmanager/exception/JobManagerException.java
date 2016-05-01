package com.fsdeveloper.jobmanager.exception;

/**
 * Class to manage overall system exceptions.
 *
 * @author Created by Douglas Rafael on 22/04/2016.
 * @version 1.0
 */
public class JobManagerException extends Exception {

    /**
     * Class constructor method that receives an error message as a parameter and passes for the super class.
     *
     * @param message The message of error.
     */
    public JobManagerException(String message) {
        super(message);
    }
}