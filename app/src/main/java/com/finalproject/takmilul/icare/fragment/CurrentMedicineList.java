package com.finalproject.takmilul.icare.fragment;//

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
import com.finalproject.takmilul.icare.activity.AddMedicineActivity;
import com.finalproject.takmilul.icare.adapter.MedicineListAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.model.MedicineModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CurrentMedicineList extends Fragment {
   
   ListView medicineLV;
   DataStorage dataStorage;
   String personID;
   SharedPreferences preferences;
   String formattedDate;
   ArrayList<MedicineModel> medicineModels;
   boolean hasCurrentMedicine;
   boolean hasCompletedMedicine = false;
   
   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.current_medicine_list_fragment, container, false);
   }
   
   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      
      medicineLV = (ListView) getActivity().findViewById(R.id.medicineLV);
      medicineModels = new ArrayList<>();
      dataStorage = new DataStorage(getActivity());
      preferences = getActivity().getSharedPreferences("person_id", 0);
      personID = preferences.getString("person_id", "");
      
      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      formattedDate = df.format(c.getTime());
      
      showMedicineList();
   }
   
   @Override
   public void onStart() {
      super.onStart();
      showMedicineList();
   }
   
   private void showMedicineList() {
      
      hasCurrentMedicine = false;
      hasCompletedMedicine = false;
      
      medicineModels = dataStorage.getMedicineModelByPersonID(personID, formattedDate, "<");
      if (medicineModels.size() > 0) {
         hasCompletedMedicine = true;
      }
      
      medicineModels = dataStorage.getMedicineModelByPersonID(personID, formattedDate, ">");
      if (medicineModels.size() > 0) {
         hasCurrentMedicine = true;
      }
      
      if (!hasCurrentMedicine && !hasCompletedMedicine) {
         final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
         builder.setTitle("Warning");
         builder.setMessage("No medicine has been added. Please add a new medicine.");
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
               
               Intent intent = new Intent(getContext(), AddMedicineActivity.class);
               startActivity(intent);
               builder.setCancelable(false);
               dialog.cancel();
            }
         });
         builder.setCancelable(false);
         builder.create().show();
      }
      
      MedicineListAdapter medicineListAdapter = new MedicineListAdapter(getActivity(), medicineModels);
      medicineLV.setAdapter(medicineListAdapter);
      medicineListAdapter.notifyDataSetChanged();
      medicineLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final CharSequence[] items = {"Update", "Delete"};
            
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("User Action");
            builder.setItems(items, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int item) {
                  if (items[item].equals("Update")) {
                     
                     Intent intent = new Intent(getActivity(), AddMedicineActivity.class);
                     String medicine_id_update = String.valueOf((medicineModels.get(position)).getmId());
                     
                     preferences = getActivity().getSharedPreferences("medicine_id_update", 0);
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putString("medicine_id_update", medicine_id_update);
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
                           
                           int mID = medicineModels.get(position).getmId();
                           boolean delete = dataStorage.deleteMedicine(mID, "D");
                           if (delete) {
                              Toast.makeText(getActivity(), "Information Deleted Successfully", Toast.LENGTH_LONG).show();
                           }
                           else {
                              Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                           }
                           showMedicineList();
                           
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


