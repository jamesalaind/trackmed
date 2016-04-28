package com.whiz.mobile.trackmed.data.store;

import android.content.Context;
import android.database.Cursor;

import com.whiz.mobile.trackmed.data.DatabaseManager;
import com.whiz.mobile.trackmed.data.SqliteHelper;
import com.whiz.mobile.trackmed.data.schema.SqliteTable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WEstrada on 4/28/2016.
 */
public class DataSource<T> {
    private Context context;
    private SqliteTable table;
    protected DatabaseManager DM = DatabaseManager.getInstance();

    public DataSource(Context context, SqliteTable table) {
        this.table = table;
        this.context = context;
        SqliteHelper helper = new SqliteHelper(context);
        DM.initialize(helper);
    }

    public void close() {
        DM.closeDatabase();
    }

    public void open() {
        DM.openDatabase();
    }

    public List<T> getAllMapped() {
        List<T> list = new ArrayList<>();

        Cursor cursor = DM.getDatabase().query(table.getName(), table.getFieldsArray(), null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            T t =
        }
        return list;
    }

    public T mapCursor(Method method, Cursor cursor) {
        method.invoke()
    }
}
