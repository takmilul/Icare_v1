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
import com.finalproject.takmilul.icare.fragment.CompletedAppointmentListFragment;
import com.finalproject.takmilul.icare.fragment.TodaysAppointmentListFragment;
import com.finalproject.takmilul.icare.fragment.UpcomingAppointmentListFragment;

public class AppointmentListActivity extends AppCompatActivity {

   Toolbar toolbar;
   TabLayout tabLayout;
   ViewPager viewPager;
   SharedPreferences preferences;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_appoinment_list);

      toolbar = (Toolbar) findViewById(R.id.appointmentToolbar);
      tabLayout = (TabLayout) findViewById(R.id.appointmentTabLayout);
      viewPager = (ViewPager) findViewById(R.id.appointmentViewpager);

      toolbar.setTitle("Appointment List");
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
      adapter.addFragment(new TodaysAppointmentListFragment(), "Today");
      adapter.addFragment(new UpcomingAppointmentListFragment(), "Upcoming");
      adapter.addFragment(new CompletedAppointmentListFragment(), "Completed");
      viewPager.setAdapter(adapter);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_appoinment_list, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();

      if (id == R.id.menu_add_appointment) {
         Intent intent = new Intent(AppointmentListActivity.this, AddAppoinmentActivity.class);
         preferences = getBaseContext().getSharedPreferences("appointment_id_update", MODE_PRIVATE);
         SharedPreferences.Editor editor = preferences.edit();
         editor.putString("appointment_id_update", "");
         editor.apply();
         editor.commit();
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public void onBackPressed() {
      super.onBackPressed();
      finish();
   }
}
