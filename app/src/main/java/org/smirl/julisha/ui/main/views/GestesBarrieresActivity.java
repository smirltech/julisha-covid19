package org.smirl.julisha.ui.main.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.smirl.julisha.R;

import java.util.Objects;

public class GestesBarrieresActivity extends AppCompatActivity {
    WebView webview;
   // String url=getString(R.string.webload);
    ProgressDialog progressDialog;
    private FirebaseAnalytics mFirebaseAnalytics;
    String themeKey = "currentTheme";
    String thememode = "currentSTyle";
    SharedPreferences sharedPreferences;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(
                "ThemePref",
                Context.MODE_PRIVATE
        );
        applyStyle();

        setContentView(R.layout.activity_gestes_barrieres);
        Toolbar toolbar=(findViewById(R.id.toolbarCovid));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.app_name));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance (this);


        webview = (WebView) findViewById (R.id.webView);





        webview.getSettings ().setJavaScriptEnabled (true);
        webview.getSettings ().setJavaScriptCanOpenWindowsAutomatically (true);
        webview.requestFocus ();
        webview.loadUrl (getString(R.string.webload));


        progressDialog = new ProgressDialog(GestesBarrieresActivity.this);
        progressDialog.setMessage ("Chargement");
        progressDialog.setCancelable (false);
        progressDialog.show ();


        webview.setWebViewClient (new WebViewClient() {


            public void onPageFinished(WebView view, String url) {
               toolbar.setTitle (view.getTitle ());

                try {
                    progressDialog.dismiss ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        });
    }





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void applyStyle () {

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



            }

        }
    }
}