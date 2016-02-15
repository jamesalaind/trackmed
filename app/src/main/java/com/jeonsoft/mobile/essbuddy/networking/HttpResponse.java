package com.jeonsoft.mobile.essbuddy.networking;

/**
 * Created by whizk on 01/21/2016.
 */
public class HttpResponse {
    private String responseMessage;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    private int responseCode;

    public HttpResponse(String responseMessage, int responseCode) {
        this.responseMessage = responseMessage;
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "Response Code: " + String.valueOf(responseCode) + ", Message: " + responseMessage;
    }
}
