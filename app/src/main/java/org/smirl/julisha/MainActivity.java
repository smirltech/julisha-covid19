package org.smirl.julisha;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import fnn.smirl.simple.Serializer;
import org.smirl.julisha.core.*;
import org.smirl.julisha.core.volley.MyStringRequest;
import org.smirl.julisha.core.volley.StaticRequestQueue;
import org.smirl.julisha.ui.main.controllers.SectionsPagerAdapter;
import org.smirl.julisha.ui.main.controllers.StatisticsFragment;
import org.smirl.julisha.ui.main.controllers.StatisticsVillesFragment;
import org.smirl.julisha.ui.main.models.Case;
import org.smirl.julisha.ui.main.models.Province;
import org.smirl.julisha.ui.main.models.Ville;
import org.smirl.julisha.ui.main.views.AboutActivity;
import org.smirl.julisha.ui.main.views.NewAlertActivity;

import java.io.IOException;
import java.security.Permission;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Constants, NavigationView.OnNavigationItemSelectedListener {

  private static final int REQUEST_INTERNET = 1;
  private static final int REQUEST_WRITE = 2;
  private static final int REQUEST_ACCESS_NETWORK_STATE = 3;

  ViewPager viewPager;
  SectionsPagerAdapter sectionsPagerAdapter;
  TabLayout tabs;
  private PermissionManager permissionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    //getSupportActionBar().setTitle("");
    permissionManager = new PermissionManager(this);
    verifier();


    sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
    viewPager = findViewById(R.id.view_pager);
    viewPager.setAdapter(sectionsPagerAdapter);
    tabs = findViewById(R.id.tabs);
    tabs.setupWithViewPager(viewPager);
    tabs.setSelectedTabIndicatorHeight(10);
    tabs.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        refreshThem();
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });


      findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getBaseContext(), NewAlertActivity.class));
          }
      });

  }

  @Override
  public void onBackPressed() {
    if (viewPager.getCurrentItem() > 0) {
      viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    } else super.onBackPressed();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_refresh:

        break;
      case R.id.action_apropos:
        startActivity(new Intent(this, AboutActivity.class));
        break;

      case R.id.action_contact:
        //
        Intent intent = new Intent (Intent.ACTION_SENDTO, Uri.fromParts (
                "mailto", getString(R.string.smirltech_mail), null));
        String subject = null;
        intent.putExtra (Intent.EXTRA_SUBJECT, subject);
        String message = null;
        intent.putExtra (Intent.EXTRA_TEXT, message);
        startActivity (Intent.createChooser (intent, ""));
        break;

      case R.id.action_share:

        Intent sendIntent = new Intent ();
        sendIntent.setAction (Intent.ACTION_SEND);
        sendIntent.putExtra (Intent.EXTRA_TEXT, getString (R.string.message_send));
        sendIntent.setType ("text/plain");
        startActivity (sendIntent);
        break;
      case R.id.action_noter:
        try {
          startActivity (new Intent (Intent.ACTION_VIEW,
                  Uri.parse ("market://details?id=" + getPackageName ())));
        } catch (ActivityNotFoundException e) {
          startActivity (new Intent (Intent.ACTION_VIEW,
                  Uri.parse ("http://play.google.com/store/apps/details?id=" + getPackageName ())));
        }
        break;

      case R.id.action_exit:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {

    switch (item.getItemId()) {
      case R.id.nav_gb:
        // fragger.switchFragment(2);
        break;
      case R.id.nav_diag:
        // fragger.switchFragment(3);
        startActivity(new Intent(this,CovidTestActivity.class));

        break;

      case R.id.nav_a_propos:
        startActivity(new Intent(this, AboutActivity.class));


        break;



      case R.id.nav_moreapp:
        Intent browserIntent = new Intent (Intent.ACTION_VIEW, Uri.parse ("https://smirl.org"));
        startActivity (browserIntent);

        break;
      case R.id.nav_share:

        Intent sendIntent = new Intent ();
        sendIntent.setAction (Intent.ACTION_SEND);
        sendIntent.putExtra (Intent.EXTRA_TEXT, getString (R.string.message_send));
        sendIntent.setType ("text/plain");
        startActivity (sendIntent);
        break;
      case R.id.nav_noter:
        try {
          startActivity (new Intent (Intent.ACTION_VIEW,
                  Uri.parse ("market://details?id=" + getPackageName ())));
        } catch (ActivityNotFoundException e) {
          startActivity (new Intent (Intent.ACTION_VIEW,
                  Uri.parse ("http://play.google.com/store/apps/details?id=" + getPackageName ())));
        }
        break;
      case R.id.nav_exit:
        finish();
        break;

      default:

    }


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  public void refreshThem() {
    StatisticsFragment sfrag = (StatisticsFragment) sectionsPagerAdapter.getItem(1);
    sfrag.populateTable();
    StatisticsVillesFragment sfrag2 = (StatisticsVillesFragment) sectionsPagerAdapter.getItem(2);
    sfrag2.populateTable();
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
        permissionManager.dialogForSettings("Permission Denied", "Now you must allow from settings.");
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
        permissionManager.dialogForSettings("Permission Denied", "Now you must allow from settings.");
      }

      @Override
      public void onPermissionGranted() {
        // readTransaction();

      }
    });

    permissionManager.checkPermission(this, Manifest.permission.ACCESS_NETWORK_STATE, new PermissionManager.PermissionAskListener() {
      @Override
      public void onNeedPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_ACCESS_NETWORK_STATE);
      }

      @Override
      public void onPermissionPreviouslyDenied() {
        // showCameraRational();
      }

      @Override
      public void onPermissionPreviouslyDeniedWithNeverAskAgain() {
        permissionManager.dialogForSettings("Permission Denied", "Now you must allow from settings.");
      }

      @Override
      public void onPermissionGranted() {
        // readTransaction();

      }
    });

  }
}