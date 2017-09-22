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
import com.finalproject.takmilul.icare.activity.AddAppoinmentActivity;
import com.finalproject.takmilul.icare.activity.AppointmentDetailsActivity;
import com.finalproject.takmilul.icare.adapter.AppointmentListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.AppointmentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TodaysAppointmentListFragment extends Fragment {

   ArrayList<AppointmentModel> appointmentModelsNow;

   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   String formattedDate;
   ListView aaptTodayChart;
   boolean hasTodaysAppointment;
   boolean hasCompletedAppointment;
   boolean hasUpcomingAppointment;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.todays_appointment_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      aaptTodayChart = (ListView) getActivity().findViewById(R.id.aaptTodayChart);
      appointmentModelsNow = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID = preferences.getString("person_id", "");

      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());
      showTodayAppointment();
   }

   @Override
   public void onStart() {
      super.onStart();
      showTodayAppointment();
   }

   private void showTodayAppointment() {

      hasTodaysAppointment = false;
      hasUpcomingAppointment = false;
      hasCompletedAppointment = false;

      appointmentModelsNow = dataStorage.getAppointmentModelByPersonIDandDate(personID, formattedDate, "<");

      if (appointmentModelsNow.size() > 0) {
         hasCompletedAppointment = true;
      }
      appointmentModelsNow = dataStorage.getAppointmentModelByPersonIDandDate(personID, formattedDate, ">");
      if (appointmentModelsNow.size() > 0) {
         hasUpcomingAppointment = true;
      }
      appointmentModelsNow = dataStorage.getAppointmentModelByPersonIDandDate(personID, formattedDate, "=");

      if (appointmentModelsNow.size() > 0) {
         hasTodaysAppointment = true;
      }

      if (!hasTodaysAppointment && !hasUpcomingAppointment && !hasCompletedAppointment) {
         final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
         builder.setTitle("Warning");
         builder.setMessage("No appointment has been added. Please add a new appointment.");
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

               Intent intent = new Intent(getContext(), AddAppoinmentActivity.class);
               startActivity(intent);
               builder.setCancelable(false);
               dialog.cancel();
            }
         });
         builder.setCancelable(false);
         builder.create().show();
      }

      AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(getActivity(), appointmentModelsNow);
      aaptTodayChart.setAdapter(appointmentListAdapter);
      appointmentListAdapter.notifyDataSetChanged();
      aaptTodayChart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final CharSequence[] items = {"Update", "Delete"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("User Action");
            builder.setItems(items, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int item) {
                  if (items[item].equals("Update")) {

                     Intent intent = new Intent(getActivity(), AddAppoinmentActivity.class);
                     String appointment_id_update = String.valueOf((appointmentModelsNow.get(position)).getApptId());

                     preferences = getActivity().getSharedPreferences("appointment_id_update", 0);
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putString("appointment_id_update", appointment_id_update);
                     editor.apply();
                     editor.commit();

                     startActivity(intent);

                  }
                  else if (items[item].equals("Delete")) {

                     new AlertDialog.Builder(getActivity()).setTitle("Delete Entry").setMessage("Are you sure you want to Delete This Entry  ?").setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // do nothing
                        }
                     }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // continue with delete
                           int apptId = appointmentModelsNow.get(position).getApptId();
                           boolean delete = dataStorage.deleteAppointment(apptId, "D");
                           if (delete) {
                              Toast.makeText(getActivity(), "Appointment Information Deleted Successfully", Toast.LENGTH_LONG).show();
                           }
                           else {
                              Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                           }
                           showTodayAppointment();

                        }
                     }).setIcon(android.R.drawable.ic_dialog_alert).show();
                  }
               }
            });
            builder.show();
            return true;
         }
      });
      aaptTodayChart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), AppointmentDetailsActivity.class);
            String appointment_id_show = String.valueOf((appointmentModelsNow.get(position)).getApptId());

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

