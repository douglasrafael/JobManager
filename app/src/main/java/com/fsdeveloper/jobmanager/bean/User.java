package com.fsdeveloper.jobmanager.bean;

/**
 *  The User class represents all objects of type User.
 *  All objects of type User are implemented as instances of this class.
 *
 * @author Douglas Rafael
 * @version 1.0
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String created_at;
    private String last_login;


    /**
     *
     * @param id
     * @param name
     * @param email
     * @param password
     * @param created_at
     */
    public User(int id, String name, String email, String password, String created_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
    }
}
