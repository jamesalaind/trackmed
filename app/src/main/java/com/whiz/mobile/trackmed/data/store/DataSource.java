package com.whiz.mobile.trackmed.data.store;

import android.content.Context;

import com.whiz.mobile.trackmed.data.DatabaseManager;
import com.whiz.mobile.trackmed.data.SqliteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WEstrada on 4/28/2016.
 */
public class DataSource<T> {
    private Context context;
    protected DatabaseManager DM = DatabaseManager.getInstance();

    public DataSource(Context context) {
        SqliteHelper helper = new SqliteHelper(context);
        DM.initialize(helper);
    }

    public void close() {
        DM.closeDatabase();
    }

    public void open() {
        DM.openDatabase();
    }

    public List<T> get() {
        List<T> list = new ArrayList<>();

        return list;
    }
}
