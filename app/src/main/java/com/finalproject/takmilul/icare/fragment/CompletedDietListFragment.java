package com.finalproject.takmilul.icare.fragment;//

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.adapter.DietListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.DietModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CompletedDietListFragment extends Fragment {

   ArrayList<DietModel> dietModelsPrev;
   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   ListView dietPrevChart;
   String formattedDate;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.completed_diet_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      dietModelsPrev = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID = preferences.getString("person_id", "");

      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());

      dietPrevChart = (ListView) getActivity().findViewById(R.id.dietPrevChart);

      showDietPrevious();
   }

   @Override
   public void onStart() {
      super.onStart();
      showDietPrevious();
   }

   private void showDietPrevious() {
      dietModelsPrev = dataStorage.getDietModelByPersonIDandDietDate(personID, formattedDate, "<");
      DietListAdapter dietListAdapter = new DietListAdapter(getActivity(), dietModelsPrev);
      dietPrevChart.setAdapter(dietListAdapter);
      dietListAdapter.notifyDataSetChanged();

   }
}
