package com.group1.eda_397_group1;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jimmy on 16-04-19.
 */
public class JSONParser {
    public JSONParser() {

    }

    public JSONObject getLoginJSON(String email, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("tag", "login");
            json.put("email", email);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSONParser.getLoginJSON", json.toString());
        return json;
    }
}
