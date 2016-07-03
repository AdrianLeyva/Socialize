package teamprogra.app.domain;

import com.google.gson.Gson;
import com.orm.SugarRecord;

import teamprogra.app.socialize.socialize.SocializeApplication;

/**
 * Created by adrianleyva on 25/06/16.
 */
public class User extends SugarRecord{

    private String idUserFacebook;
    private String name;
    private String email;
    private String photo;
    private String birthday;
    private String locale;
    private String gender;
    private String phone;
    private int organizedEvents;
    private int score;

    public User(){

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void getDataUser(SocializeApplication app){
        setName(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_NAME));
        setEmail(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_EMAIL));
        setBirthday(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_BIRTHDAY));
        setIdUserFacebook(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_ID));
        setGender(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_GENDER));
        setPhoto(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_PICTURE));
        setLocale(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_LOCALE));
        setPhone(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_PHONE));
        setOrganizedEvents(app.getIntRegisterValuePreferences(SocializeApplication.APP_VALUE_EVENTS));
        setScore(app.getIntRegisterValuePreferences(SocializeApplication.APP_VALUE_SCORE));
    }

    public String getIdUserFacebook() {
        return idUserFacebook;
    }

    public void setIdUserFacebook(String idUserFacebook) {
        this.idUserFacebook = idUserFacebook;
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


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
