package com.whiz.mobile.trackmed.data.schema;

import android.database.Cursor;

/**
 * Created by WEstrada on 4/28/2016.
 */
public class DoctorsTable extends SqliteTable {
    public DoctorsTable() {
        super("tblDoctors");

        addIntField("id", true);
        addStringField("name");
        addStringField("address");
        addFloatField("latitude");
        addFloatField("longitude");
    }

    @Override
    public Object getEntityFromCursor(Cursor cursor) {
        return null;
    }
}
