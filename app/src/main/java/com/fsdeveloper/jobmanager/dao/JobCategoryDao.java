package com.fsdeveloper.jobmanager.dao;

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
public class JobCategoryDao implements Dao<JobCategory> {


    @Override
    public List<JobCategory> list(int id_user) throws JobManagerException {
        List<JobCategory> result = new ArrayList<>();

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
