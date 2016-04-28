package com.whiz.mobile.trackmed.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by WEstrada on 4/28/2016.
 */
public final class DatabaseManager {
    private int openCounter;

    private static DatabaseManager instance;
    private static SqliteHelper sqliteHelper;
    private SQLiteDatabase db;

    public static synchronized void initialize(SqliteHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            sqliteHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName()
                    .concat(" is not initialized, call initialize() method first."));
        }
        return instance;
    }

    public synchronized void openDatabase() {
        openCounter++;
        if (openCounter == 0) {
            db = sqliteHelper.getWritableDatabase();
        }
    }

    public synchronized void closeDatabase() {
        openCounter--;
        if (openCounter == 0) {
            db.close();
        }
    }

    public SQLiteDatabase getDatabase() {
        return db;
    }
}
