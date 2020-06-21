package org.smirl.julisha.ui.main.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.smirl.julisha.MainActivity;
import org.smirl.julisha.R;

import java.util.Objects;

import fnn.smirl.appinfo.AppInfo;

public class AboutActivity extends AppCompatActivity {
    String themeKey = "currentTheme";
    String thememode = "currentSTyle";
    SharedPreferences sharedPreferences;


    //@RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      sharedPreferences = getSharedPreferences(
              "ThemePref",
              Context.MODE_PRIVATE
      );
      applyStyle();

    setContentView(R.layout.activity_about);
    Toolbar toolbar = (findViewById(R.id.toolbarCovid));
    setSupportActionBar(toolbar);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
          Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
      } else {
          Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
      }

   getSupportActionBar().setTitle(getString(R.string.action_apropos));
    toolbar.getBackground().setAlpha(0);
    //toolbar.setTitleTextColor(getColor(R.color.black));
      Button supportsUS=findViewById(R.id.support_us_btn);
      supportsUS.setOnClickListener(view ->{
          SupportsUS();

      });



    TextView labelVersion = findViewById(R.id.label_version);
    try {
      labelVersion.setText("v" + AppInfo.from(this).getAppVersionName() + "(" + AppInfo.from(this).getAppVersionCode() + ")");
    }catch(Exception ex){}
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }
    public void SupportsUS() {
        new AlertDialog.Builder(AboutActivity.this)
                .setTitle("Nous soutenir !")
                .setMessage("Pourquoi nous soutenir?\n" +
                        "- Votre soutien nous encouragera à améliorer le projet Julisha d’avantage \n" +
                        "- A continuer les développements\n")
                .setCancelable(false)
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
