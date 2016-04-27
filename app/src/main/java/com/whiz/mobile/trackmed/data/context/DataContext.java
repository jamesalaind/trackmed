package com.whiz.mobile.trackmed.data.context;

/**
 * Created by whizk on 02/15/2016.
 */
public abstract class DataContext {
    protected String resourceUrl;
    protected String method;

    public abstract void load();

    public void fetch() {
        //@Todo: Complete this.
    }
}
