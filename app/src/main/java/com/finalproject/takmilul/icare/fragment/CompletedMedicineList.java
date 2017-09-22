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
import com.finalproject.takmilul.icare.adapter.MedicineListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.MedicineModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CompletedMedicineList extends Fragment {

   ListView medicinePrevLV;
   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   String formattedDate;
   ArrayList<MedicineModel> medicineModelsPrev;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.completed_medicine_list_fragment, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      medicinePrevLV = (ListView) getActivity().findViewById(R.id.medicinePrevLV);
      medicineModelsPrev = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID = preferences.getString("person_id", "");

      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());
      
      showMedicinePrevList();
   }

   private void showMedicinePrevList() {
      medicineModelsPrev = dataStorage.getMedicineModelByPersonID(personID, formattedDate, "<");
      MedicineListAdapter medicineListAdapter = new MedicineListAdapter(getActivity(), medicineModelsPrev);
      medicinePrevLV.setAdapter(medicineListAdapter);
      medicineListAdapter.notifyDataSetChanged();
   }

   @Override
   public void onStart() {
      super.onStart();
      showMedicinePrevList();
   }
}
