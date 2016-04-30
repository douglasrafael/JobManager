package com.fsdeveloper.jobmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Phone;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs all operations database for object of client.
 *
 * @author Created by Douglas Rafael on 29/04/2016.
 * @version 1.0
 */
public class ClientDao extends DBManager implements Dao<Client> {
    private Context context;

    private String[] columns = {
            DatabaseHelper.ID,
            DatabaseHelper.CLIENT_FIRST_NAME,
            DatabaseHelper.CLIENT_LAST_NAME,
            DatabaseHelper.EMAIL,
            DatabaseHelper.CLIENT_ADDRESS,
            DatabaseHelper.CLIENT_RATING,
            DatabaseHelper.USER_ID,
            DatabaseHelper.CREATED_AT
    };

    /**
     * Class constructor, create instance of class DatabaseHelper.
     *
     * @param context Abstract class whose implementation is provided by Android system.
     */
    public ClientDao(Context context) throws JobManagerException {
        super(context);
        this.context = context;
    }

    @Override
    public List<Client> list(int user_id) throws JobManagerException {
        List<Client> result = new ArrayList<>();
        mGetReadableDatabase();

        // Select all clients of the user
        Cursor cursor = db.query(DatabaseHelper.TABLE_CLIENT, columns, DatabaseHelper.USER_ID + "=?", new String[]{String.valueOf(user_id)}, null, null, DatabaseHelper.CLIENT_FIRST_NAME);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add(createClient(cursor));
            }
            cursor.close();
        }
        DBClose();

        return result;
    }

    @Override
    public Client getById(int _id) throws JobManagerException {
        mGetReadableDatabase();
        Client client = null;

        Cursor cursor = db.query(DatabaseHelper.TABLE_CLIENT, columns, DatabaseHelper.ID + "=?", new String[]{String.valueOf(_id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            client = createClient(cursor);

            cursor.close();
        }
        DBClose();

        return client;
    }

    @Override
    public int insert(Client o) throws JobManagerException {
        mGetWritableDatabase();
        long _id = 0;

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CLIENT_FIRST_NAME, o.getFirst_name());
        values.put(DatabaseHelper.CLIENT_LAST_NAME, o.getLast_name());
        values.put(DatabaseHelper.EMAIL, o.getEmail());
        values.put(DatabaseHelper.CLIENT_ADDRESS, o.getAddress());
        values.put(DatabaseHelper.CLIENT_RATING, o.getRating());
        values.put(DatabaseHelper.USER_ID, o.getUser_id());
        values.put(DatabaseHelper.CREATED_AT, o.getCreated_at());

        db.beginTransaction();
        try {
            // Inserting of client
            _id = db.insert(DatabaseHelper.TABLE_CLIENT, null, values);

            // Inserting client phones
            if (o.getPhoneList().size() > 0) {
                PhoneDao phone = new PhoneDao(context);
                for (Phone p : o.getPhoneList()) {
                    p.setClient_id((int) _id);
                    phone.insert(p);
                }
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            DBClose();
        }

        return (int) _id;
    }

    @Override
    public boolean update(Client o) throws JobManagerException {
        mGetWritableDatabase();

        Client old_client = getById(o.getId());

        // Checks for data to be actually changed.
        if (!old_client.equals(o)) {
            db.beginTransaction();

            try {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.CLIENT_FIRST_NAME, o.getFirst_name());
                values.put(DatabaseHelper.CLIENT_LAST_NAME, o.getLast_name());
                values.put(DatabaseHelper.EMAIL, o.getEmail());
                values.put(DatabaseHelper.CLIENT_ADDRESS, o.getAddress());
                values.put(DatabaseHelper.CLIENT_RATING, o.getRating());
                values.put(DatabaseHelper.USER_ID, o.getUser_id());
                values.put(DatabaseHelper.CREATED_AT, o.getCreated_at());

                // remove client phones
                if (old_client.getPhoneList().size() > 0) {
                    PhoneDao phone = new PhoneDao(context);
                    for (Phone p : o.getPhoneList()) {
                        phone.delete(p);
                    }
                }

                // inserting client phones
                if (o.getPhoneList().size() > 0) {
                    PhoneDao phone = new PhoneDao(context);
                    for (Phone p : o.getPhoneList()) {
                        phone.insert(p);
                    }
                }

                // update client
                int rowsAffected = db.update(DatabaseHelper.TABLE_CLIENT, values, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

                // Verifies that was successfully updated
                if (rowsAffected == 1) {
                    db.setTransactionSuccessful();
                    return true;
                }

            } finally {
                db.endTransaction();
                DBClose();
            }
        }

        return false;
    }

    @Override
    public boolean delete(Client o) throws JobManagerException {
        mGetWritableDatabase();

        try {
            int rowsAffected = db.delete(DatabaseHelper.TABLE_CLIENT, DatabaseHelper.ID + "=?", new String[]{String.valueOf(o.getId())});

            // Verifies that was successfully deleted
            if (rowsAffected == 1) {
                return true;
            }
        } finally {
            DBClose();
        }

        return false;
    }

    @Override
    public List<Client> search_all(String s, int _id) throws JobManagerException {
        return null;
    }

    @Override
    public int size(int _id) throws JobManagerException {
        return 0;
    }

    /**
     * Return built client.
     *
     * @param cursor It contains data.
     * @return The client.
     */
    private Client createClient(Cursor cursor) throws JobManagerException {
        Client client = new Client();

        client.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID)));
        client.setFirst_name(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CLIENT_FIRST_NAME)));
        client.setLast_name(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CLIENT_LAST_NAME)));
        client.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMAIL)));
        client.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CLIENT_ADDRESS)));
        client.setRating(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CLIENT_RATING)));
        client.setUser_id(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_ID)));
        client.setCreated_at(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CREATED_AT)));

        // Get list all the phone of client.
        client.setPhoneList(new PhoneDao(context).list(client.getId()));

        return client;
    }
}
