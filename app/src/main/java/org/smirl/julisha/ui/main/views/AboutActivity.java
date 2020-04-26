package org.smirl.julisha.ui.main.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.smirl.julisha.R;

import fnn.smirl.appinfo.AppInfo;

public class AboutActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about);
    TextView labelVersion = findViewById(R.id.label_version);
    try {
      labelVersion.setText("v" + AppInfo.from(this).getAppVersionName() + "(" + AppInfo.from(this).getAppVersionCode() + ")");
    }catch(Exception ex){}
  }
}
