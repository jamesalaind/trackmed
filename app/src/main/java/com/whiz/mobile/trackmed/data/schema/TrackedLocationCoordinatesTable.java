package com.whiz.mobile.trackmed.data.schema;

/**
 * Created by WEstrada on 4/28/2016.
 */
public class TrackedLocationCoordinatesTable extends SqliteTable {
    public TrackedLocationCoordinatesTable() {
        super("tblTrackedLocationCoordinates");

        addIntField("id", true);
        addStringField("address");
        addFloatField("latitude");
        addFloatField("longitude");
        addStringField("timestamp");
    }
}
