package com.group1.eda_397_group1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

//i need the id to get the time from the database
//need JSON object then with it access db to get time
public class Countdown extends AppCompatActivity implements AsyncResponse {

    private long timeLength;
    private long timeLeft;
    private TextView timeText;
    private CountDownTimer cdTimer;
    private Button start;
    private Button stop;
    private Boolean isPaused;
    private DatabaseHandler databaseHandler = null;
    private JSONParser parser = new JSONParser();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        start = (Button) findViewById(R.id.startCount);
        stop = (Button) findViewById(R.id.stopCount);
        timeText = (TextView) findViewById(R.id.timeView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isPaused = false;

        //timeLength = 8000;
        timeLeft = 0;
//        timeText.setText("8");
        getTime();
    }

    //function with answer from db
    public void getTime() {
        databaseHandler = new DatabaseHandler(parser.getGetTaskInJSON(9));
        databaseHandler.delegate = this;
        databaseHandler.execute();

    }

    @Override
    public void processFinish(JSONObject json) throws JSONException {

        if (json.get("success").equals(1)) {
            Log.d("timer", "success");


            JSONObject returnTask = json.getJSONObject("task");
            Integer duration = new Integer(0);
            try{
                duration = Integer.parseInt( returnTask.get("duration").toString());
            }catch(NumberFormatException e){
                Log.e("Countdown: duration", "Countdown error in parsing duration from the task");
            }

            timeText.setText((String) returnTask.get("duration"));
            //setTime(duration);
            timeLength = duration * 60000;

        } else {
            Log.e("timer", "error");
        }
    }

//if duration is in minutes, multiply by 60000 to get long num
//    public void setTime(Integer duration){
//        String toSend = new String();
//        timeLength = duration.intValue() * 60000;
//        toSend = duration.toString();
//        timeText.setText( toSend );
//    }


    public void startCount (View view) {
        final View v = view;

        if(!isPaused){
            start.setText("Pause Countdown");
            isPaused = true;

            if(timeLeft==0){
                showCountdown(timeLength);
            }
            else{
                showCountdown(timeLeft);
            }
        }
        else{
            start.setText("Start Countdown");
            isPaused = false;
            stopCount(v);
        }
    }

    private void showCountdown(long tm) {
        cdTimer = new CountDownTimer(tm, 1000) {
            public void onTick(long t) {
                timeLeft = t;

                timeText.setText(String.valueOf(String.valueOf(t/1000)));
                //if(t == timeText/2){
                    //show message to swap

                //}
            }
            public void onFinish() {
                timeText.setText("0");
                //the tone generator doesnt work in the simulator
                ToneGenerator tone = new ToneGenerator(ToneGenerator.TONE_DTMF_A, 25);
                tone.startTone(ToneGenerator.TONE_DTMF_A, 5000);
            }
        }.start();
    }

    public void stopCount (View view) {
        if(isPaused){
            cdTimer.cancel();
            start.setText("Start Countdown");
            isPaused = false;
            timeLeft = 0;
            timeText.setText("0");
        }
        else{
            cdTimer.cancel();
        }
    }
}