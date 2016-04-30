package com.fsdeveloper.jobmanager.bean;

import java.io.Serializable;

/**
 * The PhoneType class represents all objects of type PhoneType.
 * All objects of type PhoneType are implemented as instances of this class.
 * <p/>
 * The type of phone can be: Mobile, Home or Work.
 *
 * @author Created by Douglas Rafael on 20/04/2016.
 * @version 1.0
 */
public class PhoneType implements Serializable {
    private static final long serialVersionUID = -2979141078592808318L;

    private int id;
    private String title;

    /**
     * PhoneType class constructor.
     *
     * @param id    The id of the type of phone.
     * @param title The title of the type of phone.
     */
    public PhoneType(int id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * PhoneType class constructor.
     */
    public PhoneType() {
    }

    /**
     * Retrieves/get the id of the type of phone.
     *
     * @return The id of the type of phone.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the type of phone.
     *
     * @param id The id of the type of phone.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves/get the title of the type of phone.
     *
     * @return The title of the type of phone.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title of the type phone.
     *
     * @param title The title of the type of phone.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "PhoneType{" +
                "id=" + id +
                ", title='" + title + '\'' +
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

        PhoneType phoneType = (PhoneType) o;

        return id == phoneType.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
