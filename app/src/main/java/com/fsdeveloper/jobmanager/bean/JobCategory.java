package com.fsdeveloper.jobmanager.bean;

/**
 *  The JobCategory class represents all objects of type JobCategory.
 *  All objects of type JobCategory are implemented as instances of this class.
 *
 * @author Douglas Rafael
 * @version 1.0
 */
public class JobCategory {
    private int id;
    private String name;

    /**
     * JobCategory class constructor.
     *
     * @param id The id of category.
     * @param name The name of category.
     */
    public JobCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieve/get the id of category.
     *
     * @return The id of category.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of category.
     *
     * @param id The id of category.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieve/get the name of category.
     *
     * @return The name of category.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of category.
     *
     * @param name The name of category.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "JobCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
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

        JobCategory category = (JobCategory) o;

        return (id == category.id && name.equals(category.name));
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
