package com.finalproject.takmilul.icare.activity;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;



public class MyApplication extends Application {
   
   protected void attachBaseContext(Context base) {
      super.attachBaseContext(base);
      MultiDex.install(this);
   }
}
