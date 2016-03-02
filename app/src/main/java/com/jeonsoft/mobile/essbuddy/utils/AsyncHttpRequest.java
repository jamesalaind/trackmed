package com.jeonsoft.mobile.essbuddy.utils;

import android.os.AsyncTask;

import com.jeonsoft.mobile.essbuddy.networking.HttpRequest;
import com.jeonsoft.mobile.essbuddy.networking.HttpResponse;
import com.jeonsoft.mobile.essbuddy.networking.HttpRestfulApiRequest;

/**
 * Created by whizk on 02/13/2016.
 */
public class AsyncHttpRequest extends AsyncTask<String, Integer, HttpResponse> {
    private String url, method;
    private AsyncHttpRequestListener requestListener;

    public AsyncHttpRequest(String url, String method) {
        this(url, method, null);
    }

    public AsyncHttpRequest(String url, String method, AsyncHttpRequestListener requestListener) {
        this.url = url;
        this.method = method;
        this.requestListener = requestListener;
    }

    @Override
    protected HttpResponse doInBackground(String... params) {
        HttpRestfulApiRequest request = new HttpRestfulApiRequest(url, method);
        HttpResponse response = null;
        try {
            response = request.send();
            return response;
        } catch (Exception ex) {
            if (requestListener != null)
                requestListener.failure(response, ex.getMessage());
        }
        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse response) {
        super.onPostExecute(response);
        if (response != null && !(response.getResponseCode() == 200 || response.getResponseCode() == 204)) {
            if (requestListener != null)
                requestListener.failure(response, response.getResponseMessage());
        } else {
            if (requestListener != null)
                requestListener.complete(response);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (requestListener != null)
            requestListener.request();
    }
}
