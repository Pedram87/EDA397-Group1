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

    public JSONObject getCreateTaskInJSON(Task task, User currentUser){
        JSONObject createTaskJSON = new JSONObject();


        try{
            createTaskJSON.put("tag", "create_task");
            createTaskJSON.put("task_name", task.getName());
            createTaskJSON.put("task_duration", task.getDuration());
            createTaskJSON.put("task_id", task.getId());
            createTaskJSON.put("task_owner", task.getOwnerID());
            createTaskJSON.put("task_PairProgrammer1ID", task.getPairprogramer1ID());
            createTaskJSON.put("task_PairProgrammer2ID", task.getPairprogramer2D());

        }catch (JSONException e){
            e.printStackTrace();
        }

        return createTaskJSON;
    }

    public JSONObject getUpdateTaskInJSON(Task task, User currentUser){
        JSONObject updateTaskJSON = new JSONObject();


        try{
            updateTaskJSON.put("tag", "update_task");
            updateTaskJSON.put("task_name", task.getName());
            updateTaskJSON.put("task_duration", task.getDuration());
            updateTaskJSON.put("task_id", task.getId());
            updateTaskJSON.put("task_owner", task.getOwnerID());
            updateTaskJSON.put("task_PairProgrammer1ID", task.getPairprogramer1ID());
            updateTaskJSON.put("task_PairProgrammer2ID", task.getPairprogramer2D());

        }catch (JSONException e){
            e.printStackTrace();
        }

        return updateTaskJSON;
    }
}
