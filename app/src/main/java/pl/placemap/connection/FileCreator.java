package pl.placemap.connection;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import pl.placemap.MainActivity;
import pl.placemap.MapsActivity;

public class FileCreator extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        Writer output;
        File file = new File(MainActivity.getInstance().getCacheDir(), "/data.json");
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(params[0]);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MapsActivity.getInstance().showPlacesAllFriends();
    }
}
