package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fsdeveloper.jobmanager.bean.Phone;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs all operations database for object of type Phone.
 *
 * @author Created by Douglas Rafael on 29/04/2016.
 * @version 1.0
 */
public class PhoneDao extends DBManager implements Dao<Phone> {
    private Context context;

    private String[] columns = {
            DatabaseHelper.ID,
            DatabaseHelper.PHONE_NUMBER,
            DatabaseHelper.CLIENT_ID,
            DatabaseHelper.PHONE_TYPE_ID
    };

    /**
     * Class constructor.
     *
     * @param context Abstract class whose implementation is provided by Android system.
     * @throws JobManagerException If there is a general exception of the system.
     * @throws ConnectionException If there is one exception of database connection.
     */
    public PhoneDao(Context context) throws JobManagerException, ConnectionException {
        super(context);
        this.context = context;
    }

    @Override
    public List<Phone> list(int client_id) throws JobManagerException {
        List<Phone> result = new ArrayList<>();
        mGetReadableDatabase();

        // Select all the numbers of client phones
        Cursor cursor = db.query(DatabaseHelper.TABLE_PHONE, columns, DatabaseHelper.CLIENT_ID + "=?", new String[]{String.valueOf(client_id)}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(createPhone(cursor));
            }
            cursor.close();
        }

        return result;
    }

    @Override
    public Phone getById(int _id) throws JobManagerException {
        mGetReadableDatabase();
        Phone phone = null;

        Cursor cursor = db.query(DatabaseHelper.TABLE_PHONE, columns, DatabaseHelper.ID + "=?", new String[]{String.valueOf(_id)}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            phone = createPhone(cursor);

            cursor.close();
        }

        return phone;
    }

    @Override
    public int insert(Phone o) throws JobManagerException {
        mGetWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PHONE_NUMBER, o.getNumber());
        values.put(DatabaseHelper.CLIENT_ID, o.getClient_id());
        values.put(DatabaseHelper.PHONE_TYPE_ID, o.getType().getId());

        long _id = db.insert(DatabaseHelper.TABLE_PHONE, null, values);

        return (int) _id;
    }

    @Override
    public boolean update(Phone o) throws JobManagerException {
        mGetWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PHONE_NUMBER, o.getNumber());
        values.put(DatabaseHelper.CLIENT_ID, o.getClient_id());
        values.put(DatabaseHelper.PHONE_TYPE_ID, o.getType().getId());

        int rowsAffected = db.update(DatabaseHelper.TABLE_PHONE, values, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

        // Verifies that was successfully updated
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(Phone o) throws JobManagerException {
        mGetWritableDatabase();

        int rowsAffected = db.delete(DatabaseHelper.TABLE_PHONE, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

        // Verifies that was successfully deleted
        return rowsAffected == 1;
    }

    @Override
    public List<Phone> search_all(String s, int _id) throws JobManagerException {
        return null;
    }

    @Override
    public int size(int _id) throws JobManagerException {
        return 0;
    }

    /**
     * Return built phone.
     *
     * @param cursor It contains data.
     * @return The Phone.
     * @throws JobManagerException If there is a general exception of the system.
     */
    private Phone createPhone(Cursor cursor) throws JobManagerException {
        Phone phone = new Phone();

        try {
            PhoneTypeDao type = new PhoneTypeDao(context);

            phone.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
            phone.setNumber(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PHONE_NUMBER)));
            phone.setClient_id(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CLIENT_ID)));
            phone.setType(type.getById(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PHONE_TYPE_ID))));
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return phone;
    }
}
