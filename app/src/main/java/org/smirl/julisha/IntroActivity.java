package org.smirl.julisha;

import android.content.Intent;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import org.smirl.julisha.core.Constants;
import org.smirl.julisha.core.FileManager;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.core.volley.MyStringRequest;
import org.smirl.julisha.core.volley.StaticRequestQueue;

import java.io.IOException;

public class IntroActivity extends AppCompatActivity  implements Constants {

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

    MyStringRequest request = new MyStringRequest(Request.Method.GET, BASE_URL, null,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            response = response.replace("null", "1");
            //  Utilities.snackIt(viewPager, response);
           //   System.out.println(response);
            Julisha.load(response);
            Julisha.prepareCaseGraphs();
           toNextActivity();
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            //   Utilities.snackIt(viewPager, error.getMessage());
            Julisha.generateSampleCases();
            Julisha.prepareCaseGraphs();
           toNextActivity();
          }
        });

    StaticRequestQueue.from(this).append(request);
  }

  private void toNextActivity(){
    startActivity(new Intent(IntroActivity.this, MainActivity.class));
    finish();
  }
}
