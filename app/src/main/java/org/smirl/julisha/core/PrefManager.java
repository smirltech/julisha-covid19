package org.smirl.julisha.core;

import android.content.Context;
import android.content.SharedPreferences;
import fnn.smirl.simple.Serializer;


public class PrefManager implements Constants {


  SharedPreferences pref;
  SharedPreferences.Editor editor;
  Context ctx;
  // shared pref mode
  int PRIVATE_MODE = 0;
  // Shared preferences file _option



  public PrefManager(Context context) {
    this.ctx = context;
    pref = ctx.getSharedPreferences(context.getPackageName(), PRIVATE_MODE);
    editor = pref.edit();
  }


  public SharedPreferences getPref() {
    return pref;
  }

  public SharedPreferences.Editor getEditor() {
    return editor;
  }


  public void putString(String key, String val) {
    getEditor().putString(key, val).apply();
  }


  public void putInt(String key, int val) {
    getEditor().putInt(key, val).apply();
  }

  public void putBoolean(String key, Boolean val) {
    getEditor().putBoolean(key, val).apply();
  }


  public void putLong(String key, long val) {
    getEditor().putLong(key, val).apply();
  }

  /***********************************************************************************************/

  public long getLong(String key, long defVal) {
    return getPref().getLong(key, defVal);
  }

  public int getInt(String key, int defVal) {
    return getPref().getInt(key, defVal);
  }


  public String getString(String k, String v) {
    return getPref().getString(k, v);
  }

  public String getString(String k) {
    return getPref().getString(k, "");
  }



  public void remove(String key) {
    getEditor().remove(key).apply();
  }

  public void putFloat(String k, float v) {
    getEditor().putFloat(k, v).apply();

  }



  public boolean getBoolean(String k, boolean v) {
    return pref.getBoolean(k, v);
  }
}