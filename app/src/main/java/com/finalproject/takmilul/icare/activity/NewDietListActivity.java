package com.finalproject.takmilul.icare.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.adapter.NewDietListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.ProfileModel;
import com.finalproject.takmilul.icare.util.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewDietListActivity extends AppCompatActivity implements OnItemLongClickListener {
   
   public static int MENU_SIZE;
   public static int ITEM_SIZE;
   public static SharedPreferences firstRunPreferences;
   int breakfastArray;
   int lunchArray;
   int snacksArray;
   int dinnerArray;
   String[] breakfast;
   String[] lunch;
   String[] snacks;
   String[] dinner;
   ArrayList<String[]> dietList;
   ArrayList<ProfileModel> profileModels;
   String personId;
   SharedPreferences personIdPreferences;
   SharedPreferences preferences;
   SharedPreferences idPreferences;
   SharedPreferences.Editor editor;
   NewDietListAdapter newDietListAdapter;
   ListView newDietListLv;
   DataStorage dataStorage;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_diet_list);
      newDietListLv = (ListView) findViewById(R.id.newDietListLv);
      //firstRunPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      dietList = new ArrayList<>();
      idPreferences = getBaseContext().getSharedPreferences("id_list", MODE_PRIVATE);
      personIdPreferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
      personId = personIdPreferences.getString("person_id", "");
      dataStorage = new DataStorage(this);
      profileModels = new ArrayList<>();
      /*if (ProfileListActivity.profileChanged) {
         ProfileListActivity.profileChanged = false;
         firstRunPreferences.edit().putBoolean("firstRun", true).apply();
         firstRunPreferences.edit().putBoolean("firstRun", true).commit();
      }*/
      chooseDietType();
      isFirstRun();
      newDietListAdapter = new NewDietListAdapter(NewDietListActivity.this, dietList);
      newDietListLv.setAdapter(newDietListAdapter);
      newDietListLv.setOnItemLongClickListener(this);
   }
   
   public void isFirstRun() {
      //preferences = getSharedPreferences(personId + "menu" + String.valueOf(i), MODE_PRIVATE);
      if (idPreferences.getBoolean(personId, false)) {
         breakfast = getResources().getStringArray(breakfastArray);
         lunch = getResources().getStringArray(lunchArray);
         snacks = getResources().getStringArray(snacksArray);
         dinner = getResources().getStringArray(dinnerArray);
         dietList.add(breakfast);
         dietList.add(lunch);
         dietList.add(snacks);
         dietList.add(dinner);
         
         for (int i = 0; i < dietList.size(); i++) {
   
            /*String filePath = getApplicationContext().getFilesDir().getParent()+"/shared_prefs/menu"+ String.valueOf(i) +".xml";
            File deletePrefFile = new File(filePath );
            deletePrefFile.delete();*/
            
            preferences = getSharedPreferences(personId + "menu" + String.valueOf(i), MODE_PRIVATE);
            editor = preferences.edit();
            editor.clear().apply();
            MENU_SIZE = i;
            for (int j = 0; j < dietList.get(i).length; j++) {
               editor.putString("item" + String.valueOf(j), dietList.get(i)[j]);
               ITEM_SIZE = j;
            }
            editor.putInt("item_size", ITEM_SIZE + 1);
            editor.apply();
         }
         //Toast.makeText(this, "first time run", Toast.LENGTH_SHORT).show();
         //firstRunPreferences.edit().putBoolean("firstRun", false).apply();
         idPreferences.edit().putBoolean(personId, false).apply();
      }
      else {
         for (int i = 0; i < 4; i++) {
            preferences = getSharedPreferences(personId + "menu" + String.valueOf(i), MODE_PRIVATE);
            ITEM_SIZE = preferences.getInt("item_size", 0);
            String[] menu = new String[ITEM_SIZE];
            for (int j = 0; j < ITEM_SIZE; j++) {
               menu[j] = preferences.getString("item" + String.valueOf(j), null);
            }
            dietList.add(menu);
         }
         //Toast.makeText(this, "Not the first time run", Toast.LENGTH_SHORT).show();
      }
   }
   
   public void restoreDefault(int position) {
      if (idPreferences.getBoolean(personId, false)){
         preferences = getSharedPreferences(personId + "menu" + String.valueOf(position), MODE_PRIVATE);
         editor = preferences.edit();
         dietList.remove(position);
         if(position == 0) {
            breakfast = getResources().getStringArray(breakfastArray);
            dietList.add(position, breakfast);
            for (int j = 0; j < dietList.get(position).length; j++) {
               editor.putString("item" + String.valueOf(j), dietList.get(position)[j]);
               ITEM_SIZE = j;
            }
            editor.putInt("item_size", ITEM_SIZE + 1).apply();
         }
         else if (position == 1) {
            lunch = getResources().getStringArray(lunchArray);
            dietList.add(position, lunch);
            for (int j = 0; j < dietList.get(position).length; j++) {
               editor.putString("item" + String.valueOf(j), dietList.get(position)[j]);
               ITEM_SIZE = j;
            }
            editor.putInt("item_size", ITEM_SIZE + 1).apply();
         }
         else if (position == 2) {
            snacks = getResources().getStringArray(snacksArray);
            dietList.add(position, snacks);
            for (int j = 0; j < dietList.get(position).length; j++) {
               editor.putString("item" + String.valueOf(j), dietList.get(position)[j]);
               ITEM_SIZE = j;
            }
            editor.putInt("item_size", ITEM_SIZE + 1).apply();
         }
         else {
            dinner = getResources().getStringArray(dinnerArray);
            dietList.add(position, dinner);
            for (int j = 0; j < dietList.get(position).length; j++) {
               editor.putString("item" + String.valueOf(j), dietList.get(position)[j]);
               ITEM_SIZE = j;
            }
            editor.putInt("item_size", ITEM_SIZE + 1).apply();
         }
      }
      newDietListAdapter.clear();
      //firstRunPreferences.edit().putBoolean("firstRun", false).apply();
      idPreferences.edit().putBoolean(personId, false).apply();
   }
   
   public void chooseDietType() {
      
      profileModels = dataStorage.getProfileModelByPersonID(personId);
      String birthDt = (profileModels.get(0)).getDateOfBirth();
      String majorDisease = (profileModels.get(0)).getMajorDisease();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date birthDate = null;
      try {
         birthDate=sdf.parse(birthDt);
      } catch (ParseException e) {
         e.printStackTrace();
      }
      int ageOfPerson= Utility.getAgeInYears(birthDate);
   
      if(majorDisease.equals("Diabetes")){
         breakfastArray = R.array.diabetes_diet_breakfast;
         lunchArray = R.array.diabetes_diet_lunch;
         snacksArray = R.array.diabetes_diet_snacks;
         dinnerArray = R.array.diabetes_diet_dinner;
      }
      else if (majorDisease.equals("High BP")) {
         breakfastArray = R.array.hbp_diet_breakfast;
         lunchArray = R.array.hbp_diet_lunch;
         snacksArray = R.array.hbp_diet_snacks;
         dinnerArray = R.array.hbp_diet_dinner;
      }
      else if (majorDisease.equals("Low BP")) {
         breakfastArray = R.array.lbp_diet_breakfast;
         lunchArray = R.array.lbp_diet_lunch;
         snacksArray = R.array.lbp_diet_snacks;
         dinnerArray = R.array.lbp_diet_dinner;
      }
      else if (majorDisease.equals("Pancreatic")) {
         breakfastArray = R.array.pancreatic_diet_breakfast;
         lunchArray = R.array.pancreatic_diet_lunch;
         snacksArray = R.array.pancreatic_diet_snacks;
         dinnerArray = R.array.pancreatic_diet_dinner;
      }
      else if (majorDisease.equals("No Disease")) {
         if (ageOfPerson >= 20 && ageOfPerson < 30) {
            breakfastArray = R.array.age20s_diet_breakfast;
            lunchArray = R.array.age20s_diet_lunch;
            snacksArray = R.array.age20s_diet_snacks;
            dinnerArray = R.array.age20s_diet_dinner;
         }
         else if (ageOfPerson >= 30 && ageOfPerson < 40) {
            breakfastArray = R.array.age30s_diet_breakfast;
            lunchArray = R.array.age30s_diet_lunch;
            snacksArray = R.array.age30s_diet_snacks;
            dinnerArray = R.array.age30s_diet_dinner;
         }
         else if (ageOfPerson >= 40 && ageOfPerson < 50) {
            breakfastArray = R.array.age40s_diet_breakfast;
            lunchArray = R.array.age40s_diet_lunch;
            snacksArray = R.array.age40s_diet_snacks;
            dinnerArray = R.array.age40s_diet_dinner;
         }
         else {
            breakfastArray = R.array.age20s_diet_breakfast;
            lunchArray = R.array.age20s_diet_lunch;
            snacksArray = R.array.age20s_diet_snacks;
            dinnerArray = R.array.age20s_diet_dinner;
         }
      }
   }
   
   @Override
   public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
      final CharSequence[] items = {"Edit", "Restore Default"};
      final AlertDialog.Builder builder = new AlertDialog.Builder(NewDietListActivity.this);
      builder.setTitle("User Action");
      builder.setItems(items, new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int item) {
            if (items[item].equals("Edit")) {
               
               Intent intent = new Intent(NewDietListActivity.this, NewEditDietActivity.class);
               intent.putExtra("position", position);
               intent.putExtra("person_id", personId);
               startActivity(intent);
            }
            
            else if (items[item].equals("Restore Default")) {
               
               new Builder(NewDietListActivity.this).setTitle("Restore Default").setMessage("Are you sure you want to Restore The Default Diet List?").setPositiveButton("NO", new OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                     
                  }
               }).setNegativeButton("YES", new OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                     
                     //firstRunPreferences.edit().putBoolean("firstRun", true).apply();
                     idPreferences.edit().putBoolean(personId, true).apply();
                     restoreDefault(position);
                     isFirstRun();
                     
                     newDietListAdapter = new NewDietListAdapter(NewDietListActivity.this, dietList);
                     newDietListLv.setAdapter(newDietListAdapter);
                     //newDietListLv.invalidateViews();
                     //newDietListAdapter.notifyDataSetChanged();
                  }
               }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
         }
      });
      
      builder.show();
      return true;
   }
}
