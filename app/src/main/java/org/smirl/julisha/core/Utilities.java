package org.smirl.julisha.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Utilities {

    public static void toastIt(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static void snackIt(View view, String info) {
        Snackbar.make(view, info, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    public static boolean checkInternetAvailable(Context context){
      ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
   //   Network activeNetwork = cm.getActiveNetwork();
    //  return activeNetwork != null && activeNetwork.;
return true;
    }
}
