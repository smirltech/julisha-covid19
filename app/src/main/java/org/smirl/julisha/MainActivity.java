package org.smirl.julisha;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.smirl.julisha.core.Constants;
import org.smirl.julisha.core.DialogFactory;
import org.smirl.julisha.core.PermissionManager;
import org.smirl.julisha.core.PrefManager;
import org.smirl.julisha.core.UpdatesChecker;
import org.smirl.julisha.core.Utilities;
import org.smirl.julisha.core.data.location.LocationPicker;
import org.smirl.julisha.core.data.location.OnDataSelection;
import org.smirl.julisha.ui.main.controllers.SectionsPagerAdapter;
import org.smirl.julisha.ui.main.controllers.StatisticsFragment;
import org.smirl.julisha.ui.main.controllers.StatisticsVillesFragment;
import org.smirl.julisha.ui.main.models.MyPreferences;
import org.smirl.julisha.ui.main.views.AboutActivity;
import org.smirl.julisha.ui.main.views.LogActivity;
import org.smirl.julisha.ui.main.views.SettingsActivity;
import org.smirl.julisha.ui.main.views.UrgencesActivity;
import org.smirl.julisha.ui.main.views.CovidTestActivity;
import org.smirl.julisha.ui.main.views.GestesBarrieresActivity;
import org.smirl.julisha.ui.main.views.NewAlertActivity;

import fnn.smirl.appinfo.AppInfo;


public class MainActivity extends AppCompatActivity implements Constants, NavigationView.OnNavigationItemSelectedListener {

    public static Context THIS;
    public static PrefManager PREFMANAGER;
    private static final int REQUEST_INTERNET = 1;
    private static final int REQUEST_WRITE = 2;
    private static final int REQUEST_ACCESS_NETWORK_STATE = 3;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FloatingActionButton mainFab;
    int appTheme=0;
    private static final int REQUEST_CODE=0;
    SharedPreferences sharedPreferences;
    String themeKey = "currentTheme";
    String thememode = "currentSTyle";


    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    TabLayout tabs;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // appTheme= MyPreferences(this).darkMode;

        sharedPreferences = getSharedPreferences(
                "ThemePref",
                Context.MODE_PRIVATE
        );
        checkStyle();
        //checkTheme();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        THIS = this;
        PREFMANAGER = new PrefManager(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        check4update();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.versionapp).setTitle("Version: " + AppInfo.from(this).getAppVersionName());

        permissionManager = new PermissionManager(this);
        verifier();

        handleViewPager();


        mainFab = findViewById(R.id.fab);
        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), NewAlertActivity.class));
            }
        });
    }

    public void handleViewPager() {
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
                toggleFab(true);
                refreshThem();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void toggleFab(boolean b) {
        if (b) mainFab.show();
        else mainFab.hide();
    }

    private void check4update() {
        new UpdatesChecker(this, URL_UPDATE, new UpdatesChecker.OnUpdateListener() {
        @Override
        public void onUpdate(boolean found, UpdatesChecker.Update update) {
            if (found) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("MISE A JOUR DISPONIBLE v" + update.versionName)
                        .setMessage(update.releaseNote)
                        //.setMessage(update.path)
                        .setCancelable(false)
                        .setPositiveButton("TELECHARGER", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent();
                                i.setAction(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(update.path));
                                startActivity(i);
                            }
                        }).setNeutralButton("PLUS TARD", null)
                        .create().show();
            }
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
            handleViewPager();
            break;
            case R.id.action_ville:
            LocationPicker.pickLocation(this, LocationPicker.VALUE_VILLE, new OnDataSelection() {
                @Override
                public void onDataSet(int dataId, String dataName) {
                    PREFMANAGER.putInt("maville", dataId);
                    Utilities.dialog(THIS, "DESORMAIS", "Votre nouvelle ville de suivi est maintenant : " + dataName.toUpperCase(), "OK", new Utilities.UtilityListener() {

                        @Override
                        public void onAccept() {
                            //  recreate();
                            Toast.makeText(getApplicationContext(), dataName + " est maintenant votre nouvelle ville de suivi", Toast.LENGTH_SHORT).show();

                            handleViewPager();
                        }
                    });

                }
            });
            break;
            case R.id.action_apropos:
            startActivity(new Intent(this, AboutActivity.class));
            break;
            case R.id.action_contact:
            //
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", getString(R.string.smirltech_mail), null));
            String subject = null;
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            String message = null;
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(intent, ""));
            break;
            case R.id.action_support:
            SupportsUS();
            break;
            case R.id.action_settings:
            Intent intentset= new Intent(this,SettingsActivity.class);
            startActivityForResult(intentset,REQUEST_CODE);
            break;

            case R.id.action_share:
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_send));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            break;
            case R.id.action_update:

            check4updatemenu();


            //Toast.makeText(this, "en cours d'implémentation", Toast.LENGTH_SHORT).show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_gb:
            // fragger.switchFragment(2);
            startActivity(new Intent(this, GestesBarrieresActivity.class));
            break;
            case R.id.nav_diag:
            // fragger.switchFragment(3);
            startActivity(new Intent(this, CovidTestActivity.class));

            break;
            case R.id.nav_alert:
            // fragger.switchFragment(3);
            startActivity(new Intent(this, NewAlertActivity.class));

            break;
            case R.id.nav_contacterurgence:
            startActivity(new Intent(this, UrgencesActivity.class));
            break;

            case R.id.nav_a_propos:
            startActivity(new Intent(this, AboutActivity.class));
            break;
            case R.id.nav_settings:
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
            break;
            case R.id.nav_log:
            startActivity(new Intent(this, LogActivity.class));
            break;
            case R.id.nav_support:
            SupportsUS();
            break;


            case R.id.nav_share:

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_send));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
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

    /*
    Update from menu
         */
    private void check4updatemenu() {
        new UpdatesChecker(this, URL_UPDATE, new UpdatesChecker.OnUpdateListener() {
        @Override
        public void onUpdate(boolean found, UpdatesChecker.Update update) {
            if (found) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("MISE A JOUR DISPONIBLE v" + update.versionName)
                        .setMessage(update.releaseNote)
                        //.setMessage(update.path)
                        .setCancelable(false)
                        .setPositiveButton("TELECHARGER", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent();
                                i.setAction(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(update.path));
                                startActivity(i);
                            }
                        }).setNeutralButton("PLUS TARD", null)
                        .create().show();
            } else
                Toast.makeText(getApplicationContext(), "Aucune mise à jour disponible!", Toast.LENGTH_SHORT).show();


        }

    });
    }

    public void SupportsUS() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Nous soutenir !")
                .setMessage("Pourquoi nous soutenir?\n" +
                        "- Votre soutien nous encouragera à améliorer le projet Julisha d’avantage \n" +
                        "- A continuer les développements\n")
                .setCancelable(true)
                .setPositiveButton("Airtel Money", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String numero = "+243977779579";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
                        Toast.makeText(getApplicationContext(), "Merci d'avance ❤ !", Toast.LENGTH_SHORT).show();

                        startActivity(intent);

                    }
                }).setNeutralButton("M-Pesa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String numero = "+243810311929";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
                        Toast.makeText(getApplicationContext(), "Merci d'avance ❤ !", Toast.LENGTH_SHORT).show();

                        startActivity(intent);


                    }

                })
                .create().show();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                checkStyle();
                finish();

            }


        }
    }

    public void checkStyle () {

        switch (sharedPreferences.getInt(themeKey, 0)) {
            case 0: {
            getTheme().applyStyle(R.style.AppTheme_NoActionBar, true);
            break;

        }
            case 1: {
            getTheme().applyStyle(R.style.ThemeMoveNoActionBar, true);
            break;
        }
            case 2: {
            getTheme().applyStyle(R.style.ThemeLimeNoActionBar, true);
            break;
          }
        case 3: {
            getTheme().applyStyle(R.style.ThemeDarkNoActionBar, true);
            break;
        }

        }
    }
    public void checkTheme () {

        switch (sharedPreferences.getInt(thememode, 2)) {
            case 0: {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getDelegate().applyDayNight();
            break;

        }
            case 1: {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getDelegate().applyDayNight();
            break;
        }
            case 2: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                getDelegate().applyDayNight();


            }

        }
    }

}
