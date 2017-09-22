package com.finalproject.takmilul.icare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.adapter.GeneralInformationListAdapter;
import com.finalproject.takmilul.icare.model.GeneralInformationModel;

public class GeneralInformationActivity extends AppCompatActivity {

    ListView genrealLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_information);
        genrealLV = (ListView) findViewById(R.id.genrealLV);
        ArrayList<GeneralInformationModel> al = GeneralInformationModel.getAllData();
        GeneralInformationListAdapter customAdapter = new GeneralInformationListAdapter(getApplicationContext(),al);
        genrealLV.setAdapter(customAdapter);
    }
}
