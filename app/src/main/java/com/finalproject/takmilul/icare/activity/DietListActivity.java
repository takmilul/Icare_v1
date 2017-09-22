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
import com.finalproject.takmilul.icare.fragment.CompletedDietListFragment;
import com.finalproject.takmilul.icare.fragment.TodaysDietListFragment;
import com.finalproject.takmilul.icare.fragment.UpcomingDietListFragment;

public class DietListActivity extends AppCompatActivity {

   SharedPreferences preferences;

   Toolbar toolbar;
   TabLayout tabLayout;
   ViewPager viewPager;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_diet_list);

      toolbar = (Toolbar) findViewById(R.id.dietToolbar);
      tabLayout = (TabLayout) findViewById(R.id.dietTabLayout);
      viewPager = (ViewPager) findViewById(R.id.dietViewpager);

      toolbar.setTitle("Diet List");
      toolbar.setTitleTextColor(Color.WHITE);
      setSupportActionBar(toolbar);
      //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
      adapter.addFragment(new TodaysDietListFragment(), "Today");
      adapter.addFragment(new UpcomingDietListFragment(), "Upcoming");
      adapter.addFragment(new CompletedDietListFragment(), "Completed");
      viewPager.setAdapter(adapter);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_diet_list, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();

      if (id == R.id.add_diet_menu) {
         preferences = getBaseContext().getSharedPreferences("diet_id_update", MODE_PRIVATE);
         SharedPreferences.Editor editor1 = preferences.edit();
         editor1.putString("diet_id_update", "");
         editor1.apply();
         editor1.commit();
         Intent intent = new Intent(DietListActivity.this, AddDietActivity.class);
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
