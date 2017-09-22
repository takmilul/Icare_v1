package com.finalproject.takmilul.icare.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.adapter.VaccineListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.VaccineModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CompletedVaccineListFragment extends Fragment {

   ArrayList<VaccineModel> vaccineModelsCompleted;
   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   ListView vaccineLVUpCompleted;
   String formattedDate;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.completed_vaccine_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      vaccineLVUpCompleted = (ListView) getActivity().findViewById(R.id.completedVaccineLv);
      vaccineModelsCompleted = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID = preferences.getString("person_id", "");
      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());
      
      showVaccineCompleted();
   }

   @Override
   public void onStart() {
      super.onStart();
      showVaccineCompleted();
   }

   @Override
   public void onResume() {
      super.onResume();
      //showVaccineCompleted();
   }

   private void showVaccineCompleted() {
      
      vaccineModelsCompleted = dataStorage.getVaccineModelByPersonIDCompletedUpComing(personID, formattedDate, "<");
      VaccineListAdapter vaccineListAdapter = new VaccineListAdapter(getActivity(), vaccineModelsCompleted);
      vaccineLVUpCompleted.setAdapter(vaccineListAdapter);
      vaccineListAdapter.notifyDataSetChanged();
   }
}
