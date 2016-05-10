package com.group1.eda_397_group1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

/**
 * Created by Kathy on 10/05/2016.
 */
public class TaskDone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_done);

        ExpandableListView list = (ExpandableListView) findViewById(R.id.listTasksDone);
        
    }


}
