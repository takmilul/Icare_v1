package com.finalproject.takmilul.icare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.adapter.ViewPagerAdapter;
import com.finalproject.takmilul.icare.database.DataStorage;
import com.finalproject.takmilul.icare.fragment.CurrentMedicineList;
import com.finalproject.takmilul.icare.fragment.CompletedMedicineList;

public class MedicineListActivity extends AppCompatActivity {

   Toolbar toolbar;
   TabLayout tabLayout;
   ViewPager viewPager;

   DataStorage dataStorage;
   SharedPreferences preferences;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_medicine_list);

      preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);

      toolbar = (Toolbar) findViewById(R.id.medicineToolbar);
      tabLayout = (TabLayout) findViewById(R.id.medicineTabs);
      viewPager = (ViewPager) findViewById(R.id.medicineViewpager);
      toolbar.setTitle("Medicine List");
      toolbar.setTitleTextColor(Color.WHITE);
      setSupportActionBar(toolbar);
      assert getSupportActionBar() != null;
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      setupViewPager(viewPager);

      tabLayout.post(new Runnable() {
         @Override
         public void run() {
            tabLayout.setupWithViewPager(viewPager);
         }
      });
   }

   private void setupViewPager(ViewPager viewPager) {
      ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
      adapter.addFragment(new CurrentMedicineList(), "Ongoing/Upcoming");
      adapter.addFragment(new CompletedMedicineList(), "Previous");
      viewPager.setAdapter(adapter);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_medicine_list, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();

      if (id == R.id.menu_add_medicine) {
         Intent intent = new Intent(MedicineListActivity.this, AddMedicineActivity.class);
         preferences = getBaseContext().getSharedPreferences("medicine_id_update", MODE_PRIVATE);
         SharedPreferences.Editor editor = preferences.edit();
         editor.putString("medicine_id_update", "");
         editor.apply();
         editor.commit();
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
