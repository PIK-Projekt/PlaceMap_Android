package pl.placemap.data;

public class Place {
    String name;
    String city;
    String country;
    String latitude;
    String longitude;

    public Place(String name, String city, String country, String latitude, String longitude) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
