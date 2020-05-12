package org.smirl.julisha;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.smirl.julisha.core.*;

import java.io.IOException;


public class IntroActivity extends AppCompatActivity implements Constants {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        populateProvinces();
        populateVilles();
        new InternetCheck(internet -> {
            if (internet) {
                populateCases();
            } else {
                Utilities.toastIt(this, "Pas de connexion!");
                DataUpdater.populateLocalCases(this, new DataUpdater.UpdaterListener() {
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
        });
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
