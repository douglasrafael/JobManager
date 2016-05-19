package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.User;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.tool.MyDataTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by Douglas Rafael on 24/04/2016.
 * @version 1.0
 */
public class UserDao extends DBManager implements Dao<User> {
    private Context context;

    private String[] columns = {
            DatabaseHelper.ID,
            DatabaseHelper.NAME,
            DatabaseHelper.EMAIL,
            DatabaseHelper.USER_PASSWORD,
            DatabaseHelper.IMAGE,
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
        this.context = context;
    }

    @Override
    public List<User> list(int id_user) throws JobManagerException, NullPointerException {
        List<User> result = new ArrayList<User>();
        mGetReadableDatabase();

        // Select all users
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, columns, null, null, null, null, DatabaseHelper.CREATED_AT + " DESC");

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(createUser(cursor));
            }
            cursor.close();
        }
        return result;
    }

    @Override
    public User getById(int _id) throws JobManagerException {
        mGetReadableDatabase();
        User user = null;

        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, columns, DatabaseHelper.ID + "=?", new String[]{String.valueOf(_id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            user = createUser(cursor);

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
        if (o.getEmail() != null && o.getEmail().length() > 0) {
            values.put(DatabaseHelper.EMAIL, o.getEmail());
        }
        values.put(DatabaseHelper.IMAGE, o.getImage());
        values.put(DatabaseHelper.USER_PASSWORD, o.getPassword());
        values.put(DatabaseHelper.CREATED_AT, MyDataTime.getDataTime(context.getResources().getString(R.string.date_time_bd)));

        long _id = db.insert(DatabaseHelper.TABLE_USER, null, values);

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
    private User createUser(Cursor cursor) {
        User user = new User();

        user.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_PASSWORD)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IMAGE)));
        user.setCreated_at(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CREATED_AT)));
        user.setLast_login(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_LAST_LOGIN)));

        return user;
    }
}
