package org.smirl.julisha.ui.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.smirl.julisha.R;

import java.util.Objects;

public class CovidTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAnalytics mFirebaseAnalytics;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_test);
        Toolbar toolbar=(findViewById(R.id.toolbarCovid));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Julisha");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance (this);




        Button button = findViewById(R.id.BoutonSart);
        button.setOnClickListener(this);
    }

    public void onClick(View view){

        startActivity(new Intent(this,QuestionsActivity.class));
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}