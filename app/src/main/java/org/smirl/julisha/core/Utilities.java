package org.smirl.julisha.core;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import org.smirl.julisha.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    public static void confirm(Context context, String title, String message, final UtilityListener listener) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener != null)listener.onAccept();
                    }
                })
                .create()
                .show();
    }

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

    public static boolean checkInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //   Network activeNetwork = cm.getActiveNetwork();
        //  return activeNetwork != null && activeNetwork.;
        return true;
    }

    public static class dateUtilities {

        public static String dateToString(Date date, String template) {
            return dateToString(date, template, Locale.getDefault());
        }

        public static String dateToString(Date date, String template, Locale locale) {
            return new SimpleDateFormat(template, locale).format(date);
        }

        public static String today(String template, Locale locale){
            return dateToString(new Date(), template, locale);
        }
    }

    public static void dateChooser(LayoutInflater inflater, final CalendarListener listener){
        View view = inflater.inflate(R.layout.date_selector, null, false);
        final DatePicker dp = view.findViewById(R.id.date_picker);

        new AlertDialog.Builder(inflater.getContext())
                // .setTitle("Choose Date")
                .setView(view)
                .setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar cc = Calendar.getInstance();
                        cc.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                        if(listener != null)listener.onChosen(cc);
                    }
                })
                .setNeutralButton("CANCEL", null)
                .create()
                .show();

    }


    /** listeners **/

    public interface UtilityListener {
        void onAccept();
    }

    public interface CalendarListener {
        void onChosen(Calendar date);
    }
}
