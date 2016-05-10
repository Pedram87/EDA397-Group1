package com.group1.eda_397_group1;

import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Countdown extends AppCompatActivity {

    private long timeLength;
    private long timeLeft;
    private TextView timeText;
    CountDownTimer cdTimer;
    Button start;
    Button stop;
    Boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_countdown);
        start = (Button) findViewById(R.id.startCount);
        stop = (Button) findViewById(R.id.stopCount);
        timeText = (TextView) findViewById(R.id.timeView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isPaused = false;

        timeLength = 8000;
        timeLeft = 0;
        timeText.setText("8");
    }
//if duration is in minutes, multiply by 60000 to get long num
    public void setTime(View view, int duration){
        String toSend = new String();
        Integer dur = duration * 60000;
        toSend = dur.toString();
        timeText.setText( toSend );
    }


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
            }
            public void onFinish() {
                timeText.setText("0");
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
