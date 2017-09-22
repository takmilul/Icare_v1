package com.finalproject.takmilul.icare.fragment;

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

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.activity.AppointmentDetailsActivity;
import com.finalproject.takmilul.icare.adapter.AppointmentListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.AppointmentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CompletedAppointmentListFragment extends Fragment {

   ArrayList<AppointmentModel> appointmentModelsPrev;

   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   String formattedDate;
   ListView apptPrevChart;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.completed_appointment_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      apptPrevChart = (ListView) getActivity().findViewById(R.id.apptPrevChart);
      appointmentModelsPrev = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID =preferences.getString("person_id", "");

      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());
      showPreviousAppointment();
   }

   @Override
   public void onStart() {
      super.onStart();
      showPreviousAppointment();
   }

   private void showPreviousAppointment()
   {
      appointmentModelsPrev = dataStorage.getAppointmentModelByPersonIDandDate(personID,formattedDate,"<");
      AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(getActivity(),appointmentModelsPrev);
      apptPrevChart.setAdapter(appointmentListAdapter);
      appointmentListAdapter.notifyDataSetChanged();
      apptPrevChart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id)
         {
            Intent intent = new Intent(getActivity(), AppointmentDetailsActivity.class);
            String appointment_id_show = String.valueOf((appointmentModelsPrev.get(position)).getApptId());

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
