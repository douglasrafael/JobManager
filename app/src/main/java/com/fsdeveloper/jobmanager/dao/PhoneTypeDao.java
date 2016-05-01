package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fsdeveloper.jobmanager.bean.PhoneType;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs all operations database for object of type PhoneType.
 *
 * @author Created by Douglas Rafael on 29/04/2016.
 * @version 1.0
 */
public class PhoneTypeDao extends DBManager implements Dao<PhoneType> {

    private String[] columns = {
            DatabaseHelper.ID,
            DatabaseHelper.TITLE
    };



    /**
     * Class constructor.
     *
     * @param context Abstract class whose implementation is provided by Android system.
     * @throws JobManagerException If there is a general exception of the system.
     * @throws ConnectionException If there is one exception of database connection.
     */
    public PhoneTypeDao(Context context) throws JobManagerException, ConnectionException {
        super(context);
    }

    @Override
    public List<PhoneType> list(int _id) throws JobManagerException {
        List<PhoneType> result = new ArrayList<>();
        mGetReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_PHONE_TYPE, columns, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                PhoneType type = new PhoneType();
                type.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
                type.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE)));

                result.add(type);
            }
            cursor.close();
        }

        return result;
    }

    @Override
    public PhoneType getById(int _id) throws JobManagerException {
        mGetReadableDatabase();
        PhoneType type = null;

        Cursor cursor = db.query(DatabaseHelper.TABLE_PHONE_TYPE, columns, DatabaseHelper.ID + "=?", new String[]{String.valueOf(_id)}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            type = new PhoneType(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE)));
            cursor.close();
        }

        return type;
    }

    @Override
    public int insert(PhoneType o) throws JobManagerException {
        mGetWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TITLE, o.getTitle());

        long _id = db.insert(DatabaseHelper.TABLE_PHONE_TYPE, null, values);

        return (int) _id;
    }

    @Override
    public boolean update(PhoneType o) throws JobManagerException {
        mGetWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TITLE, o.getTitle());

        int rowsAffected = db.update(DatabaseHelper.TABLE_PHONE_TYPE, values, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

        // Verifies that was successfully updated
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(PhoneType o) throws JobManagerException {
        mGetWritableDatabase();

        int rowsAffected = db.delete(DatabaseHelper.TABLE_PHONE_TYPE, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

        // Verifies that was successfully deleted
        return rowsAffected == 1;
    }

    @Override
    public List<PhoneType> search_all(String s, int _id) throws JobManagerException {
        return null;
    }

    @Override
    public int size(int _id) throws JobManagerException {
        return 0;
    }
}
