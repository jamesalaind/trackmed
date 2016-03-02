package com.jeonsoft.mobile.essbuddy.globals;

/**
 * Created by whizk on 02/15/2016.
 */
public final class GlobalConstants {
    public static final String BASE_URL = "/api/v1/";

    private String rootUrl;

    public String getRootUrl() {
        return this.rootUrl;
    }

    public void setRootUrl(String url) {
        this.rootUrl = url;
    }
}
