package org.smirl.julisha.ui.main.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.smirl.julisha.R;

import java.util.Objects;

public class UrgencesActivity extends AppCompatActivity {

    String themeKey = "currentTheme";
    String thememode = "currentSTyle";
    SharedPreferences sharedPreferences;
    LinearLayout urgence_btn1;
    LinearLayout urgence_btn2;
    RelativeLayout diagnostic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences(
                "ThemePref",
                Context.MODE_PRIVATE
        );
        applyStyle();
        setContentView(R.layout.activity_urgences);
        /*

        Toolbar toolbar=(findViewById(R.id.toolbarCovid));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");

         */
        initView();



    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    /*

    public void numero_1(View view) {
        String numero = "101";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
        startActivity(intent);
    }

    public void numero_2(View view) {
        String numero = "109";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
        startActivity(intent);
    }

    public void numero_3(View view) {
        String numero = "110";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
        startActivity(intent);
    }


     */
    public void initView(){

     urgence_btn1=findViewById(R.id.urgence_btn);
     urgence_btn2=findViewById(R.id.urgence_btn2);
     diagnostic=findViewById(R.id.diag_button2);

        urgence_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = "109";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
                startActivity(intent);

            }
        });
        urgence_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = "110";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
                startActivity(intent);

            }
        });
        diagnostic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UrgencesActivity.this, CovidTestActivity.class));
                finish();
            }
        });


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
                break;



            }
            case 3: {
                getTheme().applyStyle(R.style.ThemeDarkNoActionBar, true);
                break;

            }

        }
    }
}