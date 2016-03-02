package com.jeonsoft.mobile.essbuddy.utils;

import com.jeonsoft.mobile.essbuddy.networking.HttpResponse;

/**
 * Created by whizk on 02/13/2016.
 */
public interface AsyncHttpRequestListener {
    void request();
    void complete(HttpResponse response);
    void failure(HttpResponse response, String message);
}
