package com.finalproject.takmilul.icare.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.finalproject.takmilul.icare.R;

import java.util.ArrayList;
import java.util.Random;

public class NewEditDietActivity extends AppCompatActivity implements OnClickListener {
   
   int position;
   int itemSize;
   String[] items;
   String personId;
   EditText itemEt;
   Random randomNumber;
   Button deleteBtn;
   Button addItemBtn;
   LinearLayout layout;
   LinearLayout editDietInnerLayout;
   LinearLayout editDietOuterLayout;
   LinearLayout.LayoutParams layoutParams;
   LinearLayout.LayoutParams itemTvParams;
   LinearLayout.LayoutParams addItemParams;
   LinearLayout.LayoutParams deleteBtnParams;
   ArrayList<String[]> dietList;
   ArrayList<Button> allDeleteBtn;
   ArrayList<EditText> allItemEt;
   SharedPreferences preferences;
   SharedPreferences.Editor editor;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_edit_diet);
      editDietInnerLayout = (LinearLayout) findViewById(R.id.editDietListInnerLayout);
      editDietOuterLayout = (LinearLayout) findViewById(R.id.editDietListOuterLayout);
      position = getIntent().getIntExtra("position", -1);
      personId = getIntent().getStringExtra("person_id");
      preferences = getSharedPreferences(personId + "menu" + String.valueOf(position), MODE_PRIVATE);
      dietList = new ArrayList<>();
      allDeleteBtn = new ArrayList<>();
      allItemEt = new ArrayList<>();
      randomNumber = new Random();
      getDietItems();
      setDietItems();
      addNewItemBtn();
      addItemBtn.setOnClickListener(this);
   }
   
   public void deleteBtnListener() {
      for (int i = 0; i < allDeleteBtn.size(); i++) {
         allDeleteBtn.get(i).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               layout = (LinearLayout) v.getParent();
               layout.setVisibility(View.GONE);
               EditText edit = (EditText) layout.getChildAt(0);
               Button btn = (Button) layout.getChildAt(1);
               allDeleteBtn.remove(btn);
               allItemEt.remove(edit);
            }
         });
      }
   }
   
   public void getDietItems() {
      itemSize = preferences.getInt("item_size", 0);
      items = new String[itemSize];
      for (int j = 0; j < itemSize; j++) {
         items[j] = preferences.getString("item" + String.valueOf(j), null);
      }
   }
   
   @TargetApi(VERSION_CODES.JELLY_BEAN)
   public void setDietItems() {
      for (int i = 1; i < itemSize; i++) {
         layout = new LinearLayout(this);
         layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 100f);
         itemTvParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 85f);
         deleteBtnParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 15f);
         itemTvParams.gravity = Gravity.CENTER_VERTICAL;
         deleteBtnParams.gravity = Gravity.CENTER_VERTICAL;
         layoutParams.setMargins(0, 0, 0, 14);
         deleteBtnParams.setMargins(7, 7, 7, 7);
         layout.setLayoutParams(layoutParams);
         layout.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
         layout.setOrientation(LinearLayout.HORIZONTAL);
         layout.setBackground(getResources().getDrawable(R.drawable.shape_list_row));
         //float scale = getResources().getDisplayMetrics().density;
         itemEt = new EditText(this);
         itemEt.setLayoutParams(itemTvParams);
         itemEt.setText(items[i]);
         deleteBtn = new Button(this);
         deleteBtn.setLayoutParams(deleteBtnParams);
         deleteBtn.setBackgroundResource(R.drawable.cross_black);
         allDeleteBtn.add(deleteBtn);
         allItemEt.add(itemEt);
         layout.addView(itemEt);
         layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
         layout.addView(deleteBtn);
         editDietInnerLayout.addView(layout);
      }
      deleteBtnListener();
   }
   
   @TargetApi(VERSION_CODES.JELLY_BEAN)
   public void addNewItemBtn() {
      addItemParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
      addItemParams.setMargins(0, 10, 0, 0);
      addItemParams.gravity = Gravity.CENTER_HORIZONTAL;
      addItemBtn = new Button(this);
      addItemBtn.setLayoutParams(addItemParams);
      addItemBtn.setBackgroundResource(R.drawable.btn);
      addItemBtn.setText("Add New Item");
      addItemBtn.setAllCaps(false);
      addItemBtn.setPadding(15, 15, 15, 15);
      addItemBtn.setGravity(Gravity.CENTER);
      editDietOuterLayout.addView(addItemBtn);
   }
   
   @TargetApi(VERSION_CODES.JELLY_BEAN)
   @Override
   public void onClick(View v) {
//      int random = randomNumber.nextInt(10000);
      layout = new LinearLayout(this);
      layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 100f);
      itemTvParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 85f);
      deleteBtnParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 15f);
      itemTvParams.gravity = Gravity.CENTER_VERTICAL;
      deleteBtnParams.gravity = Gravity.CENTER_VERTICAL;
      layoutParams.setMargins(0, 0, 0, 14);
      deleteBtnParams.setMargins(7, 7, 7, 7);
      layout.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
      layout.setLayoutParams(layoutParams);
      layout.setOrientation(LinearLayout.HORIZONTAL);
      layout.setBackground(getResources().getDrawable(R.drawable.shape_list_row));
      //float scale = getResources().getDisplayMetrics().density;
      itemEt = new EditText(this);
      itemEt.setLayoutParams(itemTvParams);
      
      deleteBtn = new Button(this);
      deleteBtn.setLayoutParams(deleteBtnParams);
      deleteBtn.setBackgroundResource(R.drawable.cross_black);
      
      allItemEt.add(itemEt);
      allDeleteBtn.add(deleteBtn);
      layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
      layout.addView(itemEt);
      layout.addView(deleteBtn);
      editDietInnerLayout.addView(layout);
      
      deleteBtnListener();
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_diet_list, menu);
      return super.onCreateOptionsMenu(menu);
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == R.id.add_diet_menu) {
         SharedPreferences.Editor editor = preferences.edit();
         String dietMenu = preferences.getString("item" + String.valueOf(0), null);
         editor.clear();
         editor.commit();
         editor.putString("item0", dietMenu);
         for (int i = 0; i < allItemEt.size(); i++) {
            String dietItem = allItemEt.get(i).getText().toString().replaceAll("( )+", " ").trim();
            if (dietItem.length() > 0) {
               editor.putString("item" + String.valueOf(i + 1), dietItem);
               NewDietListActivity.ITEM_SIZE = i;
            }
         }
         editor.putInt("item_size", NewDietListActivity.ITEM_SIZE + 2);
         editor.commit();
      }
      Toast.makeText(NewEditDietActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(NewEditDietActivity.this, NewDietListActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
      return super.onOptionsItemSelected(item);
   }
}
