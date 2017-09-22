package com.finalproject.takmilul.icare.fragment;//

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.activity.AddDietActivity;
import com.finalproject.takmilul.icare.adapter.DietListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.DietModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TodaysDietListFragment extends Fragment {

   ArrayList<DietModel> dietModels;
   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   ListView dietTodaysChartLV;
   String formattedDate;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.todays_diet_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      dietModels = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences=getActivity().getSharedPreferences("person_id", 0);
      personID =preferences.getString("person_id", "");

      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());

      dietTodaysChartLV = (ListView) getActivity().findViewById(R.id.dietTodaysChart);

      showDietTodays();
   }

   @Override
   public void onStart() {
      super.onStart();
      showDietTodays();
   }

   private void showDietTodays() {
      dietModels = dataStorage.getDietModelByPersonIDandDietDate(personID,formattedDate , "=");
      DietListAdapter dietListAdapter = new DietListAdapter(getActivity(), dietModels);
      dietTodaysChartLV.setAdapter(dietListAdapter);
      dietListAdapter.notifyDataSetChanged();
      dietTodaysChartLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final CharSequence[] items = { "Update", "Delete"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("User Action");
            builder.setItems(items, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int item) {
                  if (items[item].equals("Update")) {

                     Intent intent = new Intent(getActivity(), AddDietActivity.class);
                     String diet_id_update = String.valueOf((dietModels.get(position)).getDietId());

                     preferences = getActivity().getSharedPreferences("diet_id_update", 0);
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putString("diet_id_update", diet_id_update);
                     editor.apply();
                     editor.commit();

                     startActivity(intent);


                  } else if (items[item].equals("Delete")) {

                     new AlertDialog.Builder(getActivity())
                     .setTitle("Delete Entry")
                     .setMessage("Are you sure you want to Delete This Entry ?")
                     .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           int dietId = dietModels.get(position).getDietId();
                           boolean delete = dataStorage.deleteDiet(dietId,"D");
                           if (delete) {
                              Toast.makeText(getActivity(), "Diet Information Deleted Successfully", Toast.LENGTH_LONG).show();
                           } else {
                              Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                           }
                           showDietTodays();
                        }
                     })
                     .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // do nothing
                        }
                     })
                     .setIcon(android.R.drawable.ic_dialog_alert)
                     .show();
                  }
               }
            });
            builder.show();
            return true;
         }
      });

   }
}
