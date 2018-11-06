package com.deinteti.gb.cricmodulemovil10.NetServices;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;

/**
 * Created by desarrollo on 09/03/2018.
 */

public class NetServicesUtils {
    private String LOG_TAG = "DefaultLogCricModule";

    public NetServicesUtils(String LOG_TAG) {
        this.LOG_TAG = LOG_TAG;
    }

    public String ConvertStreamToString(InputStream stream) {

        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();

        String line = null;
        try {

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in ConvertStreamToString", e);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in ConvertStreamToString", e);
        } finally {

            try {
                stream.close();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in ConvertStreamToString", e);

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in ConvertStreamToString", e);
            }
        }
        return response.toString();
    }

    private String getPostDataString(LinkedHashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (LinkedHashMap.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public InputStream performPostCall(String requestURL,
                                       JSONObject postDataParams) throws IOException {
        URL url;
        InputStream responseIS = null;
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Pigeon");
            conn.setChunkedStreamingMode(0);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(postDataParams.toString().getBytes());
            out.flush();
            int responseCode = conn.getResponseCode();
            //if response code is 200 / OK then read Inputstream
            if (responseCode == HttpURLConnection.HTTP_OK) {
                responseIS = conn.getInputStream();
            }
        } catch (Exception e) {
            throw e;
        }
        return responseIS;
    }

    public InputStream ByGetMethod(String ServerURL, boolean withBody) throws IOException {

        InputStream DataInputStream = null;
        try {

            URL url = new URL(ServerURL);
            HttpURLConnection cc = (HttpURLConnection)
                    url.openConnection();
            if (withBody) {
                cc.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                cc.setRequestProperty("Accept", "application/json");
                cc.setRequestProperty("User-Agent", "Pigeon");
                cc.setChunkedStreamingMode(0);
            }
            //set timeout for reading InputStream
            //cc.setReadTimeout(5000);
            // set timeout for connection
            //cc.setConnectTimeout(5000);
            //set HTTP method to GET
            cc.setRequestMethod("GET");
            //set it to true as we are connecting for input
            cc.setDoInput(true);
            if (withBody)
                cc.setDoOutput(true);

            //reading HTTP response code
            int response = cc.getResponseCode();

            //if response code is 200 / OK then read Inputstream
            if (response == HttpURLConnection.HTTP_OK) {
                DataInputStream = cc.getInputStream();
            }

        } catch (ConnectException ce) {
            Log.e(LOG_TAG, "Error in GetData", ce);
            throw ce;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in GetData", e);
            throw e;
        }
        return DataInputStream;
    }
}
