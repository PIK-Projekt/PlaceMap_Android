package pl.placemap;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pl.placemap.data.Data;
import pl.placemap.data.Person;
import pl.placemap.data.Place;

public class MapsActivity extends AppCompatActivity {

    private static MapsActivity instance;
    private GoogleMap mMap;
    SupportMapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        instance = this;
        setUpMapIfNeeded();
        showPlacesAllFriends();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            new AlertDialog.Builder(MapsActivity.this)
                    .setMessage("Do you really want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(MapsActivity.this, MainActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return true;
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.main_map));
            mMap = mMapFragment.getMap();
        }
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    public void onClickFriendsButton(View view) {
        List<Person> friendsList = Data.getAllFriendsInfo();
        final int size = friendsList.size();
        String[] string = new String[size + 1];
        for (int i = 0; i < size; i++) {
            string[i] = friendsList.get(i).getName();
        }
        string[size] = "All friends";
        new AlertDialog.Builder(MapsActivity.this).setTitle("Friends")
                .setSingleChoiceItems(string, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (whichButton == size)
                            showPlacesAllFriends();
                        else
                            showPlacesOneFriend(whichButton, true);
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void showPlacesOneFriend(int id, boolean clear) {
        List<Place> placeList = Data.getFriendTaggedPlaces(id);
        Place place;
        Double latitude, longitude;
        if (clear)
            mMap.clear();
        for (int i = 0; i < placeList.size(); i++) {
            place = placeList.get(i);
            latitude = Double.valueOf(place.getLatitude());
            longitude = Double.valueOf(place.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(place.getName()));
        }

    }

    public void showPlacesAllFriends() {
        mMap.clear();
        int size = Data.getNumberOfFriends();
        for (int i = 0; i < size; i++) {
            showPlacesOneFriend(i, false);
        }
    }

    public static MapsActivity getInstance() {
        return instance;
    }

}
