package com.finalproject.takmilul.icare.alarm;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.finalproject.takmilul.icare.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toast.makeText(getApplicationContext(),"Result Activity",Toast.LENGTH_LONG).show();
    }
}
