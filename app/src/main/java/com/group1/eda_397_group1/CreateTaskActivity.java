package com.group1.eda_397_group1;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
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
    private View mProgressView;
    private View mcreatTaskFormView;

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

        mProgressView = findViewById(R.id.createTask_progress);
        mcreatTaskFormView = findViewById(R.id.createTaskForm);

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


        assert createTaskButton != null;
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO create validation methods for the task
                Task task = new Task(taskName.getText().toString(), ((durationHour.getValue() * 60) + durationMinute.getValue()), currentUserID, user1Selector.getSelectedItem().toString(), user2Selector.getSelectedItem().toString());


                Log.e(" Create TAsk Activity", task.toString());
                showProgress(true);


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
        Toast toast;

        if (json.get("success").equals(1)) {
            if(tag.equals("getAllUsers")){
                Log.d("User Data acquired:", json.getJSONArray("user").toString());
                processRemoteUserData(json);
            }
            else if(tag.equals("create_task")){
                showProgress(false);
                toast = Toast.makeText(getBaseContext(), "Task created successfully", Toast.LENGTH_LONG);
                toast.show();
                finish();
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

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mcreatTaskFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mcreatTaskFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mcreatTaskFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mcreatTaskFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
