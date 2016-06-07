package pl.placemap.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.placemap.MainActivity;

public class Data {


    public static List<Place> getFriendTaggedPlaces(int id) {
        JSONArray friends, places;
        JSONObject friend, place;
        List<Place> placeList = new ArrayList<>();
        friends = getDataFromFile();
        try {
            friend = friends.getJSONObject(id);
            places = friend.getJSONArray("places");
            for (int j = 0; j < places.length(); j++) {
                place = places.getJSONObject(j);
                placeList.add(new Place(place.getString("name"), place.getString("city"), place.getString("country"), place.getString("latitude"), place.getString("longitude")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placeList;
    }

    public static List<Place> getAllFriendsTaggedPlaces() {
        JSONArray friends, places;
        JSONObject friend, place;
        List<Place> placeList = new ArrayList<>();
        friends = getDataFromFile();
        try {
            for (int i = 0; i < friends.length(); i++) {
                friend = friends.getJSONObject(i);
                places = friend.getJSONArray("places");
                for (int j = 0; j < places.length(); j++) {
                    place = places.getJSONObject(j);
                    placeList.add(new Place(place.getString("name"), place.getString("city"), place.getString("country"), place.getString("latitude"), place.getString("longitude")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placeList;
    }

    public static Person getFriendInfo(int id) {
        JSONArray friends;
        JSONObject friend;
        Person person = null;
        friends = getDataFromFile();
        try {
            friend = friends.getJSONObject(id);
            person = new Person(friend.getString("name"), friend.getString("email"), friend.getString("phone"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }

    public static List<Person> getFriendsByPosition(String name, String latitude, String longitude) {
        JSONArray friends, places;
        JSONObject friend, place;
        List<Person> friendsList = new ArrayList<>();
        friends = getDataFromFile();
        try {
            for (int i = 0; i < friends.length(); i++) {
                friend = friends.getJSONObject(i);
                places = friend.getJSONArray("places");
                for (int j = 0; j < places.length(); j++) {
                    place = places.getJSONObject(j);
                    if (latitude.equals(place.getString("latitude")) && longitude.equals(place.getString("longitude")) && name.equals(place.getString("name"))) {
                        friendsList.add(new Person(friend.getString("name"), friend.getString("email"), friend.getString("phone")));
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friendsList;
    }

    public static List<Person> getAllFriendsInfo() {
        JSONArray friends;
        JSONObject friend;
        List<Person> friendsList = new ArrayList<>();
        friends = getDataFromFile();
        try {
            for (int i = 0; i < friends.length(); i++) {
                friend = friends.getJSONObject(i);
                friendsList.add(new Person(friend.getString("name"), friend.getString("email"), friend.getString("phone")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friendsList;
    }


    public static JSONArray getDataFromFile() {
        JSONArray jsonArray = null;
        File fileOut = new File(MainActivity.getInstance().getCacheDir() + "/data.json");
        byte[] bytes = new byte[(int) fileOut.length()];
        try {
            FileInputStream in = new FileInputStream(fileOut);
            in.read(bytes);
            in.close();
            jsonArray = new JSONArray(new String(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static int getNumberOfFriends() {
        JSONArray friends = getDataFromFile();
        return friends.length();
    }


}
