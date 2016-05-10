package com.group1.eda_397_group1;

import android.os.AsyncTask;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Max on 10.05.2016.
 */
public class JSONParserTest {

    private DatabaseHandler databaseHandler = null;
    private JSONParser parser = null;

    @Test
    public void registerUser_worksCorrect() throws Exception {

    }

    @Test
    public void getUser_worksCorrect() throws NullPointerException, Exception {
        parser = new JSONParser();
        // Check if a manually entered user is returned correctly
        databaseHandler = new DatabaseHandler(parser.getLoginJSON("1@1.com", "1234"));
        AsyncTask<Void, Void, JSONObject> asyncTask = databaseHandler.execute();

        assertEquals(1, asyncTask.get().getInt("success"));
        UserSingleton user = (UserSingleton) asyncTask.get().get("user");
        assertEquals("1@1.com", user.getEmail());
    }


}
