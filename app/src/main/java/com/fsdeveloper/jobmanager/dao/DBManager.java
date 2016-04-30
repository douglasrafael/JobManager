package com.fsdeveloper.jobmanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fsdeveloper.jobmanager.exception.JobManagerException;

/**
 * @author Created by Douglas Rafael on 30/04/2016.
 * @version 1.0
 */
public class DBManager {
    protected DatabaseHelper helper;
    protected SQLiteDatabase db;

    public DBManager(Context context) throws JobManagerException {
        try {
            if (helper == null)
                helper = new DatabaseHelper(context).getInstance();
        } catch (Exception ex) {
            throw new JobManagerException("Error database!");
        }
    }

    public SQLiteDatabase mGetWritableDatabase() {
        if (db == null || !db.isOpen()) {
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public SQLiteDatabase mGetReadableDatabase() {
        if (db == null || !db.isOpen()) {
            db = helper.getReadableDatabase();
        }
        return db;
    }

    public void DBClose() {
        if (db == null || db.isOpen()) {
            db.close();
        }
    }
}
