package com.group1.eda_397_group1;

import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Countdown extends AppCompatActivity {

    private long timeLength;
    private TextView txt;
    CountDownTimer cdTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_countdown);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button start = (Button) findViewById(R.id.startCount);
        Button stop = (Button) findViewById(R.id.stopCount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void setTime(View view, long tm){
        String toSend;
        toSend = ( (tm / 3600000) + ":" + (tm / 60000) + ":" + (tm / 1000));
        txt.setText( toSend );
    }

    public void startCount (View view) {
        final View v = view;
        cdTimer = new CountDownTimer(this.timeLength,1000){

            public void onTick(long t){
                t = timeLength;
                setTime(v,t);
            }

            public void onFinish() {
                String text;
                text = ("00:00:00");
                txt.setText(text);
                ToneGenerator tone = new ToneGenerator(ToneGenerator.TONE_DTMF_A,25);
                tone.startTone(ToneGenerator.TONE_DTMF_A,5000);
            }
        }.start();
    }

    public void stopCount (View view) {
        cdTimer.cancel();
    }
}
