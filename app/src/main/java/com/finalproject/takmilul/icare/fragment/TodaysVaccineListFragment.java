package com.finalproject.takmilul.icare.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.finalproject.takmilul.icare.activity.AddVaccinationActivity;
import com.finalproject.takmilul.icare.adapter.VaccineListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.VaccineModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TodaysVaccineListFragment extends Fragment {

   ArrayList<VaccineModel> vaccineModelsToady;
   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   ListView vaccineLVToday;
   String formattedDate;
   boolean hasTodaysVaccine;
   boolean hasCompletedVaccine;
   boolean hasUpcomingVaccine;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //Toast.makeText(getActivity(), "OnCreateView() called", Toast.LENGTH_SHORT).show();
      return inflater.inflate(R.layout.todays_vaccine_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      //Toast.makeText(getActivity(), "OnActivityCreated() called", Toast.LENGTH_SHORT).show();
      vaccineLVToday = (ListView) getActivity().findViewById(R.id.todaysVaccineLv);
      vaccineModelsToady = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID = preferences.getString("person_id", "");
      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());

      showVaccineToday();
   }

   @Override
   public void onStart() {
      super.onStart();
      showVaccineToday();
      //Toast.makeText(getActivity(), "OnStart() called", Toast.LENGTH_SHORT).show();
   }

   @Override
   public void onResume() {
      super.onResume();
      //showVaccineToday();
      //Toast.makeText(getActivity(), "OnResume() called", Toast.LENGTH_SHORT).show();
   }

   private void showVaccineToday() {

      hasTodaysVaccine = false;
      hasCompletedVaccine = false;
      hasUpcomingVaccine = false;

      vaccineModelsToady = dataStorage.getVaccineModelByPersonIDCompletedUpComing(personID, formattedDate, "<");

      if (vaccineModelsToady.size() > 0) {
         hasCompletedVaccine = true;
      }

      vaccineModelsToady = dataStorage.getVaccineModelByPersonIDCompletedUpComing(personID, formattedDate, ">");
      if (vaccineModelsToady.size() > 0) {
         hasUpcomingVaccine = true;
      }

      vaccineModelsToady = dataStorage.getVaccineModelByPersonIDCompletedUpComing(personID, formattedDate, "=");
      if (vaccineModelsToady.size() > 0) {
         hasTodaysVaccine = true;
      }

      if (!hasTodaysVaccine && !hasUpcomingVaccine && !hasCompletedVaccine) {
         final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
         builder.setTitle("Warning");
         builder.setMessage("No vaccine has been added. Please add a new vaccine.");
         builder.setIcon(R.drawable.info);
         builder.setPositiveButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
               getActivity().finish();
            }
         });
         builder.setNegativeButton("Add", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               Intent intent = new Intent(getContext(), AddVaccinationActivity.class);
               startActivity(intent);
               builder.setCancelable(false);
               dialog.cancel();
            }
         });
         builder.setCancelable(false);
         builder.create().show();
      }

      VaccineListAdapter vaccineListAdapterToday = new VaccineListAdapter(getActivity(), vaccineModelsToady);
      vaccineLVToday.setAdapter(vaccineListAdapterToday);
      vaccineListAdapterToday.notifyDataSetChanged();
      vaccineLVToday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final CharSequence[] items = {"Update", "Delete"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("User Action");
            builder.setItems(items, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int item) {
                  if (items[item].equals("Update")) {

                     Intent intent = new Intent(getActivity(), AddVaccinationActivity.class);
                     String vaccine_id_update = String.valueOf((vaccineModelsToady.get(position)).getvId());

                     preferences = getActivity().getSharedPreferences("vaccine_id_update", 0);
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putString("vaccine_id_update", vaccine_id_update);
                     editor.apply();
                     editor.commit();
                     startActivity(intent);

                  }
                  else if (items[item].equals("Delete")) {

                     new AlertDialog.Builder(getActivity()).setTitle("Delete Entry").setMessage("Are you sure you want to Delete this Entry  ?").setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // do nothing
                        }
                     }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // continue with delete

                           int vacID = vaccineModelsToady.get(position).getvId();
                           boolean delete = dataStorage.deleteVaccine(vacID, "D");
                           if (delete) {
                              Toast.makeText(getActivity(), "Vaccine Information Deleted Successfully", Toast.LENGTH_LONG).show();
                           }
                           else {
                              Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                           }
                           showVaccineToday();
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
}


