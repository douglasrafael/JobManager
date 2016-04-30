package com.fsdeveloper.jobmanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.PhoneType;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

/**
 * @author Created by Douglas Rafael on 30/04/2016.
 * @version 1.0
 */
public class Manager {
    DatabaseHelper helper;
    private static SQLiteDatabase db;
    PhoneTypeDao phoneType;
    PhoneDao phone;
    ClientDao client;

    public Manager(Context context) throws JobManagerException {
        client = new ClientDao(context);
        phone = new PhoneDao(context);
        phoneType = new PhoneTypeDao(context);
    }


    public int insertClient(Client c) throws JobManagerException {
        return client.insert(c);
    }

    public Client getClient(int _id) throws  JobManagerException {
        return  client.getById(_id);
    }

    public PhoneType getPhoneType(int _id) throws JobManagerException {
        return phoneType.getById(_id);
    }
}
