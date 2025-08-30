package com.example.scc;

import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SemaphoreSmsSender {
    private static final String API_KEY = "8a34c80dad25abdd76c4c7bcc1bf6e98";  // Replace with your actual key
    private static final String SENDER_NAME = "FarmBite"; // Or use your registered sender name
    private static final String ENDPOINT = "https://api.semaphore.co/api/v4/messages";

    public static void sendSms(String recipientNumber,String message)
    {
        new SendSmsTask().execute(recipientNumber, message);

    }

    private static class SendSmsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try
            {
                String number = strings[0];
                String message = strings[1];


                // JSON payload
                String jsonData = "{"
                        + "\"apikey\":\"" + API_KEY + "\","
                        + "\"number\":\"" + number + "\","
                        + "\"message\":\"" + message + "\","
                        + "\"sendername\":\"" + SENDER_NAME + "\""
                        + "}";

                URL url = new URL(ENDPOINT);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                writer.write(jsonData);
                writer.flush();
                writer.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Message sent successfully!";
                } else {
                    return "Failed to send message. Response Code: " + responseCode;
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return "Error: "+ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
        }


    }

}