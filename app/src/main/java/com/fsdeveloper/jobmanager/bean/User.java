package com.fsdeveloper.jobmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * The User class represents all objects of type User.
 * All objects of type User are implemented as instances of this class.
 *
 * @author Douglas Rafael
 * @version 1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = 8165444006888555412L;

    private int id;
    private String name;
    private String email;
    private String password;
    private String created_at;
    private String last_login;

    /**
     * User class constructor.
     *
     * @param id         The id of user.
     * @param name       The name of user.
     * @param email      The email of user.
     * @param password   The password of user.
     * @param created_at The data and time created of user.
     */
    public User(int id, String name, String email, String password, String created_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
    }

    /**
     * User class constructor.
     *
     * @param name       The name of user.
     * @param email      The email of user.
     * @param password   The password of user.
     * @param created_at The data and time created of user.
     */
    public User(String name, String email, String password, String created_at) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
    }

    /**
     * Constructor empty
     */
    public User() {
    }

    /**
     * Retrieves/get the id of user.
     *
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of user.
     *
     * @param id The id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves/get the name of user.
     *
     * @return The name of user.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of user.
     *
     * @param name The name of user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves/get the email of user.
     *
     * @return The email of user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of user.
     *
     * @param email The email of user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves/get the password of user.
     *
     * @return The password of user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of user.
     *
     * @param password The password of user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves/get the data and time created of user.
     *
     * @return The data and time created of user.
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Set the data and time created of user.
     *
     * @param created_at The data and time created of user.
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * Retrieves/get the las login  of user.
     *
     * @return The las login  of user.
     */
    public String getLast_login() {
        return last_login;
    }

    /**
     * Set the las login  of user.
     *
     * @param last_login The las login  of user.
     */
    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", created_at='" + created_at + '\'' +
                ", last_login='" + last_login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return (id == user.id && name.equals(user.name) && email.equals(user.email));
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
