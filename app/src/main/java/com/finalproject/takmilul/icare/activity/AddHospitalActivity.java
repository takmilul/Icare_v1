package com.finalproject.takmilul.icare.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.HospitalModel;

public class AddHospitalActivity extends AppCompatActivity {
   
   EditText addHospitalNameET;
   EditText addHospitalRoadET;
   EditText addHospitalCityET;
   EditText addHospitalCountryET;
   EditText addHospitalContactET;
   EditText addHospitalEmailET;
   DataStorage dataStorage;
   SharedPreferences preferences1;
   Button saveBtn;
   Button profileNewBTN;
   String hospital_id_update;
   int id;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_hospital);
      dataStorage = new DataStorage(getApplicationContext());
      initializer();
      dataStorage = new DataStorage(getApplicationContext());
      preferences1 = getBaseContext().getSharedPreferences("hospital_id_update", MODE_PRIVATE);
      hospital_id_update = preferences1.getString("hospital_id_update", "");
      saveBtn = (Button) findViewById(R.id.addHospitalSaveBTN);
      profileNewBTN = (Button) findViewById(R.id.addHospitalNewBTN);
      
      if (hospital_id_update.equalsIgnoreCase("")) {
         saveBtn.setText("SAVE");
      }
      else {
         ActionBar actionBar = getSupportActionBar();
         actionBar.setTitle("Update Hospital");
         saveBtn.setText("UPDATE");
         id = Integer.valueOf(hospital_id_update);
         showDataforUpdate();
         saveBtn.setText("UPDATE");
         profileNewBTN.setVisibility(View.INVISIBLE);
      }
   }
   
   private void initializer() {
      addHospitalNameET = (EditText) findViewById(R.id.addHospitalNameET);
      addHospitalRoadET = (EditText) findViewById(R.id.addHospitalRoadET);
      addHospitalCityET = (EditText) findViewById(R.id.addHospitalCityET);
      addHospitalCountryET = (EditText) findViewById(R.id.addHospitalCountryET);
      addHospitalContactET = (EditText) findViewById(R.id.addHospitalContactET);
      addHospitalEmailET = (EditText) findViewById(R.id.addHospitalEmailET);
   }
   
   private void showDataforUpdate() {
      String hospitalName = dataStorage.getAllHospitalModelbyHospitalId(id).get(0).getHospitalName();
      String roadName = dataStorage.getAllHospitalModelbyHospitalId(id).get(0).getRoadName();
      String cityName = dataStorage.getAllHospitalModelbyHospitalId(id).get(0).getCityName();
      String countryName = dataStorage.getAllHospitalModelbyHospitalId(id).get(0).getCountryName();
      String contactNumber = dataStorage.getAllHospitalModelbyHospitalId(id).get(0).getContactNumber();
      String emailAddress = dataStorage.getAllHospitalModelbyHospitalId(id).get(0).getEmailAddress();
      
      addHospitalNameET.setText(hospitalName);
      addHospitalRoadET.setText(roadName);
      addHospitalCityET.setText(cityName);
      addHospitalCountryET.setText(countryName);
      addHospitalContactET.setText(contactNumber);
      addHospitalEmailET.setText(emailAddress);
   }
   
   public void onClickHospitalSave(View view) {
      String saveUpdate = saveBtn.getText().toString();
      if (saveUpdate.equalsIgnoreCase("SAVE")) {
         String flag = "A";
         String name = addHospitalNameET.getText().toString().replaceAll("( )+", " ").trim();
         String road = addHospitalRoadET.getText().toString().replaceAll("( )+", " ").trim();
         String city = addHospitalCityET.getText().toString().replaceAll("( )+", " ").trim();
         String country = addHospitalCountryET.getText().toString().replaceAll("( )+", " ").trim();
         String contact = addHospitalContactET.getText().toString().replaceAll("( )+", " ").trim();
         String email = addHospitalEmailET.getText().toString().replaceAll("( )+", " ").trim();
         
         if (name.length() > 0 && road.length() > 0 && city.length() > 0 && country.length() > 0 && contact.length() > 0) {
            
            HospitalModel hospitalModel = new HospitalModel(name, road, city, country, contact, email, flag);
            boolean insert = dataStorage.insertHospital(hospitalModel);
            if (insert) {
               Toast.makeText(getApplication(), "Hospital Information Added Successfully", Toast.LENGTH_LONG).show();
            }
            else {
               Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
         }
         else {
            Toast.makeText(getBaseContext(), "Hospital Name, Road, City, Country, Contact no. is required", Toast.LENGTH_LONG).show();
         }
      }
      else if (saveUpdate.equalsIgnoreCase("UPDATE")) {
         
         String flag = "A";
         String name = addHospitalNameET.getText().toString();
         String road = addHospitalRoadET.getText().toString();
         String city = addHospitalCityET.getText().toString();
         String country = addHospitalCountryET.getText().toString();
         String contact = addHospitalContactET.getText().toString();
         String email = addHospitalEmailET.getText().toString();
         
         HospitalModel hospitalModel = new HospitalModel(name, road, city, country, contact, email, flag);
         boolean updated = dataStorage.updateHospital(id, hospitalModel);
         if (updated) {
            Toast.makeText(getApplication(), "Hospital Information Updated Successfully", Toast.LENGTH_LONG).show();
         }
         else {
            Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
         }
         
      }
      
   }
   
   public void onClickHospitalNew(View view) {
      
      addHospitalNameET.setText("");
      addHospitalRoadET.setText("");
      addHospitalCityET.setText("");
      addHospitalCountryET.setText("");
      addHospitalContactET.setText("");
      addHospitalEmailET.setText("");
   }
}
