package com.amicel.tunaikudemo;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

public class RequestHandler {
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        Exception e;
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(requestURL).openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            if (conn.getResponseCode() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb2 = new StringBuilder();
                while (true) {
                    try {
                        String response = br.readLine();
                        if (response == null) {
                            break;
                        }
                        sb2.append(response);
                    } catch (Exception e2) {
                        e = e2;
                        sb = sb2;
                    }
                }
                sb = sb2;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return sb.toString();
        }
        return sb.toString();
    }

    public String sendGetRequest(String requestURL) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(((HttpURLConnection) new URL(requestURL).openConnection()).getInputStream()));
            while (true) {
                String s = bufferedReader.readLine();
                if (s == null) {
                    break;
                }
                sb.append(s + "\n");
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }

    public String sendGetRequestParam(String requestURL, String id) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(((HttpURLConnection) new URL(requestURL + id).openConnection()).getInputStream()));
            while (true) {
                String s = bufferedReader.readLine();
                if (s == null) {
                    break;
                }
                sb.append(s + "\n");
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
