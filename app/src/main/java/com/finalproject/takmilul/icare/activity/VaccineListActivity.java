package com.finalproject.takmilul.icare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.finalproject.takmilul.icare.R;
import com.finalproject.takmilul.icare.adapter.ViewPagerAdapter;
import com.finalproject.takmilul.icare.fragment.CompletedVaccineListFragment;
import com.finalproject.takmilul.icare.fragment.TodaysVaccineListFragment;
import com.finalproject.takmilul.icare.fragment.UpcomingVaccineListFragment;

public class VaccineListActivity extends AppCompatActivity {

   Toolbar toolbar;
   TabLayout tabLayout;
   ViewPager viewPager;
   SharedPreferences preferences;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_vaccine_list);

      toolbar = (Toolbar) findViewById(R.id.vaccineToolbar);
      tabLayout = (TabLayout) findViewById(R.id.vaccineTabs);
      viewPager = (ViewPager) findViewById(R.id.vaccineViewpager);


      /*final TabLayout.Tab today = tabLayout.newTab();
      final TabLayout.Tab upcoming = tabLayout.newTab();
      final TabLayout.Tab completed = tabLayout.newTab();

      today.setText("Today");
      upcoming.setText("upcoming");
      completed.setText("completed");

      tabLayout.addTab(today, 0);
      tabLayout.addTab(upcoming, 1);
      tabLayout.addTab(completed, 2);*/

      preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);

      toolbar.setTitle("Vaccine List");
      toolbar.setTitleTextColor(Color.WHITE);
      setSupportActionBar(toolbar);
      assert getSupportActionBar() != null;
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   
      /*final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
      upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
      getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
      
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
      adapter.addFragment(new TodaysVaccineListFragment(), "Today");
      adapter.addFragment(new UpcomingVaccineListFragment(), "Upcoming");
      adapter.addFragment(new CompletedVaccineListFragment(), "Completed");
      viewPager.setAdapter(adapter);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_vaccine_list, menu);
      /*TextView textView = (TextView) menu.findItem(R.id.menu_add_vaccine);
      textView.setTextColor(Color.WHITE);*/
      MenuItem menuItem = menu.getItem(0);
      CharSequence menuTitle = menuItem.getTitle();
      SpannableString styledMenuTitle = new SpannableString(menuTitle);
      styledMenuTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), 0, menuTitle.length(), 0);
      menuItem.setTitle(styledMenuTitle);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      //noinspection SimplifiableIfStatement
      if (id == R.id.menu_add_vaccine) {
         Intent intent = new Intent(VaccineListActivity.this, AddVaccinationActivity.class);
         preferences = getBaseContext().getSharedPreferences("vaccine_id_update", MODE_PRIVATE);
         SharedPreferences.Editor editor = preferences.edit();
         editor.putString("vaccine_id_update", "");
         editor.apply();
         editor.commit();
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
