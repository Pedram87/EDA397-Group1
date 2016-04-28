package com.group1.eda_397_group1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskList_unused extends AppCompatActivity {

    //ListView
    private ListView taskListView;
    private Button createTaskButton;

    private CustomListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //Find the listView Resource
        taskListView = (ListView) findViewById(R.id.taskList);

        //Find the "createTaskButton"
        //createTaskButton = (Button) findViewById(R.id.createTaskButton);

        //Create and populate the list of tasks
        //TODO: Add the tasks from the database in the list
        Task[] tasks = new Task[]{new Task("JespersTask", 5, new User("emailJesper", "Jesper", "Karlberg"),new User("emailBerima", "Berima", "Andam"), new User("emailMusse", "Musse", "Hussein")),
                new Task("JespersTask", 5, new User("emailJesper", "Jesper", "Karlberg"),new User("emailBerima", "Berima", "Andam"), new User("emailMusse", "Musse", "Hussein"))};


        final ArrayList<Task> taskList = new ArrayList<>();
        taskList.addAll(Arrays.asList(tasks));

        //Create ArrayAdapter using the tasklist
        taskListAdapter = new CustomListAdapter(this, taskList);

        taskListView.setAdapter(taskListAdapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //TODO: see if you can send a custom class through putexta. Maybe using "implements serializable" in the Task class?
                Intent taskIntent = new Intent(TaskList_unused.this, CreateTaskActivity.class);
                taskIntent.putExtra("task", taskList.get(position));
                startActivity(taskIntent);

            }
        });

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(TaskList_unused.this, CreateTaskActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                TaskList_unused.this.startActivity(activityChangeIntent);
            }
        });

    }
}
