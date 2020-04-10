package org.smirl.julisha.ui.main.views;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fnn.smirl.appinfo.AppInfo;
import org.smirl.julisha.R;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about);
    TextView labelVersion = findViewById(R.id.label_version);
   labelVersion.setText("v" + AppInfo.from(this).getAppVersionName() +"(" + AppInfo.from(this).getAppVersionCode() +")");
  }
}
