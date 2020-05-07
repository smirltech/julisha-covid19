package org.smirl.julisha.core;

import android.content.Context;


public class SessionManager {

  PrefManager prfm;

  public SessionManager(Context context) {
    prfm = new PrefManager(context);
  }

  public void firstTimeAsking(String permission, boolean isFirstTime) {
    prfm.putBoolean(permission, isFirstTime);
  }

  public boolean isFirstTimeAsking(String permission) {
    return prfm.getBoolean(permission, true);
  }



}