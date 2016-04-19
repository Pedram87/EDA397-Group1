package com.group1.eda_397_group1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        EditText taskName = (EditText) findViewById(R.id.taskName);
        TextView taskOwnerLabel = (TextView) findViewById(R.id.taskOwnerLabel);
        NumberPicker spinnerDuration = (NumberPicker) findViewById(R.id.durationPicker);
        Button createTaskButton = (Button) findViewById(R.id.createTaskButton);



    }
}
