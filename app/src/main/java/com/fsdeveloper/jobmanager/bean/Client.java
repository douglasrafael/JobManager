package com.fsdeveloper.jobmanager.bean;

import java.util.List;

/**
 *  The Client class represents all objects of type Customer.
 *  All objects of type Client are implemented as instances of this class.
 *
 * @author Douglas Rafael
 * @version 1.0
 */
public class Client {
    private int protocol;
    private String first_name;
    private String last_name;
    private String email;
    private String address;
    private int rating;
    private List<Phone> phoneList;

    /**
     * Client class constructor.
     *
     * @param protocol The protocol of client.
     * @param first_name The first name of client.
     * @param last_name The last name of client.
     * @param email The email of client.
     * @param address The address of client.
     * @param rating The rating of client.
     * @param phoneList The phone list
     */
    public Client(int protocol, String first_name, String last_name, String email, String address, int rating, List<Phone> phoneList) {
        this.protocol = protocol;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.rating = rating;
        this.phoneList = phoneList;
    }

    /**
     * Retrieve/get the client protocol.
     *
     * @return The protocol of client.
     */
    public int getProtocol() {
        return protocol;
    }

    /**
     * Set the protocol of client.
     *
     * @param protocol The protocol of client.
     */
    public void setProtocol(int protocol) {
        this.protocol = protocol;
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
                "id=" + protocol +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", phoneList=" + phoneList +
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

        Client client = (Client) o;

        return protocol == client.protocol;
    }

    @Override
    public int hashCode() {
        int result = protocol;
        result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
        result = 31 * result + (last_name != null ? last_name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneList != null ? phoneList.hashCode() : 0);
        return result;
    }
}
