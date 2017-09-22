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
import com.finalproject.takmilul.icare.activity.AddAppoinmentActivity;
import com.finalproject.takmilul.icare.activity.AppointmentDetailsActivity;
import com.finalproject.takmilul.icare.adapter.AppointmentListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.AppointmentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpcomingAppointmentListFragment extends Fragment {

   ArrayList<AppointmentModel> appointmentModelsUP;

   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   String formattedDate;
   ListView aaptUpcomingChart;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.upcoming_appointment_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      aaptUpcomingChart = (ListView) getActivity().findViewById(R.id.aaptUpcomingChart);
      appointmentModelsUP = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID = preferences.getString("person_id", "");

      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());
      showUpcomingAppointment();
   }

   @Override
   public void onStart() {
      super.onStart();
      showUpcomingAppointment();
   }

   private void showUpcomingAppointment()
   {
      appointmentModelsUP = dataStorage.getAppointmentModelByPersonIDandDate(personID,formattedDate,">");
      AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(getActivity(),appointmentModelsUP);
      aaptUpcomingChart.setAdapter(appointmentListAdapter);
      appointmentListAdapter.notifyDataSetChanged();
      aaptUpcomingChart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final CharSequence[] items = { "Update", "Delete"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("User Action");
            builder.setItems(items, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int item) {
                  if (items[item].equals("Update")) {

                     Intent intent = new Intent(getActivity(), AddAppoinmentActivity.class);
                     String appointment_id_update = String.valueOf((appointmentModelsUP.get(position)).getApptId());

                     preferences = getActivity().getSharedPreferences("appointment_id_update", 0);
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putString("appointment_id_update", appointment_id_update);
                     editor.apply();
                     editor.commit();

                     startActivity(intent);


                  } else if (items[item].equals("Delete")) {

                     new AlertDialog.Builder(getActivity())
                     .setTitle("Delete Entry")
                     .setMessage("Are you sure you want to Delete This Entry  ?")
                     .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // do nothing
                        }
                     })
                     .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
      
                           int apptId = appointmentModelsUP.get(position).getApptId();
                           boolean delete = dataStorage.deleteAppointment(apptId,"D");
                           if (delete) {
                              Toast.makeText(getActivity(), "Appointment Information Deleted Successfully", Toast.LENGTH_LONG).show();
                           } else {
                              Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                           }
                           showUpcomingAppointment();
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
      aaptUpcomingChart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id)
         {
            Intent intent = new Intent(getActivity(), AppointmentDetailsActivity.class);
            String appointment_id_show = String.valueOf((appointmentModelsUP.get(position)).getApptId());

            preferences = getActivity().getSharedPreferences("appointment_id_show", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("appointment_id_show", appointment_id_show);
            editor.apply();
            editor.commit();

            startActivity(intent);

         }
      });
   }

}


