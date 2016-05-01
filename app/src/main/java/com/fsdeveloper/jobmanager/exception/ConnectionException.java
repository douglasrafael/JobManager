package com.fsdeveloper.jobmanager.exception;

/**
 * Class to manage exceptions type of connection of the database.
 *
 * @author Created by Douglas Rafael on 01/05/2016.
 * @version 1.0
 */
public class ConnectionException extends Exception {
    /**
     * Class constructor method that receives an error message as a parameter and passes for the super class.
     *
     * @param message The message of error.
     */
    public ConnectionException(String message) {
        super(message);
    }
}
