package org.smirl.julisha.core;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionManager {

  private Context context;
  private SessionManager sessionManager;

  public PermissionManager(Context context) {
    this.context = context;
    sessionManager = new SessionManager(context);
  }

  public boolean shouldAskPermission() {
    return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
  }

  private boolean shouldAskPermission(Context context, String permission) {
    if (shouldAskPermission()) {
      int permissionResult = ActivityCompat.checkSelfPermission(context, permission);
      if (permissionResult != PackageManager.PERMISSION_GRANTED) {
        return true;
      }
    }
    return false;
  }

  public void checkPermission(Context context, String permission, PermissionAskListener listener) {
    if (shouldAskPermission(context, permission)) {
      if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context, permission)) {
        listener.onPermissionPreviouslyDenied();
      } else {
        if (sessionManager.isFirstTimeAsking(permission)) {
          sessionManager.firstTimeAsking(permission, false);
          listener.onNeedPermission();
        } else {
          listener.onPermissionPreviouslyDeniedWithNeverAskAgain();
        }
      }
    } else {
      listener.onPermissionGranted();
    }
  }

  private void showPermissionRational(String title,String msg, String permission) {
    new AlertDialog.Builder(context).setTitle("Permission Denied").setMessage("Without this permission this app is unable to open camera to take your photo. Are you sure you want to deny this permission.")
        .setCancelable(false)
        .setNegativeButton("I'M SURE", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
            dialog.dismiss();
          }
        }).show();

  }

  public void dialogForSettings(String title, String msg) {
    new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
        .setCancelable(false)
        .setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            goToSettings();
            dialog.dismiss();
          }
        }).show();
  }


  private void goToSettings() {
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.parse("package:" + context.getPackageName());
    intent.setData(uri);
    context.startActivity(intent);
  }

  public interface PermissionAskListener {
    void onNeedPermission();

    void onPermissionPreviouslyDenied();

    void onPermissionPreviouslyDeniedWithNeverAskAgain();

    void onPermissionGranted();
  }
}


