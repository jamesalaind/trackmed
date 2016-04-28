package com.whiz.mobile.trackmed.data.schema;

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
}
