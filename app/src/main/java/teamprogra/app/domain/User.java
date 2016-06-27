package teamprogra.app.domain;

import android.net.Uri;

import com.google.gson.Gson;

/**
 * Created by adrianleyva on 25/06/16.
 */
public class User {

    private String name;
    private String email;
    private Uri photo;
    private int age;
    private String occupation;
    private String ubication;
    private String gender;
    private long phone;
    private int organizedEvents;
    private int score;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Serialización del objeto
     * que será devuelto como un String en fomrato jSon
     */
    public String serializeUser(){
        Gson userJson = new Gson();
        return userJson.toJson(this);
    }

    /**
     * Creación del objeto desde el jSon
     */
    public static User createUserFromJson(String userJson){
        Gson objectJson = new Gson();
        return objectJson.fromJson(userJson, User.class);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getOrganizedEvents() {
        return organizedEvents;
    }

    public void setOrganizedEvents(int organizedEvents) {
        this.organizedEvents = organizedEvents;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
