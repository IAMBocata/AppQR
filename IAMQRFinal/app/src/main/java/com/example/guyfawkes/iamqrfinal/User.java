package com.example.guyfawkes.iamqrfinal;

import java.io.Serializable;;

/**
 * Created by yous on 23/03/18.
 */

public class User implements Serializable {

    private int id;
    private String name;
    private String mail;
    private String urlPhoto;
    private float money;
    private boolean googleLogin;

    public User() {

    }

    public User(int id, String name, String mail, String urlPhoto, float money, boolean googleLogin) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.urlPhoto = urlPhoto;
        this.money = money;
        this.googleLogin = googleLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public boolean isGoogleLogin() {
        return googleLogin;
    }

    public void setGoogleLogin(boolean googleLogin) {
        this.googleLogin = googleLogin;
    }

    public String simpleToString(){
        return id + " - " + name + " - " + money;
    }
}
