package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fsdeveloper.jobmanager.bean.User;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * @author Created by Douglas Rafael on 24/04/2016.
 * @version 1.0
 */
public class UserDao implements Dao<User> {
    DatabaseHelper helper;
    SQLiteDatabase db;

    public UserDao(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    @Override
    public List<User> list(int id_user) throws JobManagerException {
        db = helper.getReadableDatabase();
        List<User> result = new ArrayList<User>();;
        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_USER + " WHERE " + DatabaseHelper.ID + "=" + id_user;
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor != null) {
            User user = new User();
            cursor.moveToLast();
            user.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_PASSWORD)));
            user.setCreated_at(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CREATED_AT)));
            user.setLast_login(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_LAST_LOGIN)));

            result.add(user);
        }
        cursor.close();
        db.close();

        return result;
    }

    @Override
    public void save(User o) throws JobManagerException {
        if(!o.equals(null)) {
            db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.NAME, o.getName());
            values.put(DatabaseHelper.EMAIL, o.getEmail());
            values.put(DatabaseHelper.USER_PASSWORD, o.getPassword());
            values.put(DatabaseHelper.CREATED_AT, o.getCreated_at());

            // insert row
            db.insert(DatabaseHelper.TABLE_USER, null, values);
            db.close();
        }
    }

    @Override
    public void update(User o) throws JobManagerException {

    }

    @Override
    public boolean delete(User o) throws JobManagerException {
        return false;
    }

    @Override
    public User search(int id) throws JobManagerException {
        return null;
    }

    @Override
    public List<User> search_all(String s, int id_user) throws JobManagerException {
        return null;
    }

}
