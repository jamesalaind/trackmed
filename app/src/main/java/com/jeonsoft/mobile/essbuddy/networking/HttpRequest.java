package com.jeonsoft.mobile.essbuddy.networking;

import android.os.Environment;

import com.jeonsoft.mobile.essbuddy.log.AppLogger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by whizk on 01/20/2016.
 */
public class HttpRequest {
    private String mUrl;
    private String mMethod;
    private static final int READ_TIME_OUT = 10000;
    private static final int CONNECT_TIME_OUT = 15000;
    private static final String BOUNDARY = "*****";
    private static final String TWO_HYPHENS = "--";
    private static final String LINE_FEED = "\r\n";

    public HttpRequest(String url, String method) {
        this.mUrl = url;
        this.mMethod = method;
    }

    public int sendRequest() throws IOException {
        int response = 400;
        String filename = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/vid.mp4";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024;

        URL url = new URL(mUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(READ_TIME_OUT);
        con.setConnectTimeout(CONNECT_TIME_OUT);
        con.setRequestMethod(mMethod);
        con.addRequestProperty("Authorization", "Token token=V2VuZGVsbDpzZWNyZXQ=");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setChunkedStreamingMode(1024);
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);

        /*Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("employee_code", "9999");
        builder.appendQueryParameter("last_name", "wendell");
        builder.appendQueryParameter("first_name", "wayne");
        builder.appendQueryParameter("company_id", "1");
        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(builder.build().getEncodedQuery());
        writer.flush();*/

        DataOutputStream outputStream;
        outputStream = new DataOutputStream(con.getOutputStream());
        outputStream.writeBytes(twoHyphens + boundary + lineEnd);

        String conStr;
        conStr = "Content-Disposition: form-data; name=\"UploadFile\";filename=\"" + filename + "\"" + lineEnd;
        outputStream.writeBytes(conStr);
        outputStream.writeBytes(lineEnd);
        outputStream.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(filename) + lineEnd);
        outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
        outputStream.writeBytes(lineEnd);
        outputStream.writeBytes("--" + boundary + "--" + lineEnd);

        FileInputStream fileInputStream = new FileInputStream(new File(filename));
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        AppLogger.logE("File Length: " + bytesAvailable);
        try {
            while(bytesRead > 0) {
                try {
                    outputStream.write(buffer, 0, bufferSize);
                } catch (OutOfMemoryError e) {
                    AppLogger.logE(e.getMessage());
                    return 400;
                }
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
        } catch (Exception e) {
            AppLogger.logE(e.getMessage());
            return 400;
        }
        outputStream.writeBytes(lineEnd);
        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        /*writer.close();
        os.close();*/
        fileInputStream.close();

        //con.connect();
        response = con.getResponseCode();
        AppLogger.logE(con.getResponseMessage());

        if (response == HttpURLConnection.HTTP_OK) {
            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            AppLogger.logE(sb.toString());
            br.close();
        }

        return response;
    }

    /*
        RFC 2388 multipart/form-data
     */
    public HttpResponse submitRequest() throws IOException {
        HttpURLConnection con;
        DataOutputStream os;

        URL url = new URL(mUrl);
        con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setChunkedStreamingMode(1024);
        con.setRequestMethod(mMethod);
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("ENCTYPE", "multipart/form-data");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        con.setRequestProperty("Authorization", "Token token=V2VuZGVsbDpzZWNyZXQ=");
        String filename = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/vid.mp4";
        File f = new File(filename);
        if (!f.exists()) {
            AppLogger.logE("File not found.");
            return new HttpResponse("File not found.", 500);
        }

        FileInputStream fis = new FileInputStream(filename);
        con.setRequestProperty("Content-Length", String.valueOf(fis.available()));
        os = new DataOutputStream(con.getOutputStream());
        os.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_FEED);
        os.writeBytes("Content-Disposition: form-data; name=\"uploadedFile\";filename=\"" + filename + "\"" + LINE_FEED);
        os.writeBytes(LINE_FEED);

        int bytesRead;
        int bytesAvailable = fis.available();
        int bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024;
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];
        bytesRead = fis.read(buffer, 0, bufferSize);
        try {
            while(bytesRead > 0) {
                try {
                    os.write(buffer, 0, bufferSize);
                } catch (OutOfMemoryError e) {
                    AppLogger.logE(e.getMessage());
                    return new HttpResponse(e.getMessage(), 500);
                }
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fis.read(buffer, 0, bufferSize);
                AppLogger.logE("Byte(s) read: " + String.valueOf(bytesRead));
            }
        } catch (Exception e) {
            return new HttpResponse(e.getMessage(), 500);
        }
        os.writeBytes(LINE_FEED);
        os.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_FEED);
        int responseCode = con.getResponseCode();
        String responseMessage = con.getResponseMessage();

        os.flush();
        os.close();
        fis.close();
        return new HttpResponse(responseMessage, responseCode);
    }
}
