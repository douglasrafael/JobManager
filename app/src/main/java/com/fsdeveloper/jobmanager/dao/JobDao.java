package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.bean.JobCategory;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.tool.MyDataTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * @author Created by Douglas Rafael on 30/04/2016.
 * @version 1.0
 */
public class JobDao extends DBManager implements Dao<Job> {
    private Context context;

    private String[] columns = {
            DatabaseHelper.JOB_PROTOCOL,
            DatabaseHelper.TITLE,
            DatabaseHelper.JOB_DESCRIPTION,
            DatabaseHelper.JOB_NOTE,
            DatabaseHelper.JOB_PRICE,
            DatabaseHelper.JOB_EXPENSE,
            DatabaseHelper.JOB_FINALIZED_AT,
            DatabaseHelper.CREATED_AT,
            DatabaseHelper.JOB_UPDATE_AT,
            DatabaseHelper.USER_ID,
            DatabaseHelper.CLIENT_ID
    };

    public JobDao(Context context) throws JobManagerException, ConnectionException {
        super(context);
        this.context = context;
    }

    @Override
    public List<Job> list(int user_id) throws JobManagerException {
        List<Job> result = new ArrayList<>();
        mGetReadableDatabase();

        // Select all clients of the user
        Cursor cursor = db.query(DatabaseHelper.TABLE_JOB, columns, DatabaseHelper.USER_ID + "=?", new String[]{String.valueOf(user_id)}, null, null, DatabaseHelper.CREATED_AT);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(createJob(cursor));
            }
            cursor.close();
        }

        return result;
    }

    @Override
    public Job getById(int _id) throws JobManagerException {
        // Not to be implemented
        return null;
    }

    /**
     * Select the job according to the protocol.
     *
     * @param protocol The protocol of job.
     * @return The job.
     * @throws JobManagerException If there is a general exception of the system.
     */
    public Job getByProtocol(String protocol) throws JobManagerException {
        mGetReadableDatabase();
        Job job = null;

        Cursor cursor = db.query(DatabaseHelper.TABLE_JOB, columns, DatabaseHelper.JOB_PROTOCOL + "=?", new String[]{protocol}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            job = createJob(cursor);
        }
        return job;
    }

    @Override
    public int insert(Job o) throws JobManagerException {
        mGetWritableDatabase();
        long _id = 0;

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.JOB_PROTOCOL, generateProtocol());
        values.put(DatabaseHelper.TITLE, o.getTitle());
        values.put(DatabaseHelper.JOB_DESCRIPTION, o.getDescription());
        values.put(DatabaseHelper.JOB_NOTE, o.getNote());
        values.put(DatabaseHelper.JOB_PRICE, o.getPrice());
        values.put(DatabaseHelper.JOB_EXPENSE, o.getExpense());
        values.put(DatabaseHelper.JOB_FINALIZED_AT, o.getFinalized_at());
        values.put(DatabaseHelper.CREATED_AT, MyDataTime.getDataTime(context.getResources().getString(R.string.date_time_bd)));
        values.put(DatabaseHelper.USER_ID, o.getUser_id());
        values.put(DatabaseHelper.CLIENT_ID, o.getClient().getId());

        db.beginTransaction();
        try {
            // inserting of client if not exist
            ClientDao client = new ClientDao(context);

            if(client.getById(o.getClient_id()) == null) {
                client.insert(o.getClient());
            }

            // Inserting of job
            _id = db.insert(DatabaseHelper.TABLE_JOB, null, values);

            // Associates the categories to job
            if (o.getCategories().size() > 0) {
                ContentValues valuesCategory;

                for (JobCategory c : o.getCategories()) {
                    valuesCategory = new ContentValues();

                    valuesCategory.put(DatabaseHelper.JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL, o.getProtocol());
                    valuesCategory.put(DatabaseHelper.JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID, c.getId());
                    db.insert(DatabaseHelper.TABLE_JOB_HAS_JOB_CATEGORY, null, valuesCategory);
                }
            }

            db.setTransactionSuccessful();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return (int) _id;
    }

    @Override
    public boolean update(Job o) throws JobManagerException {
        mGetWritableDatabase();

        Job old_job = getByProtocol(o.getProtocol());

        // Checks for data to be actually changed.
        if (!old_job.equals(o)) {
            db.beginTransaction();

            try {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.TITLE, o.getTitle());
                values.put(DatabaseHelper.JOB_DESCRIPTION, o.getDescription());
                values.put(DatabaseHelper.JOB_NOTE, o.getNote());
                values.put(DatabaseHelper.JOB_PRICE, o.getPrice());
                values.put(DatabaseHelper.JOB_EXPENSE, o.getExpense());
                values.put(DatabaseHelper.JOB_FINALIZED_AT, o.getFinalized_at());
                values.put(DatabaseHelper.JOB_UPDATE_AT, MyDataTime.getDataTime(context.getResources().getString(R.string.date_time_bd)));
                values.put(DatabaseHelper.USER_ID, o.getUser_id());
                values.put(DatabaseHelper.CLIENT_ID, o.getClient_id());


                // update job
                int rowsAffected = db.update(DatabaseHelper.TABLE_JOB, values, DatabaseHelper.JOB_PROTOCOL + "=?", new String[]{String.valueOf(o.getProtocol())});

                // Verifies that was successfully updated
                if (rowsAffected == 1) {
                    db.setTransactionSuccessful();
                    return true;
                }

            } finally {
                db.endTransaction();
            }
        }

        return false;
    }

    @Override
    public boolean delete(Job o) throws JobManagerException {
        return false;
    }

    @Override
    public List<Job> search_all(String s, int _id) throws JobManagerException {
        return null;
    }

    @Override
    public int size(int _id) throws JobManagerException {
        return 0;
    }

    /**
     * Verifies if the protocol is unique.
     * Is searched for in the database.
     * If is not found it is because it is unique.
     *
     * @param protocol The protocol.
     * @return True if unique or False otherwise.
     * @throws JobManagerException If there is a general exception of the system.
     */
    public boolean protocolIsUnique(String protocol) throws JobManagerException {
        return getByProtocol(protocol) != null;
    }

    /**
     * @return The protocol of job.
     * @throws JobManagerException If there is a general exception of the system.
     */
    public String generateProtocol() throws JobManagerException {
        Random r = new Random();

        String protocol = String.valueOf(MyDataTime.getCalendar().get(Calendar.YEAR));

        while (protocol.length() < 10) {
            // generate number [0, 10)
            protocol += r.nextInt(10) * MyDataTime.getCalendar().get(Calendar.SECOND);
        }

        protocol = protocol.substring(0, 10);

        // Verifies that the protocol already exists
        try {
            mGetReadableDatabase();
            if (new JobDao(context).getByProtocol(protocol) != null) {
                return generateProtocol();
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return protocol;
    }


    /**
     * Return built job.
     *
     * @param cursor It contains data.
     * @return The job.
     * @throws JobManagerException If there is a general exception of the system.
     */
    private Job createJob(Cursor cursor) throws JobManagerException {
        Job job = new Job();

        job.setProtocol(cursor.getString(cursor.getColumnIndex(DatabaseHelper.JOB_PROTOCOL)));
        job.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE)));
        job.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.JOB_DESCRIPTION)));
        job.setNote(cursor.getString(cursor.getColumnIndex(DatabaseHelper.JOB_NOTE)));
        job.setPrice(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.JOB_PRICE)));
        job.setExpense(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.JOB_EXPENSE)));
        job.setFinalized_at(cursor.getString(cursor.getColumnIndex(DatabaseHelper.JOB_FINALIZED_AT)));
        job.setCreated_at(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CREATED_AT)));
        job.setUpdated_at(cursor.getString(cursor.getColumnIndex(DatabaseHelper.JOB_UPDATE_AT)));
        job.setUser_id(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_ID)));
        job.setClient_id(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CLIENT_ID)));

        try {
            // get all categories
            job.setCategories(new JobCategoryDao(context).getCategoriesJob(job.getProtocol()));

            // get Client
            job.setClient(new ClientDao(context).getById(job.getClient_id()));
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return job;
    }
}
