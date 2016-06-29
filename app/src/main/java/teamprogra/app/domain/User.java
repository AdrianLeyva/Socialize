package teamprogra.app.domain;

import android.net.Uri;

import com.google.gson.Gson;

/**
 * Created by adrianleyva on 25/06/16.
 */
public class User {

    private String id;
    private String name;
    private String email;
    private String photo;
    private String birthday;
    private String occupation;
    private String ubication;
    private String gender;
    private long phone;
    private int organizedEvents;
    private int score;

    public User(){

    }

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String  birthday) {
        this.birthday = birthday;
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
