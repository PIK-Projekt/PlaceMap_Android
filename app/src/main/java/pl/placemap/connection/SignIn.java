package pl.placemap.connection;

import android.os.AsyncTask;
import android.widget.Toast;

import com.facebook.AccessToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pl.placemap.MainActivity;

public class SignIn extends AsyncTask<Void, Void, Void> {
    int responseCode;

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String urlString = "http://192.168.1.14:8081/signin";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.getOutputStream().write(AccessToken.getCurrentAccessToken().getToken().getBytes("UTF8"));
            responseCode = connection.getResponseCode();
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
              new GetFriendsData().execute();
        } else {
            Toast.makeText(MainActivity.getInstance(), "Unable to connect with server", Toast.LENGTH_SHORT).show();
        }
    }
}
