package com.example.guyfawkes.iamqrfinal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guyfawkes on 11/04/18.
 */

public class JSONParser {

    public static User getUser(String s) throws JSONException {

        JSONObject allJson = new JSONObject(s);

        // Como solo hay un json con todos los valores directamente sobre el usuario se piden al objeto global 'alljson'

        User u = new User(Integer.parseInt(allJson.getString("id")),
                allJson.getString("name"),
                allJson.getString("mail"),
                allJson.getString("photoUrl"),
                Float.parseFloat(allJson.getString("money")),
                allJson.getBoolean("isGoogleUser"));

        return u;
    }

    public static boolean getMoneyRequest(String s) throws JSONException {

        JSONObject allJson = new JSONObject(s);

        Boolean done = allJson.getBoolean("done");

        return done;
    }

    public static boolean parseLogin(String s) throws JSONException {
        return new JSONObject(s).getBoolean("login");
    }
}