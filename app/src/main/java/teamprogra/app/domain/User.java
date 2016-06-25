package teamprogra.app.domain;

import com.google.gson.Gson;

/**
 * Created by adrianleyva on 25/06/16.
 */
public class User {

    private String name;
    private String email;
    private int age;
    private String occupation;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, int age, String occupation) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.occupation = occupation;
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
}
