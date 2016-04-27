package com.whiz.mobile.trackmed.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by whizk on 02/13/2016.
 */
public class HttpRestfulApiRequest {
    private String mUrl;
    private String method;

    public HttpRestfulApiRequest(String url, String method) {
        this.method = method;
        this.mUrl = url;
    }

    public HttpResponse send() throws IOException {
        HttpURLConnection con;

        URL url = new URL(mUrl);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Authorization", "Token token=V2VuZGVsbDpzZWNyZXQ=");
        con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        con.setRequestProperty("Accept", "application/json;charset=utf-8");

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            con.disconnect();
            return new HttpResponse(sb.toString(), status);
        }
        return new HttpResponse(con.getResponseMessage(), status);
    }
}
