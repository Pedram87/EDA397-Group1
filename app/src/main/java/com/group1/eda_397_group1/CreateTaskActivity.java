package com.group1.eda_397_group1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

public class CreateTaskActivity extends AppCompatActivity {

    private CreatePairProgTask cppTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        final EditText taskName = (EditText) findViewById(R.id.taskName);
        TextView taskOwnerLabel = (TextView) findViewById(R.id.taskOwnerLabel);
        final NumberPicker durationHour = (NumberPicker) findViewById(R.id.durationPickerHour);
        final NumberPicker durationMinute = (NumberPicker) findViewById(R.id.durationPickerMinute);
        Button createTaskButton = (Button) findViewById(R.id.createTaskButton);

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Task task = new Task(taskName.getText().toString(), ((durationHour.getValue()*60)+durationMinute.getValue()), new User("","",""), new User("","",""), new User("","",""));

                cppTask = new CreatePairProgTask(task);
                cppTask.execute((Void) null);
            }
        });


        durationHour.setMaxValue(10);
        durationHour.setMinValue(0);

        durationMinute.setMaxValue(59);
        durationMinute.setMinValue(0);


    }


    public class CreatePairProgTask extends AsyncTask<Void, Void, Boolean> {

        private Task task;

        public CreatePairProgTask(Task task){
            this.task = task;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JSONObject jsonReturnObject = null;
            JSONParser parser = new JSONParser();

            String result = null;
            try{

                Log.e("CreatePairProgActivity", task.toString()); //result);

            }catch (Exception e){

            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            cppTask = null;
        }

        @Override
        protected void onCancelled() {
            cppTask = null;
            // showProgress(false);
        }
    }
}
