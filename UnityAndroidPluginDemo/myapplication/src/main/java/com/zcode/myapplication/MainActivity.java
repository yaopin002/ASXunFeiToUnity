package com.zcode.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btMSC;
    private Button btShow;
    private SpeechEngine mSpeechEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpeechEngine = new SpeechEngine(this);
        mSpeechEngine.InitConnection();

        btMSC = (Button) findViewById(R.id.btMSC);
        btShow = (Button) findViewById(R.id.btShow);
        btMSC.setOnClickListener(this);
        btShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btMSC:
                mSpeechEngine.StartSpeech();
                break;
            case R.id.btShow:
                mSpeechEngine.ShowWord();
                break;
        }
    }
}
