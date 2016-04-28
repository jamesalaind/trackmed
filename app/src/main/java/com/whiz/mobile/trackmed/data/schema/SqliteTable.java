package com.whiz.mobile.trackmed.data.schema;

import android.database.sqlite.SQLiteDatabase;

import com.whiz.mobile.trackmed.data.SqliteHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by WEstrada on 4/28/2016.
 */
public abstract class SqliteTable {
    private String name;
    private HashMap<String, SqliteField> fields;

    public SqliteTable(String name) {
        this.name = name;
        this.fields = new HashMap<>();
    }

    public SqliteField addField(SqliteField field) {
        return fields.put(field.getName(), field);
    }

    public SqliteField addField(String name, String dataType, boolean isPrimaryKey) {
        SqliteField field = new SqliteField(name, dataType, isPrimaryKey);
        this.addField(field);
        return field;
    }

    public SqliteField addField(String name, String dataType) {
        return this.addField(name, dataType, false);
    }

    public SqliteField addIntField(String name, boolean isPrimaryKey) {
        return this.addField(name, SqliteHelper.INT_TYPE, isPrimaryKey);
    }

    public SqliteField addIntField(String name) {
        return this.addIntField(name, false);
    }

    public SqliteField addFloatField(String name) {
        return this.addField(name, SqliteHelper.REAL_TYPE);
    }

    public SqliteField addStringField(String name) {
        return this.addField(name, SqliteHelper.TEXT_TYPE);
    }

    public SqliteField addDateField(String name) {
        return this.addField(name, SqliteHelper.DATETIME_TYPE);
    }

    public SqliteField addBigIntField(String name, boolean isPrimaryKey) {
        return this.addField(name, SqliteHelper.BIG_INT_TYPE, isPrimaryKey);
    }

    public SqliteField addBlobField(String name) {
        return this.addField(name, SqliteHelper.BLOB_TYPE);
    }

    public void removeField(String name) {
        fields.remove(name);
    }

    public SqliteField getField(String name) {
        return fields.get(name);
    }

    public String generateScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ".concat(name).concat(" ("));

        Set set = fields.entrySet();
        Iterator i = set.iterator();
        boolean hasPrimaryKey = false;
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            SqliteField field = (SqliteField) me.getKey();
            String primaryKey = "";
            if (field.isPrimaryKey() && !hasPrimaryKey) {
                primaryKey = " PRIMARY KEY AUTOINCREMENT";
                hasPrimaryKey = true;
            }
            sb.append(field.getName().concat(" ").concat(field.getDataType()).concat(primaryKey).concat(SqliteHelper.COMMA_SEP));
        }
        sb.append(")");
        return sb.toString();
    }

    public String generateDropScript() {
        return "DROP TABLE IF EXISTS " + name;
    }

    public String createTable(SQLiteDatabase db) {
        String sql = generateScript();
        db.execSQL(sql);
        return sql;
    }

    public String dropTable(SQLiteDatabase db) {
        String sql = generateDropScript();
        db.execSQL(sql);
        return sql;
    }
}
