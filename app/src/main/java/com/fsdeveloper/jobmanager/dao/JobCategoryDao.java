package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fsdeveloper.jobmanager.bean.JobCategory;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs all operations database for object of type Category.
 *
 * @author Created by Douglas Rafael on 23/04/2016.
 * @version 1.0
 */
public class JobCategoryDao extends DBManager implements Dao<JobCategory> {

    private String[] columns = {
            DatabaseHelper.ID,
            DatabaseHelper.NAME
    };

    /**
     * Class constructor.
     *
     * @param context Abstract class whose implementation is provided by Android system.
     * @throws JobManagerException If there is a general exception of the system.
     * @throws ConnectionException If there is one exception of database connection.
     */
    public JobCategoryDao(Context context) throws JobManagerException, ConnectionException {
        super(context);
    }

    @Override
    public List<JobCategory> list(int id_user) throws JobManagerException {
        mGetReadableDatabase();

        List<JobCategory> result = new ArrayList<>();

        Cursor cursor = db.query(DatabaseHelper.TABLE_JOB_CATEGORY, columns, null, null, null, null, DatabaseHelper.NAME);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                JobCategory category = new JobCategory();
                category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));

                result.add(category);
            }
            cursor.close();
        }

        return result;
    }

    @Override
    public JobCategory getById(int _id) throws JobManagerException {
        mGetReadableDatabase();
        JobCategory category = null;

        Cursor cursor = db.query(DatabaseHelper.TABLE_JOB_CATEGORY, columns, DatabaseHelper.ID + "=?", new String[]{String.valueOf(_id)}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            category = new JobCategory();
            category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
            category.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));

            cursor.close();
        }

        return category;
    }

    @Override
    public int insert(JobCategory o) throws JobManagerException {
        mGetWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME, o.getName());

        long _id = db.insert(DatabaseHelper.TABLE_JOB_CATEGORY, null, values);

        return (int) _id;
    }

    @Override
    public boolean update(JobCategory o) throws JobManagerException {
        mGetWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME, o.getName());

        int rowsAffected = db.update(DatabaseHelper.TABLE_JOB_CATEGORY, values, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

        // Verifies that was successfully updated
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(JobCategory o) throws JobManagerException {
        mGetWritableDatabase();

        int rowsAffected = db.delete(DatabaseHelper.TABLE_JOB_CATEGORY, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

        // Verifies that was successfully deleted
        return rowsAffected == 1;
    }

    @Override
    public List<JobCategory> search_all(String s, int id_user) throws JobManagerException {
        mGetReadableDatabase();
        List<JobCategory> result = new ArrayList<>();

        Cursor cursor = db.query(true, DatabaseHelper.TABLE_JOB_CATEGORY, columns, DatabaseHelper.NAME + " LIKE ?", new String[]{s + "%"}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                JobCategory category = new JobCategory();
                category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));

                result.add(category);
            }
            cursor.close();
        }

        return result;
    }

    @Override
    public int size(int id_user) throws JobManagerException {
        return 0;
    }

    /**
     * Select all categories of job.
     *
     * @param protocol_job The protocol of job.
     * @return Categories of job.
     * @throws JobManagerException If there is an exception.
     */
    public List<JobCategory> getCategoriesJob(int protocol_job) throws JobManagerException {
        mGetReadableDatabase();
        List<JobCategory> result = new ArrayList<>();

        String query = "SELECT " + DatabaseHelper.ID + "," + DatabaseHelper.NAME + " FROM " + DatabaseHelper.TABLE_JOB_CATEGORY + " " +
                "INNER JOIN " + DatabaseHelper.TABLE_JOB_HAS_JOB_CATEGORY + " ON " + DatabaseHelper.JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID + "=" + DatabaseHelper.ID + " " +
                "WHERE " + DatabaseHelper.JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL + "=" + protocol_job + ";";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                JobCategory category = new JobCategory();
                category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));

                result.add(category);
            }
            cursor.close();
        }

        return result;
    }
}
