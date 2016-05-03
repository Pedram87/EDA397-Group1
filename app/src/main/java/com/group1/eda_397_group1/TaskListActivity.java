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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskListActivity extends AppCompatActivity {

    //ListView
    private ListView taskListView;
    private Button createTaskButton;

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

        //dbHandler = new DatabaseHandler(parser.getGetTasksInJSON())

        //Create and populate the list of tasks
        //TODO: Add the tasks from the database in the list
        Task[] tasks = new Task[]{new Task("JespersTask", 5, "emailJesper", "emailBerima", "emailMusse"),
                new Task("JespersTask", 5, "emailJesper" ,"emailBerima", "emailMusse")};


        taskList = new ArrayList<>();
        taskList.addAll(Arrays.asList(tasks));

        //Create ArrayAdapter using the tasklist
        if(taskList != null) {
            taskListAdapter = new CustomListAdapter(this, taskList);
        }

        if(taskListAdapter!=null) {
            taskListView.setAdapter(taskListAdapter);
        }
        if(taskListAdapter!=null) {
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //TODO: see if you can send a custom class through putexta. Maybe using "implements serializable" in the Task class?
                //TODO: See what we have to send here to get the info in the database
                Intent taskIntent = new Intent(TaskListActivity.this, CreateTaskActivity.class);
                taskIntent.putExtra("taskName", taskList.get(position).getName());
                startActivity(taskIntent);

            }
        });

            taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);

                    builder.setTitle("Delete task");
                    builder.setMessage("Are you sure you want to delete this task?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("", "Yes clicked");
                            //TODO Deleta task from database
                            dialog.dismiss();
                        }

                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("", "No clicked");
                            //TODO Nothing should be here
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                    return true;
                }
            });


        }

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(TaskListActivity.this, CreateTaskActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                TaskListActivity.this.startActivity(activityChangeIntent);
            }
        });

    }
}
