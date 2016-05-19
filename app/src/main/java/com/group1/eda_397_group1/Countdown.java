package com.group1.eda_397_group1;

import android.content.Intent;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
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
    private Intent in;
    private int hour;
    private int hmod;
    private int min;
    private int sec;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        start = (Button) findViewById(R.id.startCount);
        stop = (Button) findViewById(R.id.stopCount);
        timeText = (TextView) findViewById(R.id.timeView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isPaused = false;

        in = getIntent();
        timeLeft = 0;
        getTime();
    }

    //function with answer from db
    public void getTime() {
        String id = in.getStringExtra("taskID");
        int taskId = Integer.parseInt(id);

        databaseHandler = new DatabaseHandler(parser.getGetTaskInJSON(taskId));
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

            hour = duration / 60;
            min = duration % 60;
            sec = min / 60;

            setTimerText();

            timeLength = duration * 60000;

        } else {
            Log.e("timer", "error");
        }
    }

    public void startCount (View view) {
        final View v = view;

        if(timeLength > 0) {
            if (!isPaused) {
                start.setText("Pause Countdown");
                isPaused = true;

                if (timeLeft == 0) {
                    showCountdown(timeLength);
                } else {
                    showCountdown(timeLeft);
                }
            } else {
                start.setText("Start Countdown");
                isPaused = false;
                cdTimer.cancel();
            }
        }
        else{
            stopCount(view);
        }
    }

    private void showCountdown(long tm) {
        cdTimer = new CountDownTimer(tm, 1000) {
            public void onTick(long t) {
                timeLeft = t;

                hour = (int) (timeLeft/1000) / 3600;
                hmod = (int) (timeLeft/1000) % 3600;
                min =  hmod / 60;
                sec = hmod % 60;
                setTimerText();
            }
            public void onFinish() {
                timeText.setText("00:00:00");
                //the tone generator doesnt work in the emulator
                ToneGenerator tone = new ToneGenerator(ToneGenerator.TONE_DTMF_A, 25);
                tone.startTone(ToneGenerator.TONE_DTMF_A, 5000);
            }
        }.start();
    }

    public void stopCount (View view) {
        if(timeLeft>0)
            cdTimer.cancel();
        start.setText("Start Countdown");
        isPaused = false;
        timeLeft = 0;
        timeText.setText("00:00:00");
    }

    protected void setTimerText () {
        String sStr;
        String mStr;
        String hStr;


        if (sec<10)
            sStr = "0" + sec;
        else
            sStr = "" + sec;
        if (min<10)
            mStr = "0" + min;
        else
            mStr = "" + min;
        if (hour<10)
            hStr = "0" + hour;
        else
            hStr = "" + hour;

        timeText.setText(hStr + ":" + mStr + ":" + sStr);
    }
}
