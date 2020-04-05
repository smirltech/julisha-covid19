package org.smirl.julisha;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import org.smirl.julisha.core.*;
import org.smirl.julisha.core.volley.MyStringRequest;
import org.smirl.julisha.core.volley.StaticRequestQueue;

import java.io.File;
import java.io.IOException;

public class IntroActivity extends AppCompatActivity implements Constants {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intro);

    populateProvinces();
    populateVilles();
    populateCases();

  }


  private void populateProvinces() {
    String dd = null;
    try {
      dd = FileManager.readFromAssets(this, "rdc/provinces.json");
      Julisha.loadProvinces(dd);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void populateVilles() {
    String dd = null;
    try {
      dd = FileManager.readFromAssets(this, "rdc/villes.json");
      Julisha.loadVilles(dd);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void populateCases() {

    DataUpdater.populateCases(this, new DataUpdater.UpdaterListener() {
      @Override
      public void onCompleted() {
        toNextActivity();
      }

      @Override
      public void onFailed() {
        toNextActivity();
      }
    });
  }

  private void toNextActivity() {
    startActivity(new Intent(IntroActivity.this, MainActivity.class));
    finish();
  }


}
