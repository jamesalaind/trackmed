package com.whiz.mobile.trackmed.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whiz.mobile.trackmed.data.schema.DoctorsTable;
import com.whiz.mobile.trackmed.data.schema.TrackedLocationCoordinatesTable;

/**
 * Created by WEstrada on 4/28/2016.
 */
public class SqliteHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "trackmed_db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String BLOB_TYPE = " BLOB";
    public static final String INT_TYPE = " INTEGER";
    public static final String BIG_INT_TYPE = " BIGINT";
    public static final String DATETIME_TYPE = " DATETIME";
    public static final String REAL_TYPE = " REAL";
    public static final String COMMA_SEP = ",";
    private Context context;

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new DoctorsTable().createTable(db);
        new TrackedLocationCoordinatesTable().createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        new DoctorsTable().dropTable(db);
        new TrackedLocationCoordinatesTable().dropTable(db);
        onCreate(db);
    }
}
