package com.fsdeveloper.jobmanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Create or update the database SQLite.
 *
 * @author Created by Douglas Rafael on 23/04/2016.
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "job_manager";

    // Database version
    private static final int DATABASE_VERSION = 8;

    // Database tables
    public static final String TABLE_USER = "user";
    public static final String TABLE_CLIENT = "client";
    public static final String TABLE_JOB = "job";
    public static final String TABLE_JOB_CATEGORY = "job_category";
    public static final String TABLE_JOB_HAS_JOB_CATEGORY = "job_has_job_category";
    public static final String TABLE_PHONE_TYPE = "phone_type";
    public static final String TABLE_PHONE = "phone";

    // Columns in common tables
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String TITLE = "title";
    public static final String EMAIL = "email";
    public static final String CREATED_AT = "created_at";
    public static final String USER_ID = "user_id";
    public static final String CLIENT_ID = "client_id";

    // Columns user table
    public static final String USER_PASSWORD = "password";
    public static final String USER_LAST_LOGIN = "last_login";

    // Columns client table
    public static final String CLIENT_FIRST_NAME = "first_name";
    public static final String CLIENT_LAST_NAME = "last_name";
    public static final String CLIENT_ADDRESS = "address";
    public static final String CLIENT_RATING = "rating";

    // Columns job table
    public static final String JOB_PROTOCOL = "protocol";
    public static final String JOB_DESCRIPTION = "description";
    public static final String JOB_NOTE = "note";
    public static final String JOB_PRICE = "price";
    public static final String JOB_EXPENSE = "expense";
    public static final String JOB_FINALIZED_AT = "finalized_at";
    public static final String JOB_UPDATE_AT = "updated_at";

    // Columns job_category table
    // present in columns in common (_id and name)

    // Columns job_has_job_category table
    public static final String JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL = "job_protocol";
    public static final String JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID = "job_category_id";

    // Columns type_phone table
    // present in columns in common (_id and title)

    // Columns phone table
    public static final String PHONE_NUMBER = "number";
    public static final String PHONE_TYPE_ID = "phone_type_id";

    // Script to create the user table
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            NAME + " VARCHAR(45) NOT NULL," + EMAIL + " VARCHAR(125) NOT NULL," + USER_PASSWORD + " VARCHAR(45)," + CREATED_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            USER_LAST_LOGIN + " TIMESTAMP, CONSTRAINT 'id_UNIQUE' UNIQUE(" + ID + "));";

    // Script to create the client table
    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENT + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            CLIENT_FIRST_NAME + " VARCHAR(45) NOT NULL," + CLIENT_LAST_NAME + " VARCHAR(45)," + EMAIL + " VARCHAR(125)," + CLIENT_ADDRESS + " VARCHAR(255)," +
            CLIENT_RATING + " INTEGER DEFAULT 0," + USER_ID + " INTEGER NOT NULL," + CREATED_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            "CONSTRAINT 'id_UNIQUE' UNIQUE(" + ID + "), CONSTRAINT 'fk_client_user' FOREIGN KEY(" + USER_ID + ") " +
            "REFERENCES " + TABLE_USER + "(" + ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    /**
     * Constructor method that creates the database or updated if necessary, according to the version
     *
     * @param context Class abstract provided by android
     */
    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creating required tables
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_CLIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // On upgrade drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);

        // Create new tables
        onCreate(sqLiteDatabase);
    }
}
