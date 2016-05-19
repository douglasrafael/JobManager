package com.fsdeveloper.jobmanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

/**
 * @author Created by Douglas Rafael on 30/04/2016.
 * @version 1.0
 */
public class DBManager {
    protected DatabaseHelper helper;
    protected SQLiteDatabase db;

    /**
     * Get instance of class DatabaseHelper.
     *
     * @param context Abstract class whose implementation is provided by Android system.
     * @throws ConnectionException If there is one exception of database connection.
     */
    public DBManager(Context context) throws ConnectionException {
        try {
            if (helper == null)
                helper = new DatabaseHelper(context).getInstance();
        } catch (Exception ex) {
            throw new ConnectionException("Error creating connection instance!");
        }
    }

    /**
     * Opens write permission to the database.
     * Only opens if not open.
     *
     * @return SQLiteDatabase The write permission.
     */
    public SQLiteDatabase mGetWritableDatabase() {
        if (db == null || !db.isOpen()) {
            db = helper.getWritableDatabase();
        }
        return db;
    }

    /**
     * Opens read permission to the database.
     * Only opens if not open.
     *
     * @return SQLiteDatabase The read permission.
     */
    public SQLiteDatabase mGetReadableDatabase() {
        if (db == null || !db.isOpen()) {
            db = helper.getReadableDatabase();
        }
        return db;
    }

    /**
     * Closes the database connection.
     * Only closes if it is open.
     */
    public void DBClose() {
        if (db != null && DBIsOpen()) {
            db.close();
        }
    }

    /**
     * Checks whether the connection is open.
     *
     * @return True if is open or False otherwise.
     */
    public boolean DBIsOpen() {
        if (db != null && db.isOpen()) {
            return true;
        }
        return false;
    }
}
