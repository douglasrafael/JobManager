package com.fsdeveloper.jobmanager.dao;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Created by Douglas Rafael on 23/04/2016.
 * @version 1.0
 */
public class JobCategoryDaoTest extends AndroidTestCase {
    JobCategoryDao category;

    @Before
    public void setUp() throws Exception {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        category = new JobCategoryDao(context);
    }

    @Test
    public void testList() throws Exception {
        System.out.println(category.list(1).toString());
    }

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testSearch() throws Exception {

    }

    @Test
    public void testSearch_all() throws Exception {

    }
}