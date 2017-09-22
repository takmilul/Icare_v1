package com.finalproject.takmilul.icare.activity;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.adapter.ProfileListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileListActivity extends AppCompatActivity {

   ListView profileLV;
   ArrayList<ProfileModel> profileModels;
   DataStorage dataStorage;
   SharedPreferences preferences;
   SharedPreferences preferences1;
   EditText profileSearchET;
   static boolean profileChanged;
   private Boolean exit = false;
   private String TAG = "main";
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_profile_list);

      profileLV = (ListView) findViewById(R.id.profileLV);
      profileSearchET = (EditText) findViewById(R.id.profileSearchET);
      dataStorage = new DataStorage(getApplicationContext());
      profileChanged = false;
   
      if (Build.VERSION.SDK_INT >= 23) {
         accessPermission();
      }
   
      showProfile();
      

      profileSearchET.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
            showProfileSearch(profileSearchET.getText().toString());

         }

         @Override
         public void afterTextChanged(Editable s) {

         }
      });

   }
   
   @TargetApi(VERSION_CODES.JELLY_BEAN)
   private boolean accessPermission() {
      String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            permission.CALL_PHONE,
            permission.SEND_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            permission.ACCESS_NETWORK_STATE,
            permission.ACCESS_FINE_LOCATION,
            permission.ACCESS_COARSE_LOCATION,
            permission.READ_CONTACTS
      };
      
            /*if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                  == PackageManager.PERMISSION_GRANTED) {
               Log.v(TAG,"Permission is granted");
               return true;
            } else {
            
               Log.v(TAG,"Permission is revoked");
               ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
               return false;
            }
         }
         else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
         }*/
   
         int result;
         List<String> listPermissionsNeeded = new ArrayList<>();
         for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
               listPermissionsNeeded.add(p);
            }
         }
         if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
         }
         return true;
   
      
   }
   
   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      if (requestCode == 100) {
         if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
         }
         return;
      }
   }
   
   @Override
   protected void onStart() {
      super.onStart();
      showProfile();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_profile_list, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      //noinspection SimplifiableIfStatement
      if (id == R.id.add_profile_menu) {

         preferences1 = getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
         SharedPreferences.Editor editor1 = preferences1.edit();
         editor1.putString("person_id_update", "");
         editor1.apply();
         editor1.commit();

         Intent intent = new Intent(ProfileListActivity.this, AddProfileActivity.class);
         startActivity(intent);
         return true;
      }
      if (id == R.id.menu_change_user_info) {
         Intent intent = new Intent(ProfileListActivity.this, UpdateUserActivity.class);
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public void onBackPressed() {
      if (exit) {
         finish(); // finish activity
      }
      else {
         Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
         exit = true;
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               exit = false;
            }
         }, 3 * 1000);
      }
   }

   private void showProfile() {
      profileModels = dataStorage.getAllProfile();

      ProfileListAdapter profileListAdapter = new ProfileListAdapter(ProfileListActivity.this, profileModels);
      profileLV.setAdapter(profileListAdapter);
      profileListAdapter.notifyDataSetChanged();
      profileLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ProfileListActivity.this, DashBoardActivity.class);
            String profile_id = (profileModels.get(position)).getPersonId();
            preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("person_id", profile_id);
            editor.apply();
            editor.commit();
            profileChanged = true;
            startActivity(intent);

            preferences1 = getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = preferences1.edit();
            editor1.putString("person_id_update", "");
            editor1.apply();
            editor1.commit();
         }
      });

      profileLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final CharSequence[] items = {"Update", "Delete"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileListActivity.this);
            builder.setTitle("User Action");
            builder.setItems(items, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int item) {
                  if (items[item].equals("Update")) {
                     Intent intent = new Intent(ProfileListActivity.this, AddProfileActivity.class);
                     String profile_id_update = String.valueOf((profileModels.get(position)).getId());

                     preferences = getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putString("person_id_update", profile_id_update);
                     editor.apply();
                     editor.commit();

                     startActivity(intent);

                  }
                  else if (items[item].equals("Delete")) {

                     new AlertDialog.Builder(ProfileListActivity.this).setTitle("Delete Profile").setMessage("Are you sure you want to Delete The Profile  ?").setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // do nothing
                        }
                     }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
      
                           String personId = profileModels.get(position).getPersonId();
                           boolean delete = dataStorage.deleteAllActivitiesByPersonId(personId, "D");
                           if (delete) {
                              Toast.makeText(getApplication(), "Profile Deleted Successfully", Toast.LENGTH_LONG).show();
                           }
                           else {
                              Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                           }
                           showProfile();
      
                        }
                     }).setIcon(android.R.drawable.ic_dialog_alert).show();
                  }
               }
            });
            builder.show();

            return true;
         }
      });
   }
   
   private void showProfileSearch(String prName) {
      profileModels = dataStorage.getProfileModelBySearchbyName(prName);

      ProfileListAdapter profileListAdapter = new ProfileListAdapter(ProfileListActivity.this, profileModels);
      profileLV.setAdapter(profileListAdapter);
      profileListAdapter.notifyDataSetChanged();
      profileLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ProfileListActivity.this, DashBoardActivity.class);
            String profile_id = (profileModels.get(position)).getPersonId();
            preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("person_id", profile_id);
            editor.apply();
            editor.commit();
            startActivity(intent);

            preferences1 = getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = preferences1.edit();
            editor1.putString("person_id_update", "");
            editor1.apply();
            editor1.commit();

         }
      });
   }

}
