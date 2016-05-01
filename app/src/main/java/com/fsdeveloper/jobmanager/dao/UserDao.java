package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fsdeveloper.jobmanager.bean.User;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by Douglas Rafael on 24/04/2016.
 * @version 1.0
 */
public class UserDao extends DBManager implements Dao<User> {

    private String[] columns = {
            DatabaseHelper.ID,
            DatabaseHelper.NAME,
            DatabaseHelper.EMAIL,
            DatabaseHelper.USER_PASSWORD,
            DatabaseHelper.CREATED_AT,
            DatabaseHelper.USER_LAST_LOGIN
    };

    /**
     * Class constructor, create instance of class DatabaseHelper.
     *
     * @param context Abstract class whose implementation is provided by Android system.
     * @throws JobManagerException If there is a general exception of the system.
     * @throws ConnectionException If there is one exception of database connection.
     */
    public UserDao(Context context) throws JobManagerException, ConnectionException {
        super(context);
    }

    @Override
    public List<User> list(int id_user) throws JobManagerException, NullPointerException {
        return null;
    }

    @Override
    public User getById(int _id) throws JobManagerException {
        mGetReadableDatabase();
        User user = null;

        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, columns, DatabaseHelper.ID + "=?", new String[]{String.valueOf(_id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            user = createPhone(cursor);

            cursor.close();
        }
        DBClose();

        return user;
    }

    @Override
    public int insert(User o) throws JobManagerException {
        mGetWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME, o.getName());
        values.put(DatabaseHelper.EMAIL, o.getEmail());
        values.put(DatabaseHelper.USER_PASSWORD, o.getPassword());
        values.put(DatabaseHelper.CREATED_AT, o.getCreated_at());

        long _id = db.insert(DatabaseHelper.TABLE_USER, null, values);

        DBClose();

        return (int) _id;
    }

    @Override
    public boolean update(User o) throws JobManagerException {
        return false;
    }

    @Override
    public boolean delete(User o) throws JobManagerException {
        return false;
    }

    @Override
    public List<User> search_all(String s, int id_user) throws JobManagerException {
        return null;
    }

    @Override
    public int size(int id_user) throws JobManagerException {
        return 0;
    }

    /**
     * Return built user.
     *
     * @param cursor It contains data.
     * @return The user.
     */
    private User createPhone(Cursor cursor) {
        User user = new User();

        user.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_PASSWORD)));
        user.setCreated_at(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CREATED_AT)));
        user.setLast_login(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_LAST_LOGIN)));

        return user;
    }
}
