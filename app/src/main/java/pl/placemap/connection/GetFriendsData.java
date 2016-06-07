package pl.placemap.connection;


import android.os.AsyncTask;
import android.widget.Toast;

import com.facebook.AccessToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pl.placemap.MainActivity;


public class GetFriendsData extends AsyncTask<Void, Void, Void> {
    int responseCode;
    BufferedReader rd;
    StringBuilder result = new StringBuilder();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String line;
            String urlString = "http://192.168.1.14:8081/user/friends/" + AccessToken.getCurrentAccessToken().getUserId();
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            responseCode = connection.getResponseCode();
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            new FileCreator().execute(result.toString());
        } else {
            Toast.makeText(MainActivity.getInstance(), "Unable to connect with server", Toast.LENGTH_SHORT).show();
        }
    }
}
