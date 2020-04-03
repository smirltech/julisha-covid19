package org.smirl.julisha;

import android.Manifest;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import fnn.smirl.simple.Serializer;
import org.smirl.julisha.core.*;
import org.smirl.julisha.core.volley.MyStringRequest;
import org.smirl.julisha.core.volley.StaticRequestQueue;
import org.smirl.julisha.ui.main.controllers.SectionsPagerAdapter;
import org.smirl.julisha.ui.main.models.Case;
import org.smirl.julisha.ui.main.models.Province;
import org.smirl.julisha.ui.main.models.Ville;

import java.io.IOException;
import java.security.Permission;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Constants {

  private static final int REQUEST_INTERNET = 1;
  private static final int REQUEST_WRITE = 2;

  ViewPager viewPager;
  SectionsPagerAdapter sectionsPagerAdapter;
  private PermissionManager permissionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    permissionManager = new PermissionManager(this);
    verifier();

    sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
    viewPager = findViewById(R.id.view_pager);
    viewPager.setAdapter(sectionsPagerAdapter);
    TabLayout tabs = findViewById(R.id.tabs);
    tabs.setupWithViewPager(viewPager);


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()){
      case R.id.action_refresh:

        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void verifier() {
    permissionManager.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionManager.PermissionAskListener() {
      @Override
      public void onNeedPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
      }

      @Override
      public void onPermissionPreviouslyDenied() {
        // showCameraRational();
      }

      @Override
      public void onPermissionPreviouslyDeniedWithNeverAskAgain() {
        permissionManager.dialogForSettings("Permission Denied", "Now you must allow camera read sms from settings.");
      }

      @Override
      public void onPermissionGranted() {
        // readTransaction();

      }
    });

    permissionManager.checkPermission(this, Manifest.permission.INTERNET, new PermissionManager.PermissionAskListener() {
      @Override
      public void onNeedPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);
      }

      @Override
      public void onPermissionPreviouslyDenied() {
        // showCameraRational();
      }

      @Override
      public void onPermissionPreviouslyDeniedWithNeverAskAgain() {
        permissionManager.dialogForSettings("Permission Denied", "Now you must allow camera read sms from settings.");
      }

      @Override
      public void onPermissionGranted() {
        // readTransaction();

      }
    });

  }
}