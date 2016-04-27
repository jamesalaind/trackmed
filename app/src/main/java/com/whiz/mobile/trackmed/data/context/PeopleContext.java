package com.whiz.mobile.trackmed.data.context;

/**
 * Created by whizk on 02/15/2016.
 */
public final class PeopleContext {
    private static PeopleContext instance;

    private PeopleContext() {

    }

    public static PeopleContext getInstance() {
        if (instance == null)
            instance = new PeopleContext();
        return instance;
    }
}
