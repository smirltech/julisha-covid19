package org.smirl.julisha.ui.main.views;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.smirl.julisha.R;

import java.util.Objects;

import fnn.smirl.appinfo.AppInfo;

public class AboutActivity extends AppCompatActivity {

  //@RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    Toolbar toolbar = (findViewById(R.id.toolbarCovid));
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
   getSupportActionBar().setDisplayShowHomeEnabled(true);
    setTitle(getString(R.string.action_apropos));
    toolbar.getBackground().setAlpha(0);
    //toolbar.setTitleTextColor(getColor(R.color.black));

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
}
