package org.smirl.julisha.core;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;


public class DialogFactory {
    Context ctx;

    public DialogFactory(Context ctx) {
        this.ctx = ctx;
    }


    public static void printError(Context ctx, String error) {
        error(ctx, error);
    }

    public static void printError(Context ctx, Exception e) {
        printError(ctx, e.getMessage());
    }

    public static void debug(Context context, String response) {
        //print(context, response);
    }

    public static void debugToast(Context ctx, String msg) {
        //Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();

    }

    public void printError(String response) {
        printError(ctx, response);
    }

    public void print(String response) {
        dialog(ctx, response);
    }

    public static void print(Context ctx, String response) {
        dialog(ctx, response);
    }

    public static void conError(Context ctx, String error) {

        String msg, title;
        if (error != null && error.contains("java.net")) {
            msg = "Impossible de se connecter à l'internet !";
            title = "No Internet";
        } else {
            title = "Unknown Error";
            msg = "Une erreur inconnue s'est produite.\nSi cela persiste veillez contactez Weza-Group.";
        }
        dialog(ctx, title, msg);
    }

    public static void dialog(Context context, String message) {
        dialog(context, null, message);
    }

    public static void dialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    public static void error(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle("ERREUR")
                //.setIcon(R.mipmap.bug_16)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    public static void success(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle("FELICITATION")
                //.setIcon(R.mipmap.checked)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    public static void confirm(Context context, String message, final OnConfirmListener onConfirmListener) {
        new AlertDialog.Builder(context)
                .setTitle("ETES-VOUS SUR")
                //.setIcon(R.mipmap.question)
                .setMessage(message)
                .setCancelable(true)
                .setNeutralButton("MODIFIER", null)
                .setPositiveButton("CONFIRMER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onConfirmListener != null) onConfirmListener.onConfirm();
                    }
                })
                .create()
                .show();
    }

    public static void danger(Context context, String message, final OnConfirmListener onConfirmListener) {
        new AlertDialog.Builder(context)
                .setTitle("ACTION DANGEREUSE !")
                //.setIcon(R.mipmap.warning_16)
                .setMessage(message)
                .setCancelable(true)
                .setNeutralButton("ANNULER", null)
                .setPositiveButton("CONFIRMER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onConfirmListener != null) onConfirmListener.onConfirm();
                    }
                })
                .create()
                .show();
    }

    public interface OnConfirmListener {
        public void onConfirm();
    }

}
