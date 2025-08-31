package com.example.scc;

import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SemaphoreSmsSender {
    private static final String API_KEY = "8a34c80dad25abdd76c4c7bcc1bf6e98";
    private static final String SENDER_NAME = "FarmBite";
    private static final String ENDPOINT = "https://api.semaphore.co/api/v4/messages";

    // Add the "public" keyword here to make the interface visible
    public interface SmsSendListener {
        void onResult(String result);
    }

    public static void sendSms(String recipientNumber, String message, SmsSendListener listener) {
        new SendSmsTask(listener).execute(recipientNumber, message);
    }

    private static class SendSmsTask extends AsyncTask<String, Void, String> {
        private SmsSendListener listener;

        public SendSmsTask(SmsSendListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String number = strings[0];
                String message = strings[1];

                HashMap<String, String> params = new HashMap<>();
                params.put("apikey", API_KEY);
                params.put("number", number);
                params.put("message", message);
                params.put("sendername", SENDER_NAME);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, String> param : params.entrySet()) {
                    if (postData.length() != 0) {
                        postData.append('&');
                    }
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

                URL url = new URL(ENDPOINT);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Message sent successfully!";
                } else {
                    return "Failed to send message. Response Code: " + responseCode;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return "Error: " + ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (listener != null) {
                listener.onResult(result);
            }
        }
    }
}
