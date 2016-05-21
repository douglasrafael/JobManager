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
    private String name;
    private String email;
    private String address;
    private byte[] image;
    private int rating;
    private int user_id;
    private String created_at;
    private List<Phone> phoneList;
    private int totalOfJobs;

    /**
     * Client class constructor.
     *
     * @param id         The id of client.
     * @param name       The name of client.
     * @param email      The email of client.
     * @param address    The address of client.
     * @param rating     The rating of client.
     * @param user_id    The id of user.
     * @param phoneList  The phone list
     */
    public Client(int id, String name, String email, String address, byte[] image, int rating, int user_id, List<Phone> phoneList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.rating = rating;
        this.user_id = user_id;
        this.phoneList = phoneList;
    }

    /**
     * Client class constructor.
     *
     * @param name      The name of client.
     * @param email     The email of client.
     * @param address   The address of client.
     * @param image     The image of client
     * @param rating    The rating of client.
     * @param user_id   The id of user.
     * @param phoneList The phone list
     */
    public Client(String name, String email, String address, byte[] image, int rating, int user_id, List<Phone> phoneList) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.image = image;
        this.rating = rating;
        this.user_id = user_id;
        this.phoneList = phoneList;
    }

    /**
     * Client class constructor.
     *
     * @param name      The name of client.
     * @param email     The email of client.
     * @param address   The address of client.
     * @param user_id   The id of user.
     * @param phoneList The phone list
     */
    public Client(String name, String email, String address, byte[] image, int user_id, List<Phone> phoneList) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.image = image;
        this.user_id = user_id;
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
     * Retrieve/get the name of client.
     *
     * @return The name of client.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of client.
     *
     * @param name The name of client.
     */
    public void setName(String name) {
        this.name = name;
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
     * Retrieve/get the image of client.
     *
     * @return The image of client.
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Set the image of client.
     *
     * @param image The image of client.
     */
    public void setImage(byte[] image) {
        this.image = image;
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

    /**
     * Retrieve/get the total the jobs of client.
     *
     * @return The phone list.
     */
    public int getTotalOfJobs() {
        return totalOfJobs;
    }

    /**
     * Set the total the jobs of client.
     *
     * @param totalOfJobs The total.
     */
    public void setTotalOfJobs(int totalOfJobs) {
        this.totalOfJobs = totalOfJobs;
    }

    /**
     * Check if id is null.
     *
     * @return True if is null or False.
     */
    public boolean isIdNull() {
        return ((Object) id) == null;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name ='" + name + '\'' +
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;

        return address != null ? address.equals(client.address) : client.address == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + user_id;
        return result;
    }
}