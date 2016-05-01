package com.fsdeveloper.jobmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * The Job class represents all objects of type Job.
 * All objects of type Job are implemented as instances of this class.
 *
 * @author Created by Douglas Rafael on 20/04/2016.
 * @version 1.1
 */
public class Job implements Serializable {
    private static final long serialVersionUID = -7790542829078114180L;

    private String protocol;
    private String title;
    private String description;
    private String note;
    private Double price;
    private Double expense;
    private String finalized_at;
    private String created_at;
    private String updated_at;
    private int user_id;
    private int client_id;
    private Client client;
    List<JobCategory> categories;

    /**
     * Job class constructor.
     *
     * @param protocol     The protocol of job.
     * @param title        The title of job.
     * @param description  The description of job.
     * @param note         The note of job.
     * @param price        The price of job.
     * @param expense      The value of the expenditure spent for performing the job.
     * @param finalized_at The date and time that was finalized.
     * @param created_at   The date and time that was created.
     * @param user_id      The id of user who made the job.
     * @param client_id    The id of client that requested the job.
     * @param categories   The categories of job.
     */
    public Job(String protocol, String title, String description, String note, Double price, Double expense, String finalized_at, String created_at, int user_id, int client_id, List<JobCategory> categories) {
        this.protocol = protocol;
        this.title = title;
        this.description = description;
        this.note = note;
        this.price = price;
        this.expense = expense;
        this.finalized_at = finalized_at;
        this.created_at = created_at;
        this.user_id = user_id;
        this.client_id = client_id;
        this.categories = categories;
    }

    /**
     * Job class constructor.
     */
    public Job() {
    }

    /**
     * Retrieves/get the protocol of job.
     *
     * @return The protocol of job.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Set the protocol of job.
     *
     * @param protocol The protocol of job.
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Retrieves/get title of job.
     *
     * @return The title of job.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title of job.
     *
     * @param title The title job.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves/get description of job.
     *
     * @return The description of job.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description of job.
     *
     * @param description The description of job.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves/get note of job.
     *
     * @return The note of job.
     */
    public String getNote() {
        return note;
    }

    /**
     * Set the note of job.
     *
     * @param note The note of job.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Retrieves/get price of job.
     *
     * @return The note of job.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Set the price of job.
     *
     * @param price The price of job.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Retrieves/get expense of job.
     *
     * @return The expense of job.
     */
    public Double getExpense() {
        return expense;
    }

    /**
     * Set the expense of job.
     *
     * @param expense The expense of job.
     */
    public void setExpense(Double expense) {
        this.expense = expense;
    }

    /**
     * Retrieves/get the date and time the job was finalized.
     *
     * @return The date and time.
     */
    public String getFinalized_at() {
        return finalized_at;
    }

    /**
     * Set the date and time the job was finalized.
     *
     * @param finalized_at The date and time.
     */
    public void setFinalized_at(String finalized_at) {
        this.finalized_at = finalized_at;
    }

    /**
     * Retrieves/get the date and time the job was created.
     *
     * @return The date and time.
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Set the date and time the job was created.
     *
     * @param created_at The date and time.
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * Retrieves/get the date and time the job was updated.
     *
     * @return The date and time.
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * Set the date and time the job was updated.
     *
     * @param updated_at The date and time.
     */
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * Retrieves/get the id user who made the job.
     *
     * @return The id of user.
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Set the id user who made the job.
     *
     * @param user_id The id of user.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Retrieves/get the id of client The client that requested the job.
     *
     * @return The id of client.
     */
    public int getClient_id() {
        return client_id;
    }

    /**
     * Set the id of client The client that requested the job.
     *
     * @param client_id The id of  client.
     */
    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    /**
     * Retrieves/get client.
     *
     * @return The client.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Set client.
     *
     * @param client The client.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Retrieves/get list of categories.
     *
     * @return The list of categories.
     */
    public List<JobCategory> getCategories() {
        return categories;
    }

    /**
     * Set categories.
     *
     * @param categories list of categories.
     */
    public void setCategories(List<JobCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Job{" +
                "protocol='" + protocol + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", price=" + price +
                ", expense=" + expense +
                ", finalized_at='" + finalized_at + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", user_id=" + user_id +
                ", client_id=" + client_id +
                ", client=" + client +
                ", categories=" + categories +
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

        Job job = (Job) o;

        return (protocol == job.protocol && title.equals(job.title) && user_id == job.user_id && client_id == job.client_id);
    }

    @Override
    public int hashCode() {
        int result = protocol != null ? protocol.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + user_id;
        result = 31 * result + client_id;
        return result;
    }
}

