package org.smirl.julisha.ui.main.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.smirl.julisha.R;
import org.smirl.julisha.ui.main.models.DiagnosticData;

import java.util.Objects;



public class DiagnosticActivity extends AppCompatActivity {
    TextView tv, textViewCountDown,nbquestion;
    Button btnconfimer, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1, rb2, rb3, rb4;
   // Context contex = this;
    //String asText;
    public static int temps=2000;
   ProgressDialog progressDialog;
   String[] questions= DiagnosticData.questions;
   String[] reponses =DiagnosticData.reponses;
   String[] choix =DiagnosticData.choix;
    int flag = 0;
    public static int Etat = 0, Positif = 0, Nagatif = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);
        Toolbar toolbar = (findViewById(R.id.toolbarCovid));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.app_name));


        TextView textView = (TextView) findViewById(R.id.DispName);
        textView.setText("Question:");


        //textView.setText("Question:");

        btnconfimer = (Button) findViewById(R.id.button3);
        tv = (TextView) findViewById(R.id.tvque);
        nbquestion=findViewById(R.id.nbquestion);

        radio_g = (RadioGroup) findViewById(R.id.answersgrp);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);

        tv.setText(questions[flag]);
        rb1.setText(choix[0]);
        rb2.setText(choix[1]);

        btnconfimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (radio_g.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), R.string.repondez_svp, Toast.LENGTH_SHORT).show();

                    return;
                }

                RadioButton Qreponse = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String repText = Qreponse.getText().toString();

                if (repText.equals(reponses[flag])) {
                    Positif++;


                } else {
                    Nagatif++;
                    //Toast.makeText(getApplicationContext(), "Question Suivante", Toast.LENGTH_SHORT).show();


                }

                flag++;

                if (nbquestion!=null)
                    nbquestion.setText( flag + "/9");


                if (flag < questions.length) {
                    tv.setText(questions[flag]);
                    rb1.setText(choix[flag * 2]);
                    rb2.setText(choix[flag * 2 + 1]);

                } else {
                    Etat = Positif;
                    final Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent(getApplicationContext(), CovidResultatActivity.class);
                            startActivity(in);
                            finish();

                        }
                    },temps);
                    progressDialog = new ProgressDialog(DiagnosticActivity.this);
                    progressDialog.setMessage (getString(R.string.wait));
                    progressDialog.setCancelable (false);
                    progressDialog.show();




                }
                radio_g.clearCheck();


            }


        });
    }



        @Override
        public void onBackPressed () {
            View parentLayout = findViewById(android.R.id.content);

            Snackbar snackbar = Snackbar.make(parentLayout, R.string.abandonner_le_test, Snackbar.LENGTH_LONG)
                    .setAction("OUI", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           // Intent in = new Intent(getApplicationContext(), CovidResultatActivity.class);
                            //startActivity(in);
                            finish();

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.white));
            View sview = snackbar.getView();

            sview.setBackgroundColor(Color.RED);
            snackbar.show();


        }

    @Override
    public boolean onSupportNavigateUp() {
        View parentLayout = findViewById(android.R.id.content);

        Snackbar snackbar = Snackbar.make(parentLayout, R.string.abandonner_le_test, Snackbar.LENGTH_LONG)
                .setAction("OUI", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Intent in = new Intent(getApplicationContext(), CovidResultatActivity.class);
                        //startActivity(in);
                        finish();

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.white));
        View sview = snackbar.getView();

        sview.setBackgroundColor(Color.RED);
        snackbar.show();
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        tv.setText(questions[flag]);





    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}








