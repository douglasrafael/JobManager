package com.fsdeveloper.jobmanager.dao;

import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.util.List;

/**
 * Interface that ensures the contract with the classes that the implement.
 * Implementation of crud methods and other essential becomes obligatory.
 *
 * @author Created by Douglas Rafael on 22/04/2016.
 * @version 1.0
 */
public interface Dao<T> {

    /**
     * Returns a list containing the objects of the agreement with the user id.
     *
     * @param id_user The user id
     * @return The list containing the objects.
     * @throws JobManagerException If there is an exception.
     */
    public List<T> list(int id_user) throws JobManagerException;

    /**
     * Return object according to your id.
     *
     * @param _id The id
     * @return The object
     * @throws JobManagerException If there is an exception.
     */
    public T getById(int _id) throws JobManagerException;

    /**
     * Saves an object.
     *
     * @param o The object to be saved.
     * @return True if inserted and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean insert(T o) throws JobManagerException;

    /**
     * Upgrades a specific object.
     *
     * @param o The object to be upgraded.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean update(T o) throws JobManagerException;

    /**
     * Removes a specific object.
     *
     * @param o The object to be removed.
     * @return True if removed and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean delete(T o) throws JobManagerException;

    /**
     * Search for objects in accordance with the string and user id passed as a parameter.
     * A list of objects found is returned. If not found no corresponding object the string, the list will be returned empty.
     *
     * @param s The string to be searched.
     * @param id_user The user id
     * @return The object list.
     * @throws JobManagerException If there is an exception.
     */
    public List<T> search_all(String s, int id_user) throws JobManagerException;

}