package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fsdeveloper.jobmanager.bean.JobCategory;
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
     * Class constructor, create instance of class DatabaseHelper.
     *
     * @param context Abstract class whose implementation is provided by Android system.
     */
    public JobCategoryDao(Context context) throws JobManagerException {
        super(context);
    }

    @Override
    public List<JobCategory> list(int id_user) throws JobManagerException {
        mGetReadableDatabase();

        List<JobCategory> result = new ArrayList<>();

        /**
         * SELECT job_category._id, job_category.name FROM job_category
         * INNER JOIN job_has_job_category ON job_category._id = job_has_job_category.job_category_id
         * INNER JOIN job ON job_has_job_category.job_protocol = job.protocol
         * WHERE job.user_id = 1
         * GROUP BY job_category.name
         * ORDER BY job_category.name ASC;
         *
         * Selects in ascending order all categories registered by user.
         */
        String query = "SELECT " + DatabaseHelper.ID + ", " + DatabaseHelper.NAME + " FROM " + DatabaseHelper.TABLE_JOB_CATEGORY + " INNER JOIN " +
                DatabaseHelper.TABLE_JOB_HAS_JOB_CATEGORY + " ON " + DatabaseHelper.TABLE_JOB_CATEGORY + "." + DatabaseHelper.ID + " = " +
                DatabaseHelper.TABLE_JOB_HAS_JOB_CATEGORY + "." + DatabaseHelper.JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID + " INNER JOIN " +
                DatabaseHelper.TABLE_JOB + " ON " + DatabaseHelper.TABLE_JOB_HAS_JOB_CATEGORY + "." + DatabaseHelper.JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL + " = " +
                DatabaseHelper.TABLE_JOB + "." + DatabaseHelper.JOB_PROTOCOL + " WHERE " + DatabaseHelper.TABLE_JOB + "." + DatabaseHelper.USER_ID + " = " + id_user + " GROUP BY " +
                DatabaseHelper.TABLE_JOB_CATEGORY + "." + DatabaseHelper.NAME + " ORDER BY " + DatabaseHelper.TABLE_JOB_CATEGORY + "." + DatabaseHelper.NAME + " ASC;";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                JobCategory category = new JobCategory();
                category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));

                result.add(category);
            }
            cursor.close();
        }
        DBClose();

        return result;
    }

    @Override
    public JobCategory getById(int _id) throws JobManagerException {
        mGetReadableDatabase();
        JobCategory category = null;

        Cursor cursor = db.query(DatabaseHelper.TABLE_JOB_CATEGORY, columns, DatabaseHelper.ID + "=?", new String[]{String.valueOf(_id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            category = new JobCategory();
            category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
            category.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));

            cursor.close();
        }
        DBClose();

        return category;
    }

    @Override
    public int insert(JobCategory o) throws JobManagerException {
        mGetWritableDatabase();
        long _id = 0;

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME, o.getName());

        _id = db.insert(DatabaseHelper.TABLE_JOB_CATEGORY, null, values);

        return (int) _id;
    }

    @Override
    public boolean update(JobCategory o) throws JobManagerException {
        mGetWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.NAME, o.getName());

            int rowsAffected = db.update(DatabaseHelper.TABLE_JOB_CATEGORY, values, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

            // Verifies that was successfully updated
            if (rowsAffected == 1) {
                return true;
            }
        } finally {
            DBClose();
        }

        return false;
    }

    @Override
    public boolean delete(JobCategory o) throws JobManagerException {
        mGetWritableDatabase();

        try {
            int rowsAffected = db.delete(DatabaseHelper.TABLE_JOB_CATEGORY, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

            // Verifies that was successfully deleted
            if (rowsAffected == 1) {
                return true;
            }
        } finally {
            DBClose();
        }

        return false;
    }

    @Override
    public List<JobCategory> search_all(String s, int id_user) throws JobManagerException {
        mGetReadableDatabase();
        List<JobCategory> result = new ArrayList<>();

        /**
         * SELECT job_category._id, job_category.name FROM job_category
         * INNER JOIN job_has_job_category ON job_category._id = job_has_job_category.job_category_id
         * INNER JOIN job ON job_has_job_category.job_protocol = job.protocol
         * WHERE job.user_id = id_user AND job_category.name LIKE "s%"
         * GROUP BY job_category.name
         * ORDER BY job_category.name ASC;
         *
         * Selects in ascending order all categories registered by user.
         */
        String query = "SELECT " + DatabaseHelper.ID + ", " + DatabaseHelper.NAME + " FROM " + DatabaseHelper.TABLE_JOB_CATEGORY + " INNER JOIN " +
                DatabaseHelper.TABLE_JOB_HAS_JOB_CATEGORY + " ON " + DatabaseHelper.TABLE_JOB_CATEGORY + "." + DatabaseHelper.ID + " = " +
                DatabaseHelper.TABLE_JOB_HAS_JOB_CATEGORY + "." + DatabaseHelper.JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID + " INNER JOIN " +
                DatabaseHelper.TABLE_JOB + " ON " + DatabaseHelper.TABLE_JOB_HAS_JOB_CATEGORY + "." + DatabaseHelper.JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL + " = " +
                DatabaseHelper.TABLE_JOB + "." + DatabaseHelper.JOB_PROTOCOL + " WHERE " + DatabaseHelper.TABLE_JOB + "." + DatabaseHelper.ID + " = " + id_user + " AND " +
                DatabaseHelper.TABLE_JOB_CATEGORY + "." + DatabaseHelper.NAME + " LIKE '" + s + "%'" + " GROUP BY " +
                DatabaseHelper.TABLE_JOB_CATEGORY + "." + DatabaseHelper.NAME + " ORDER BY " + DatabaseHelper.TABLE_JOB_CATEGORY + "." + DatabaseHelper.NAME + " ASC LIMIT 5;";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                JobCategory category = new JobCategory();
                category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));

                result.add(category);
            }
            cursor.close();
        }
        DBClose();

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

        if (cursor != null) {
            while (cursor.moveToNext()) {
                JobCategory category = new JobCategory();
                category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));

                result.add(category);
            }
            cursor.close();
        }
        DBClose();

        return result;
    }
}
