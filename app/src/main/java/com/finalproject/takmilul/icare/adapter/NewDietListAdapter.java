package com.finalproject.takmilul.icare.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finalproject.takmilul.icare.R;

import java.util.ArrayList;

public class NewDietListAdapter extends ArrayAdapter {

   ArrayList<String[]> dietModels = new ArrayList<>();
   Context context;
   LayoutInflater inflater;

   public NewDietListAdapter(Context context, ArrayList<String[]> dietModels) {
      super(context, 0, dietModels);
      this.context = context;
      this.dietModels = dietModels;
   }

   public View getView(int position, View convertView, ViewGroup parent) {

      ViewHolder dietlHolder = new ViewHolder();
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
      layoutParams.setMargins(50, 0, 0, 20);
      if (convertView == null) {
         //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         inflater = LayoutInflater.from(context);
         convertView = inflater.inflate(R.layout.new_diet_list_row, parent, false);
         dietlHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.newDietListRow);
         dietlHolder.dietType = (TextView) convertView.findViewById(R.id.dietTypeTv);
         for (int i = 1; i < dietModels.get(position).length; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextSize(18);
            textView.setText(String.valueOf(i) + ". " + dietModels.get(position)[i]);
            dietlHolder.linearLayout.addView(textView);
         }
         convertView.setTag(dietlHolder);
      }
      else {
         dietlHolder = (ViewHolder) convertView.getTag();
      }
      dietlHolder.dietType.setText(dietModels.get(position)[0]);
      
      return convertView;
   }

   private static class ViewHolder {
      TextView dietType;
      LinearLayout linearLayout;
   }
}
