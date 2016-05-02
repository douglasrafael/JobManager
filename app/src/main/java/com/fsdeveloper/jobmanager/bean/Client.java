package com.fsdeveloper.jobmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * The Client class represents all objects of type Customer.
 * All objects of type Client are implemented as instances of this class.
 *
 * @author Created by Douglas Rafael on 20/04/2016.
 * @version 1.0
 */
public class Client implements Serializable {
    private static final long serialVersionUID = -3377250322480752537L;

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String address;
    private int rating;
    private int user_id;
    private String created_at;
    private List<Phone> phoneList;

    /**
     * Client class constructor.
     *
     * @param id         The id of client.
     * @param first_name The first name of client.
     * @param last_name  The last name of client.
     * @param email      The email of client.
     * @param address    The address of client.
     * @param rating     The rating of client.
     * @param user_id    The id of user.
     * @param created_at The data time created.
     * @param phoneList  The phone list
     */
    public Client(int id, String first_name, String last_name, String email, String address, int rating, int user_id, String created_at, List<Phone> phoneList) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.rating = rating;
        this.user_id = user_id;
        this.created_at = created_at;
        this.phoneList = phoneList;
    }

    /**
     * Client class constructor.
     *
     * @param first_name The first name of client.
     * @param last_name  The last name of client.
     * @param email      The email of client.
     * @param address    The address of client.
     * @param rating     The rating of client.
     * @param user_id    The id of user.
     * @param created_at The data time created.
     * @param phoneList  The phone list
     */
    public Client(String first_name, String last_name, String email, String address, int rating, int user_id, String created_at, List<Phone> phoneList) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.rating = rating;
        this.user_id = user_id;
        this.created_at = created_at;
        this.phoneList = phoneList;
    }

    /**
     * Client class constructor.
     */
    public Client() {
    }

    /**
     * Retrieve/get the client id.
     *
     * @return The id of client.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of client.
     *
     * @param id The id of client.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieve/get the first name of client.
     *
     * @return The first name of client.
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Set the first name of client.
     *
     * @param first_name The first name of client.
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Retrieve/get the last name of client.
     *
     * @return The last name of client.
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Set the last name of client.
     *
     * @param last_name The last name of client.
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Retrieve/get the email of client.
     *
     * @return The email of client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of client.
     *
     * @param email The email of client.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieve/get the address of client.
     *
     * @return The address of client.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address of client.
     *
     * @param address The address of client.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieve/get the rating of client.
     *
     * @return the rating of client.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Set the rating of client.
     *
     * @param rating the rating of client.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Retrieves/get the id user.
     *
     * @return The id of user.
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Set the id user.
     *
     * @param user_id The id of user.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Creation date of object.
     *
     * @return Creation date.
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * The Creation date of object.
     *
     * @param created_at Creation date.
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * Retrieve/get the phone list of client.
     *
     * @return The phone list.
     */
    public List<Phone> getPhoneList() {
        return phoneList;
    }

    /**
     * Set the phone list of client.
     *
     * @param phoneList The phone list.
     */
    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", user_id=" + user_id +
                ", created_at='" + created_at + '\'' +
                ", phoneList=" + phoneList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Client client = (Client) o;

        if (id != client.id)
            return false;
        if (rating != client.rating)
            return false;
        if (user_id != client.user_id)
            return false;
        if (first_name != null ? !first_name.equals(client.first_name) : client.first_name != null)
            return false;
        if (last_name != null ? !last_name.equals(client.last_name) : client.last_name != null)
            return false;
        if (email != null ? !email.equals(client.email) : client.email != null)
            return false;
        if (address != null ? !address.equals(client.address) : client.address != null)
            return false;
        if (created_at != null ? !created_at.equals(client.created_at) : client.created_at != null)
            return false;

        return phoneList != null ? phoneList.equals(client.phoneList) : client.phoneList == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + user_id;
        return result;
    }
}
