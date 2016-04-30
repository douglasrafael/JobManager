package com.fsdeveloper.jobmanager.dao;

import android.content.Context;
import android.content.Loader;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fsdeveloper.jobmanager.R;

import java.io.IOException;

/**
 * Create or update the database SQLite.
 *
 * @author Created by Douglas Rafael on 23/04/2016.
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static Resources res;
    private static Context context;
    private static DatabaseHelper myInstance;

    // Database name
    private static final String DATABASE_NAME = "job_manager";

    // Database version
    private static final int DATABASE_VERSION = 29;

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
    private final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            NAME + " VARCHAR(45) NOT NULL," + EMAIL + " VARCHAR(125) NOT NULL," + USER_PASSWORD + " VARCHAR(45)," + CREATED_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            USER_LAST_LOGIN + " TIMESTAMP, CONSTRAINT 'id_UNIQUE' UNIQUE(" + ID + "), CONSTRAINT 'email_UNIQUE' UNIQUE(" + EMAIL + "));";

    // Script to create the client table
    private final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENT + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            CLIENT_FIRST_NAME + " VARCHAR(45) NOT NULL," + CLIENT_LAST_NAME + " VARCHAR(45)," + EMAIL + " VARCHAR(125)," + CLIENT_ADDRESS + " VARCHAR(255)," +
            CLIENT_RATING + " INTEGER DEFAULT 0," + USER_ID + " INTEGER NOT NULL," + CREATED_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            "CONSTRAINT 'id_UNIQUE' UNIQUE(" + ID + "), CONSTRAINT 'fk_client_user' FOREIGN KEY(" + USER_ID + ") " +
            "REFERENCES " + TABLE_USER + "(" + ID + ") ON DELETE CASCADE ON UPDATE CASCADE);" +
            "CREATE INDEX '" + TABLE_CLIENT + ".fk_client_user_idx' ON " + TABLE_CLIENT + "(" + USER_ID + ");" +
            "CREATE INDEX '" + TABLE_CLIENT + ".first_name_idx' ON " + TABLE_CLIENT + "(" + CLIENT_FIRST_NAME + ");";

    // Script to create the job table
    private final String CREATE_TABLE_JOB = "CREATE TABLE " + TABLE_JOB + "(" + JOB_PROTOCOL + " INTEGER PRIMARY KEY," + TITLE + " VARCHAR(125) NOT NULL," +
            JOB_DESCRIPTION + " VARCHAR(545)," + JOB_NOTE + " VARCHAR(545)," + JOB_PRICE + " DOUBLE NOT NULL," + JOB_EXPENSE + " DOUBLE DEFAULT 0.0," + JOB_FINALIZED_AT + " TIMESTAMP," +
            CREATED_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," + JOB_UPDATE_AT + " TIMESTAMP," + USER_ID + " INTEGER NOT NULL," + CLIENT_ID + " INTEGER NOT NULL," +
            "CONSTRAINT 'id_UNIQUE' UNIQUE(" + JOB_PROTOCOL + "), CONSTRAINT 'fk_job_user' FOREIGN KEY(" + USER_ID + ") REFERENCES " + TABLE_USER + "(" + ID + ") ON DELETE CASCADE ON UPDATE CASCADE," +
            "CONSTRAINT 'fk_job_client' FOREIGN KEY(" + CLIENT_ID + ") REFERENCES " + TABLE_CLIENT + "(" + ID + ") ON DELETE CASCADE ON UPDATE CASCADE);" +
            "CREATE INDEX '" + TABLE_JOB + ".fk_job_user_idx' ON " + TABLE_JOB + "(" + USER_ID + ");" +
            "CREATE INDEX '" + TABLE_JOB + ".fk_job_client_idx' ON " + TABLE_JOB + "(" + CLIENT_ID + ");" +
            "CREATE INDEX '" + TABLE_JOB + ".title_idx' ON " + TABLE_JOB + "(" + TITLE + ");";

    // Script to create the job_category table
    private final String CREATE_TABLE_JOB_CATEGORY = "CREATE TABLE " + TABLE_JOB_CATEGORY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            NAME + " VARCHAR(45) NOT NULL, CONSTRAINT 'id_UNIQUE' UNIQUE(" + ID + "), CONSTRAINT 'name_UNIQUE' UNIQUE(" + NAME + "));" +
            "CREATE INDEX '" + TABLE_JOB_CATEGORY + ".name_idx' ON " + TABLE_JOB_CATEGORY + "(" + NAME + ");";

    // Script to create the job_has_job_category table
    private final String CREATE_TABLE_JOB_HAS_JOB_CATEGORY = "CREATE TABLE " + TABLE_JOB_HAS_JOB_CATEGORY + "(" + JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL + " INTEGER NOT NULL," +
            JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID + " INTEGER NOT NULL, PRIMARY KEY(" + JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL + "," + JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID + ")," +
            "CONSTRAINT 'fk_job_has_job_category_job' FOREIGN KEY(" + JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL + ") REFERENCES " + TABLE_JOB + "(" + JOB_PROTOCOL + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
            "CONSTRAINT 'fk_job_has_job_category_job_category' FOREIGN KEY(" + JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID + ") REFERENCES " + TABLE_JOB_CATEGORY + "(" + ID + "));" +
            "CREATE INDEX '" + TABLE_JOB_HAS_JOB_CATEGORY + ".fk_job_has_job_category_job_category_idx' ON " + TABLE_JOB_HAS_JOB_CATEGORY + "(" + JOB_HAS_JOB_CATEGORY_JOB_CATEGORY_ID + ");" +
            "CREATE INDEX '" + TABLE_JOB_HAS_JOB_CATEGORY + ".fk_job_has_job_category_job_idx' ON " + TABLE_JOB_HAS_JOB_CATEGORY + "(" + JOB_HAS_JOB_CATEGORY_JOB_PROTOCOL + ");";

    // Script to create the phone_type table
    private final String CREATE_TABLE_PHONE_TYPE = "CREATE TABLE " + TABLE_PHONE_TYPE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            TITLE + " VARCHAR(45) NOT NULL, CONSTRAINT 'id_UNIQUE' UNIQUE(" + ID + "), " +
            "CONSTRAINT 'title_UNIQUE' UNIQUE(" + TITLE + "));";

    // Script to create the phone table
    private final String CREATE_TABLE_PHONE = "CREATE TABLE " + TABLE_PHONE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            PHONE_NUMBER + " VARCHAR(25) NOT NULL," + CLIENT_ID + " INTEGER NOT NULL," + PHONE_TYPE_ID + " INTEGER NOT NULL," +
            "CONSTRAINT 'id_UNIQUE' UNIQUE(" + ID + "), CONSTRAINT 'fk_phone_client' FOREIGN KEY(" + CLIENT_ID + ") REFERENCES " + TABLE_CLIENT + "(" + ID + ") " +
            "ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT 'fk_phone_phone_type' FOREIGN KEY(" + PHONE_TYPE_ID + ") REFERENCES " + TABLE_PHONE_TYPE + "(" + ID + "));" +
            "CREATE INDEX '" + TABLE_PHONE + ".fk_phone_client_idx' ON " + TABLE_PHONE + "(" + CLIENT_ID + ");" +
            "CREATE INDEX '" + TABLE_PHONE + ".fk_phone_phone_type_idx' ON " + TABLE_PHONE + "(" + PHONE_TYPE_ID + ");";


    /**
     * Constructor method that creates the database or updated if necessary, according to the version
     *
     * @param context Class abstract provided by android.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        res = context.getResources();
    }

    /**
     * Get instance.
     *
     * @param context Class abstract provided by android.
     * @return The instance.
     */

    /**
     * Retrieves a thread-safe instance of the singleton object and opens the database
     * with writing permissions.
     *
     * @return the singleton instance.
     */
    public static synchronized DatabaseHelper getInstance() {
        if (myInstance == null) {
            myInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return myInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_ALL = CREATE_TABLE_USER + CREATE_TABLE_CLIENT + CREATE_TABLE_JOB_CATEGORY + CREATE_TABLE_JOB + CREATE_TABLE_JOB_HAS_JOB_CATEGORY +
                CREATE_TABLE_PHONE_TYPE + CREATE_TABLE_PHONE + insertCategories() + insertTypesPphone();

        sqLiteDatabase.beginTransaction();
        try {
            // Note. "ExecSQL" only runs per line ;
            for (String s : SQL_ALL.split(";")) {
                // Creating required tables
                sqLiteDatabase.execSQL(s);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException sqle) {
            Log.e("DB", sqle.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // On upgrade drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONE_TYPE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_HAS_JOB_CATEGORY);

        // Create new tables
        onCreate(sqLiteDatabase);
    }

    /**
     * Insert categories default.
     *
     * @return SQL inserting categories default.
     */
    public String insertCategories() {
        String query = "";

        String[] categoriesDefault = res.getStringArray(R.array.categories_default);
        for (int i = 0; i < categoriesDefault.length; i++) {
            // Script inserting categories default
            query += "INSERT INTO " + TABLE_JOB_CATEGORY + "(" + ID + "," + NAME + ") VALUES(" + (i + 1) + ",'" + categoriesDefault[i] + "');";
        }
        return query;
    }

    public String insertTypesPphone() {
        String query = "";

        String[] typesPhoneDefault = res.getStringArray(R.array.types_phone);
        for (int i = 0; i < typesPhoneDefault.length; i++) {
            // Script inserting types phone default
            query += "INSERT INTO " + TABLE_PHONE_TYPE + "(" + ID + "," + TITLE + ") VALUES(" + (i + 1) + ",'" + typesPhoneDefault[i] + "');";
        }

        return query;
    }
}
