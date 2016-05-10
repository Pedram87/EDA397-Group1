package com.group1.eda_397_group1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskListActivity extends AppCompatActivity implements AsyncResponse {

    //ListView
    private ListView taskListView;
    private Button createTaskButton;
    private Button refreshButton;

    private CustomListAdapter taskListAdapter;
    private ArrayList<Task> taskList;

    private DatabaseHandler dbHandler;
    private JSONParser parser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //Find the listView Resource
        taskListView = (ListView) findViewById(R.id.taskList);

        //Find the "createTaskButton"
        createTaskButton = (Button) findViewById(R.id.createTaskButton);

        //Find the refresh button
        refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setClickable(true);

        dbHandler = new DatabaseHandler(parser.getGetTasksInJSON("2@2.com"));
        dbHandler.delegate = this;
        dbHandler.execute();

        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                taskListAdapter.update();

            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //TODO: see if you can send a custom class through putexta. Maybe using "implements serializable" in the Task class?
                //TODO: See what we have to send here to get the info in the database
                Intent taskIntent = new Intent(TaskListActivity.this, Countdown.class);
                taskIntent.putExtra("taskID", taskList.get(position).getId());
                startActivity(taskIntent);

            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    final int positionInner = pos;
                    AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);

                    builder.setTitle("Delete task");
                    builder.setMessage("Are you sure you want to delete this task?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {



                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("", "Yes clicked");
                            Task task = taskList.get(positionInner);
                            taskList.remove(task);
                            taskListAdapter.notifyDataSetChanged();
                            JSONObject objectToDelete = parser.getDeleteTaskInJSON(task);
                            dbHandler = new DatabaseHandler(objectToDelete);
                            dbHandler.delegate = TaskListActivity.this;
                            dbHandler.execute();
                            dialog.dismiss();
                        }

                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("", "No clicked");
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                    return true;
                }
            });




        createTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(TaskListActivity.this, CreateTaskActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                TaskListActivity.this.startActivity(activityChangeIntent);
            }
        });

    }

    @Override
    public void processFinish(JSONObject json) throws JSONException {
        if (json.get("success").equals(1)) {
            Log.d("Create task activity", "task created successfully");

            String tag = json.getString("tag");

            if(tag.equals("delete_task")){
                Log.e("hhhhhhhhhh> ", "skjflshfshkfjsfsfs");
                updateTaskList(json);
               // taskListView.invalidateViews();
            }

            else if(tag.equals("get_tasks")) {
                updateTaskList(json);
            }


            // Go to another activity and store user
        } else {
            Log.e("LoginActivity", "login error");
            new AlertDialog.Builder(TaskListActivity.this)
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

    public void updateTaskList(JSONObject json) throws JSONException{
        taskList = new ArrayList<Task>();
        JSONArray jsonArray = json.getJSONArray("tasks");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject taskObject = (JSONObject) jsonArray.get(i);
            String taskName = taskObject.getString("name");
            String id = taskObject.getString("task_id");
            int duration = taskObject.getInt("total_time");
            String ownerID = taskObject.getString("owner");
            //TODO Add pair pgorrammers in db json
            String pairProg1 = taskObject.getString("pairProgrammer1");
            String pairProg2 = "";
            if (taskObject.getString("pairProgrammer2") != null) {
                pairProg2 = taskObject.getString("pairProgrammer2");
            } else {
                pairProg2 = "No other user assigned";
            }

            Task task = new Task(taskName, duration, ownerID, pairProg1, pairProg2);
            task.setId(id);
            taskList.add(task);


        }

        //taskList.addAll(Arrays.asList(tasks));

        //Create ArrayAdapter using the tasklist
        if (taskList != null) {
            taskListAdapter = new CustomListAdapter(this, taskList);
        }

        if (taskListAdapter != null) {
            taskListView.setAdapter(taskListAdapter);
        }
    }
}
