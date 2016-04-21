package com.fsdeveloper.jobmanager.bean;

import java.io.Serializable;

/**
 * The Phone class represents all objects of type Phone.
 * All objects of type Phone are implemented as instances of this class.
 *
 * @author Created by Douglas Rafael on 20/04/2016.
 * @version 1.0
 */
public class Phone {
    private int id;
    private int number;
    private PhoneType type;

    /**
     * Phone class constructor.
     *
     * @param id     Id of phone.
     * @param number Number of phone.
     * @param type   Type of phone.
     */
    public Phone(int id, int number, PhoneType type) {
        this.id = id;
        this.number = number;
        this.type = type;
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
    public int getNumber() {
        return number;
    }

    /**
     * Set the phone number.
     *
     * @param number The phone number.
     */
    public void setNumber(int number) {
        this.number = number;
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
                "number=" + number +
                ", type=" + type +
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

        Phone phone = (Phone) o;

        return (id == phone.id && number == phone.number && type.equals(phone.type));
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}