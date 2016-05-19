package com.fsdeveloper.jobmanager.bean;

import java.io.Serializable;

/**
 * The Phone class represents all objects of type Phone.
 * All objects of type Phone are implemented as instances of this class.
 *
 * @author Created by Douglas Rafael on 20/04/2016.
 * @version 1.0
 */
public class Phone implements Serializable {
    private static final long serialVersionUID = -7181903763870895362L;

    private int id;
    private String number;
    private int client_id;
    private PhoneType type;

    /**
     * Phone class constructor.
     *
     * @param id        Id of phone.
     * @param number    Number of phone.
     * @param client_id The id of client.
     * @param type      Type of phone.
     */
    public Phone(int id, String number, int client_id, PhoneType type) {
        this.id = id;
        this.number = number;
        this.client_id = client_id;
        this.type = type;
    }

    /**
     * Phone class constructor.
     *
     * @param number    Number of phone.
     * @param client_id The id of client
     * @param type      Type of phone.
     */
    public Phone(String number, int client_id, PhoneType type) {
        this.number = number;
        this.client_id = client_id;
        this.type = type;
    }

    /**
     * Phone class constructor.
     *
     * @param number    Number of phone.
     * @param type      Type of phone.
     */
    public Phone(String number, PhoneType type) {
        this.number = number;
        this.type = type;
    }

    /**
     * Phone class constructor.
     */
    public Phone() {
    }

    /**
     * Retrieve/get the phone id.
     *
     * @return The id of phone.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the phone id.
     *
     * @param id Id of phone.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieve/get the phone number.
     *
     * @return The phone number.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Set the phone number.
     *
     * @param number The phone number.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Retrieve/get the id of client.
     *
     * @return The id of client.
     */
    public int getClient_id() {
        return client_id;
    }

    /**
     * Set the id of client.
     *
     * @param client_id The id of client.
     */
    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    /**
     * Retrieves/get the type of phone.
     *
     * @return The type of phone.
     */
    public PhoneType getType() {
        return type;
    }

    /**
     * Set the type of phone.
     *
     * @param type The type phone.
     */
    public void setType(PhoneType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", client_id=" + client_id +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Phone)) {
            return false;
        }

        Phone phone = (Phone) o;

        if (number != null ? !number.equals(phone.number) : phone.number != null) {
            return false;
        }

        return type != null ? type.equals(phone.type) : phone.type == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + client_id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}