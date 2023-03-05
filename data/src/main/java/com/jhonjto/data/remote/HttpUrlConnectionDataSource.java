package com.jhonjto.data.remote;

import com.jhonjto.data.BuildConfig;
import com.jhonjto.domain.interfaces.ConnectionDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlConnectionDataSource extends Thread implements ConnectionDataSource {

    HttpURLConnection urlConnection = null;

    @Override
    public String getValidationsUser(Double latitude, Double longitude) {
        new HttpUrlConnectionDataSource().start();

        String result = "";
        try {
            URL url = new URL(BuildConfig.BASE_URL+HttpServiceUrl.getValidations(latitude, longitude));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");

            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new IOException("Invalid response from server: " + code);
            }

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    result = line;
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }
}
