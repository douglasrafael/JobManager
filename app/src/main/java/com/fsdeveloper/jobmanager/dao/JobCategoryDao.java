package com.fsdeveloper.jobmanager.dao;

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
public class JobCategoryDao extends DatabaseAccess implements Dao<JobCategory> {

    public JobCategoryDao(Context context) {
        super(context);
    }

    @Override
    public List<JobCategory> list(int id_user) throws JobManagerException {
        List<JobCategory> result = new ArrayList<>();

        open(); // open connection
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM job_category", null);
        cursor.moveToFirst();
        JobCategory c;
        while (!cursor.isAfterLast()) {
            c = new JobCategory();
            c.setId(cursor.getInt(0));
            c.setName(cursor.getString(1));
            result.add(c);

            cursor.moveToNext();
        }
        cursor.close();
        close(); // // close connection

        return result;
    }

    @Override
    public void save(JobCategory o) throws JobManagerException {

    }

    @Override
    public void update(JobCategory o) throws JobManagerException {

    }

    @Override
    public boolean delete(JobCategory o) throws JobManagerException {
        return false;
    }

    @Override
    public JobCategory search(int id) throws JobManagerException {
        return null;
    }

    @Override
    public List<JobCategory> search_all(String s, int id_user) throws JobManagerException {
        return null;
    }
}
