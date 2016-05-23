package com.fsdeveloper.jobmanager.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fsdeveloper.jobmanager.bean.Balance;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.bean.JobCategory;
import com.fsdeveloper.jobmanager.bean.Phone;
import com.fsdeveloper.jobmanager.bean.PhoneType;
import com.fsdeveloper.jobmanager.bean.User;
import com.fsdeveloper.jobmanager.dao.ClientDao;
import com.fsdeveloper.jobmanager.dao.JobCategoryDao;
import com.fsdeveloper.jobmanager.dao.JobDao;
import com.fsdeveloper.jobmanager.dao.PhoneDao;
import com.fsdeveloper.jobmanager.dao.PhoneTypeDao;
import com.fsdeveloper.jobmanager.dao.UserDao;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.util.ArrayList;
import java.util.List;

/**
 * It provides access to all methods of DAOs.
 *
 * @author Created by Douglas Rafael on 30/04/2016.
 * @version 1.0
 */
public class Manager {
    public static final String SHARED_PREF = "jmconfigs";

    private UserDao user;
    private ClientDao client;
    private JobDao job;
    private JobCategoryDao category;
    private PhoneDao phone;
    private PhoneTypeDao phoneType;
    private Context context;

    /**
     * Create instances of the DAOs.
     *
     * @param context Abstract class whose implementation is provided by Android system.
     * @throws JobManagerException If there is an exception.
     */
    public Manager(Context context) throws JobManagerException, ConnectionException {
        this.context = context;

        user = new UserDao(context);
        client = new ClientDao(context);
        job = new JobDao(context);
        category = new JobCategoryDao(context);
        phone = new PhoneDao(context);
        phoneType = new PhoneTypeDao(context);
    }

    /**
     * Close connection to database.
     *
     * @param o Object that used the connection.
     */
    public void DBClose(Object o) {
        if (o instanceof User) {
            user.DBClose();
        } else if (o instanceof Client) {
            client.DBClose();
        } else if (o instanceof Job) {
            job.DBClose();
        } else if (o instanceof JobCategory) {
            category.DBClose();
        } else if (o instanceof Phone) {
            phone.DBClose();
        } else if (o instanceof PhoneType) {
            phoneType.DBClose();
        }
    }

    /**
     * Check if connection to database is open.
     *
     * @param o Object that used the connection.
     * @return True if is open or False otherwise.
     */
    public boolean DBIsOpen(Object o) {
        if (o instanceof User) {
            return user.DBIsOpen();
        } else if (o instanceof Client) {
            return client.DBIsOpen();
        } else if (o instanceof Job) {
            return job.DBIsOpen();
        } else if (o instanceof JobCategory) {
            return category.DBIsOpen();
        } else if (o instanceof Phone) {
            return phone.DBIsOpen();
        } else if (o instanceof PhoneType) {
            return phoneType.DBIsOpen();
        }
        return false;
    }

    // TODO - Methods of UserDao

    /**
     * Select the user list.
     *
     * @return The list of users.
     * @throws JobManagerException If there is an exception.
     */
    public List<User> listOfUsers() throws JobManagerException {
        return user.list(0);
    }

    /**
     * Insert new user.
     *
     * @param u The user.
     * @return The id inserted or -1 if fail.
     * @throws JobManagerException If there is an exception.
     */
    public int insertUser(User u) throws JobManagerException {
        return user.insert(u);
    }

    public User getUserById(int user_id) throws JobManagerException {
        return user.getById(user_id);
    }


    // TODO - Methods of ClientDao

    /**
     * Select the user client list.
     *
     * @param user_id The user id.
     * @return The list of clients.
     * @throws JobManagerException If there is an exception.
     */
    public List<Client> listOfClients(int user_id) throws JobManagerException {
        return client.list(user_id);
    }

    /**
     * Select client.
     *
     * @param _id The id of client.
     * @return The client.
     * @throws JobManagerException If there is an exception.
     */
    public Client getClient(int _id) throws JobManagerException {
        return client.getById(_id);
    }

    /**
     * Insert new client.
     *
     * @param c The client.
     * @return The id inserted or -1 if fail.
     * @throws JobManagerException If there is an exception.
     */
    public int insertClient(Client c) throws JobManagerException {
        return client.insert(c);
    }

    /**
     * Update the client.
     *
     * @param c The client.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean updateClient(Client c) throws JobManagerException {
        return client.update(c);
    }

    /**
     * Delete the client.
     *
     * @param c The client.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean deleteClient(Client c) throws JobManagerException {
        return client.delete(c);
    }

    /**
     * Search user clients according to the string.
     *
     * @param s       The string/term searching.
     * @param user_id The user id.
     * @return The list of clients.
     * @throws JobManagerException If there is an exception.
     */
    public List<Client> searchAllClient(String s, int user_id) throws JobManagerException {
        return client.search_all(s, user_id);
    }

    // TODO - Methods of JobCategoryDao

    /**
     * Select the job categories list.
     *
     * @return The list of categories.
     * @throws JobManagerException If there is an exception.
     */
    public List<JobCategory> listOfJobCategories() throws JobManagerException {
        return category.list(0);
    }

    /**
     * Select category.
     *
     * @param _id The id of category.
     * @return The category.
     * @throws JobManagerException If there is an exception.
     */
    public JobCategory getCategory(int _id) throws JobManagerException {
        return category.getById(_id);
    }

    /**
     * Insert new category.
     *
     * @param jc The category.
     * @return The id inserted or -1 if fail.
     * @throws JobManagerException If there is an exception.
     */
    public int insertCategory(JobCategory jc) throws JobManagerException {
        return category.insert(jc);
    }

    /**
     * Update the category.
     *
     * @param jc The category.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean updateCcategory(JobCategory jc) throws JobManagerException {
        return category.update(jc);
    }

    /**
     * Delete the category.
     *
     * @param jc The category.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean deleteCategory(JobCategory jc) throws JobManagerException {
        return category.delete(jc);
    }

    /**
     * Search categories according to the string.
     *
     * @param s The string/term searching.
     * @return The list of categories.
     * @throws JobManagerException If there is an exception.
     */
    public List<JobCategory> searchAllCategory(String s) throws JobManagerException {
        return category.search_all(s, 0);
    }

    /**
     * Select all categories of job.
     *
     * @param protocol The protocol of job.
     * @return The list of categories.
     * @throws JobManagerException If there is an exception.
     */
    public List<JobCategory> getCategoriesJob(String protocol) throws JobManagerException {
        return category.getCategoriesJob(protocol);
    }

    // TODO - Methods of PhoneDao

    /**
     * Select the phone list.
     *
     * @return The list of phones.
     * @throws JobManagerException If there is an exception.
     */
    public List<Phone> listOfPhones(int client_id) throws JobManagerException {
        return phone.list(client_id);
    }

    /**
     * Select phone.
     *
     * @param _id The id of phone.
     * @return The phone.
     * @throws JobManagerException If there is an exception.
     */
    public Phone getPhone(int _id) throws JobManagerException {
        return phone.getById(_id);
    }

    /**
     * Insert new phone.
     *
     * @param p The phone.
     * @return The id inserted or -1 if fail.
     * @throws JobManagerException If there is an exception.
     */
    public int insertPhone(Phone p) throws JobManagerException {
        return phone.insert(p);
    }

    /**
     * Update the phone.
     *
     * @param p The phone.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean updatePhone(Phone p) throws JobManagerException {
        return phone.update(p);
    }

    /**
     * Delete the phone.
     *
     * @param p The phone.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean deletePhone(Phone p) throws JobManagerException {
        return phone.delete(p);
    }

    // TODO - Methods of PhoneTypeDao

    /**
     * Select list of phones type.
     *
     * @return List of phones type.
     * @throws JobManagerException If there is an exception.
     */
    public List<PhoneType> listOfPhonesTtype() throws JobManagerException {
        return phoneType.list(0);
    }

    /**
     * Get phone type.
     *
     * @param _id The id of phone type.
     * @return The phone type.
     * @throws JobManagerException If there is an exception.
     */
    public PhoneType getPhoneType(int _id) throws JobManagerException {
        return phoneType.getById(_id);
    }

    // TODO - Methods of JobDao

    /**
     * Generates a unique protocol.
     *
     * @return The protocol.
     * @throws JobManagerException If there is an exception.
     */
    public String generateProtocol() throws JobManagerException {
        return job.generateProtocol();
    }

    /**
     * Selects the job according to the Protocol.
     *
     * @param protocol The protocol.
     * @return The job.
     * @throws JobManagerException If there is an exception.
     */
    public Job getJobByProtocol(String protocol) throws JobManagerException {
        return job.getByProtocol(protocol);
    }

    /**
     * Select the job list.
     *
     * @param user_id The job id.
     * @return The list of jobs.
     * @throws JobManagerException If there is an exception.
     */
    public List<Job> listOfJobs(int user_id) throws JobManagerException {
        return job.list(user_id);
    }

    /**
     * Select job.
     *
     * @param protocol The protocol of job.
     * @return The job.
     * @throws JobManagerException If there is an exception.
     */
    public Job getJob(String protocol) throws JobManagerException {
        return job.getByProtocol(protocol);
    }

    /**
     * Insert new job.
     *
     * @param j The job.
     * @return The id inserted or -1 if fail.
     * @throws JobManagerException If there is an exception.
     */
    public int insertJob(Job j) throws JobManagerException {
        return job.insert(j);
    }

    /**
     * Update the job.
     *
     * @param j The job.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean updateJob(Job j) throws JobManagerException {
        return job.update(j);
    }

    /**
     * Delete the job.
     *
     * @param j The job.
     * @return True if updated and False if not.
     * @throws JobManagerException If there is an exception.
     */
    public boolean deleteJob(Job j) throws JobManagerException {
        return job.delete(j);
    }

    /**
     * Search user clients according to the string.
     *
     * @param s       The string/term searching.
     * @param user_id The user id.
     * @return The list of clients.
     * @throws JobManagerException If there is an exception.
     */
    public List<Job> searchAllJob(String s, int user_id) throws JobManagerException {
        return job.search_all(s, user_id);
    }

    /**
     * Number of user jobs.
     *
     * @param user_id The user id.
     * @return The number of jobs.
     * @throws JobManagerException If there is an exception.
     */
    public int sizeJob(int user_id) throws JobManagerException {
        return job.size(user_id);
    }

    /**
     * Returns the total number of jobs performed to the client.
     *
     * @param user_id   The user id.
     * @param client_id The client id.
     * @return The number of jobs.
     * @throws JobManagerException If there is an exception.
     */
    public int numberJobClient(int user_id, int client_id) throws JobManagerException {
        return job.numberJobClient(user_id, client_id);
    }

    /**
     * Mark or unchecks the job finalized
     * Passes True as a parameter to "marked" or pass False uncheck.
     *
     * @param protocol The protocol of the job
     * @param marked   True or False
     * @return If is success in the operation.
     */
    public boolean setChangeFinalizedJob(String protocol, boolean marked) throws JobManagerException {
        return job.setChangeFinalizedJob(protocol, marked);
    }

    /**
     * List the jobs associated with the client.
     *
     * @param client_id The id of client
     * @return The List of the jobs.
     * @throws JobManagerException If there is a general exception of the system.
     */
    public List<Job> listJobsAssociateClient(int client_id) throws JobManagerException {
        return job.listJobsClient(client_id);
    }

    // TODO - Methods of Balance
    public Balance getBalanceJobs(String dateStart, String dateEnd, int user_id, boolean includeNotFinalized) throws JobManagerException {
        List<Job> jobsCurrentBalance, jobsNotCurrentBalance;
        Balance balance = null;

        if ((dateStart != null && dateStart.length() > 0) && (dateEnd != null && dateEnd.length() > 0)) {
            jobsCurrentBalance = new ArrayList<Job>();
            jobsNotCurrentBalance = new ArrayList<Job>();
            Double valueInput = 0.0;
            Double valueOutput = 0.0;
            Double valueProfit = 0.0;

            balance = new Balance();
            balance.setDateStart(dateStart);
            balance.setDateEnd(dateEnd);

            List<Job> jobsBalance = new ArrayList<Job>(job.listJobToBalance(balance, user_id));
            if (jobsBalance != null && jobsBalance.size() > 0) {

                for (Job job : jobsBalance) {
                    if (includeNotFinalized) {
                        valueInput += job.getPrice();
                        valueOutput += job.getExpense();
                        jobsCurrentBalance.add(job);
                    } else {
                        if (job.isFinalized()) {
                            valueInput += job.getPrice();
                            valueOutput += job.getExpense();
                            jobsCurrentBalance.add(job);
                        } else {
                            jobsNotCurrentBalance.add(job);
                        }
                    }
                }
            }

            /**
             * Calc
             */
            balance.setInputValue(valueInput);
            balance.setOutputValue(valueOutput);

            valueProfit = valueInput - valueOutput;
            balance.setTotalValue(valueProfit);
            balance.setCurrentBalance(jobsCurrentBalance);
            balance.setNotCurrentBalance(jobsNotCurrentBalance);

            if (jobsCurrentBalance.size() > 0) {
                balance.setAverageInput(valueInput / jobsCurrentBalance.size());
                balance.setAverageOutput(valueOutput / jobsCurrentBalance.size());
                balance.setAverageProfit(valueProfit / jobsCurrentBalance.size());
            } else {
                balance.setAverageInput(0.0);
                balance.setAverageOutput(0.0);
                balance.setAverageProfit(0.0);
            }
        }

        return balance;
    }
}