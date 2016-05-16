package com.group1.eda_397_group1;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CreateTaskActivity extends AppCompatActivity implements AsyncResponse{

  //  private CreatePairProgTask cppTask;
    private  DatabaseHandler databaseHandler;
    private JSONParser parser = new JSONParser();
    private ArrayList<User> users;
    private List<String> usersString = new ArrayList<>();
    Spinner user1Selector;
    Spinner user2Selector;
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> dataAdapter2;

    private String currentUserID;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        final EditText taskName = (EditText) findViewById(R.id.taskName);
        TextView taskOwnerLabel = (TextView) findViewById(R.id.taskOwnerLabel);
        //Get Current User from Singleton
        currentUserID = UserSingleton.getInstance().getEmail();
        taskOwnerLabel.setText(taskOwnerLabel.getText() + " " + currentUserID);
        final NumberPicker durationHour = (NumberPicker) findViewById(R.id.durationPickerHour);
        final NumberPicker durationMinute = (NumberPicker) findViewById(R.id.durationPickerMinute);
        Button createTaskButton = (Button) findViewById(R.id.createTaskButton);
        user1Selector = (Spinner) findViewById(R.id.user1);
        user2Selector = (Spinner) findViewById(R.id.user2);
        Button randomizeButton = (Button) findViewById(R.id.randomizeButton);

        // Tasks and Users
        //getUserDataFromDatabaseTask = new GetUserDataFromDatabase();
        //getUserDataFromDatabaseTask.execute((Void)null);

        // Get from online database
        getDataForAllUsersInRemoteDB();



        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, usersString);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, usersString);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        user1Selector.setAdapter(dataAdapter);
        user2Selector.setAdapter(dataAdapter2);





        randomizeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int randomNumber1 = new Random().nextInt(usersString.size());
                int randomNumber2 = new Random().nextInt(usersString.size());
                user1Selector.setSelection(randomNumber1);
                user2Selector.setSelection(randomNumber2);

                user1Selector.refreshDrawableState();
                user2Selector.refreshDrawableState();
            }
        });


        createTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO create validation methods for the task
                Task task = new Task(taskName.getText().toString(), ((durationHour.getValue() * 60) + durationMinute.getValue()), currentUserID, user1Selector.getSelectedItem().toString(), user2Selector.getSelectedItem().toString());


                Log.e(" Create TAsk Activity", task.toString());

                createTaskInRemoteDB(task);


            }
        });





        durationHour.setMaxValue(10);
        durationHour.setMinValue(0);

        durationMinute.setMaxValue(59);
        durationMinute.setMinValue(0);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void createTaskInRemoteDB(Task task){
        databaseHandler = new DatabaseHandler(parser.getCreateTaskInJSON(task));
        databaseHandler.delegate = this;
        databaseHandler.execute();
    }

    public ArrayList<User> getDataForAllUsersInRemoteDB(){
        ArrayList<User> allUsers = new ArrayList<>();

        databaseHandler = new DatabaseHandler(parser.getGetUsersInJSON());
        databaseHandler.delegate = this;
        databaseHandler.execute();

        return allUsers;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateTask Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.group1.eda_397_group1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateTask Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.group1.eda_397_group1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    public void processFinish(JSONObject json) throws JSONException {
        String tag = json.getString("tag");
        // error_msg

        if (json.get("success").equals(1)) {
            if(tag.equals("getAllUsers")){
                Log.d("User Data acquired:", json.getJSONArray("user").toString());
                processRemoteUserData(json);
            }
            else if(tag.equals("create_task")){
                Log.d("Create task activity", "task created successfully");
            }


            // Go to another activity and store user
        } else {
            Log.e("LoginActivity", "login error");
            new AlertDialog.Builder(CreateTaskActivity.this)
                    .setTitle("Create Task failure")
                    .setMessage(json.get("error_msg").toString())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing here
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }


    public void processRemoteUserData(JSONObject json)throws JSONException{
        ArrayList<User> usersFromRemote = new ArrayList<>();
        JSONArray usersFromRemoteJSON = json.getJSONArray("user");

        // Extract users from the JSONArray to a java Array
        for(int i = 0; i < usersFromRemoteJSON.length(); i++){
            JSONObject userJSon = (JSONObject)usersFromRemoteJSON.get(i);
            User user = new User(userJSon.getString("email"), userJSon.getString("name"), "");
            usersFromRemote.add(user);
            Log.e("User" + i, user.toString());
        }


        for(User u: usersFromRemote){
            usersString.add(u.getEmail());
        }

        dataAdapter.notifyDataSetChanged();
        dataAdapter2.notifyDataSetChanged();
    }

}
