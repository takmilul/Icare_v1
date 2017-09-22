package com.finalproject.takmilul.icare.fragment;

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
import com.finalproject.takmilul.icare.activity.AddVaccinationActivity;
import com.finalproject.takmilul.icare.adapter.VaccineListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.VaccineModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpcomingVaccineListFragment extends Fragment {

   ArrayList<VaccineModel> vaccineModelsUp;
   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   ListView vaccineLVUpcoming;
   String formattedDate;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.upcoming_vaccine_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      vaccineLVUpcoming = (ListView) getActivity().findViewById(R.id.upcomingVaccineLv);
      vaccineModelsUp = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID = preferences.getString("person_id", "");
      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());
      
      showVaccineUpcoming();
   }

   @Override
   public void onStart() {
      super.onStart();
      showVaccineUpcoming();
   }

   @Override
   public void onResume() {
      super.onResume();
      //showVaccineUpcoming();
   }

   private void showVaccineUpcoming() {
      
      vaccineModelsUp = dataStorage.getVaccineModelByPersonIDCompletedUpComing(personID, formattedDate, ">");
      VaccineListAdapter vaccineListAdapterUp = new VaccineListAdapter(getActivity(), vaccineModelsUp);
      vaccineLVUpcoming.setAdapter(vaccineListAdapterUp);
      vaccineListAdapterUp.notifyDataSetChanged();
      vaccineLVUpcoming.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                     String vaccine_id_update = String.valueOf((vaccineModelsUp.get(position)).getvId());

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
                           int vacID = vaccineModelsUp.get(position).getvId();
                           boolean delete = dataStorage.deleteVaccine(vacID, "D");
                           if (delete) {
                              Toast.makeText(getActivity(), "Vaccine Information Deleted Successfully", Toast.LENGTH_LONG).show();
                           }
                           else {
                              Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                           }
                           showVaccineUpcoming();
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


