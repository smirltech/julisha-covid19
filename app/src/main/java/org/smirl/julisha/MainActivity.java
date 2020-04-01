package org.smirl.julisha;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import fnn.smirl.simple.Serializer;
import org.smirl.julisha.core.*;
import org.smirl.julisha.core.volley.MyStringRequest;
import org.smirl.julisha.core.volley.StaticRequestQueue;
import org.smirl.julisha.ui.main.controllers.SectionsPagerAdapter;
import org.smirl.julisha.ui.main.models.Case;
import org.smirl.julisha.ui.main.models.Province;
import org.smirl.julisha.ui.main.models.Ville;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Constants {

    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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
                      //  Utilities.snackIt(viewPager, response);
                     //   System.out.println(response);
                        Julisha.load(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Utilities.snackIt(viewPager, error.getMessage());
                        Julisha.generateSampleCases();

                    }
                });

        StaticRequestQueue.from(this).append(request);
    }
}