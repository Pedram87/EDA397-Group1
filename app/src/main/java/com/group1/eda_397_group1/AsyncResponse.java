package com.group1.eda_397_group1;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jimmy on 16-04-28.
 */
public interface AsyncResponse {
    void processFinish(JSONObject output) throws JSONException;
}
