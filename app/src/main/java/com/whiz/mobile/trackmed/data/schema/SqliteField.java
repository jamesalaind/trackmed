package com.whiz.mobile.trackmed.data.schema;

/**
 * Created by WEstrada on 4/28/2016.
 */
public class SqliteField {
    private String name;
    private String dataType;
    private boolean isPrimaryKey;

    public SqliteField(String name, String dataType, boolean isPrimaryKey) {
        this.name = name;
        this.dataType = dataType;
        this.isPrimaryKey = isPrimaryKey;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public SqliteField(String name, String dataType) {
        this(name, dataType, false);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
