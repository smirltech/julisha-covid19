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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.smirl.julisha.R;

import java.util.Objects;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv, textViewCountDown;
    Button btnconfimer, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1, rb2, rb3, rb4;
   // Context contex = this;
    //String asText;
    public static int temps=3000;
   ProgressDialog progressDialog;

    String questions[] = {
            "Votre niveau de fièvre est-il superieur à 38 °C ?\n\n" +
                    "La température normale du corps oscille entre 36 °C et 37,2 °C selon les personnes, le cycle féminin (elle monte avec l'ovulation) et le moment de la journée (elle grimpe le soir). On parle de fièvre à partir de 38 °C.",
            "Est-ce que vous avez une toux récente ? \n \n " +
                    "Toux récente signifie une toux que vous n'aviez pas avant ou si vous toussez de manière chronique, que votre toux s'est empirée.",
            "Avez-vous des difficultés à respirer ?",
            "Avez-vous une fatigue inhabituelle ces derniers jours ?",
            "Avez-vous mal à la gorge ?",
            "Avez-vous une impossibilité de manger ou boire depuis 24 heures ou plus ?",
            "Avez-vous des courbatures en dehors des douleurs musculaires liées à une activité sportive intense ?",
            "Avez-vous perdu l’odorat de manière brutale sans rapport avec le nez bouché ?",
            "Avez-vous la diarrhée ? \n \n Avoir la diarrhée signifie émettre au moins 3 selles molles ou liquides par jour ou à une fréquence (c’est à dire un nombre de fois pour une période de temps) anormale pour la personne."

    };

    String reponses[] = {"Oui", "Oui",  "Oui", "Oui","Oui","Oui","Oui","Oui","Oui","int"};
    String choix[] = {
            "Oui", "Non",
            "Oui", "Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",

    };
    int flag = 0;
    public static int Etat = 0, Positif = 0, Nagatif = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (findViewById(R.id.toolbarCovid));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.app_name));

        TextView textView = (TextView) findViewById(R.id.DispName);
        textView.setText("Question:");


        //textView.setText("Question:");

        btnconfimer = (Button) findViewById(R.id.button3);
        //quitbutton=(Button)findViewById(R.id.buttonquit);
        tv = (TextView) findViewById(R.id.tvque);

        radio_g = (RadioGroup) findViewById(R.id.answersgrp);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);

        tv.setText(questions[flag]);
        rb1.setText(choix[0]);
        rb2.setText(choix[1]);

        btnconfimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int color = mBackgroundColor.getColor();
                //mLayout.setBackgroundColor(color);

                if (radio_g.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Veuillez répondre  SVP", Toast.LENGTH_SHORT).show();

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
                    progressDialog = new ProgressDialog(QuestionsActivity.this);
                    progressDialog.setMessage ("Traitement en cours...\nVeuillez patienter");
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

            Snackbar snackbar = Snackbar.make(parentLayout, "Abandonner le test en cours ?", Snackbar.LENGTH_LONG)
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

        Snackbar snackbar = Snackbar.make(parentLayout, "Abandonner le Test en cours ?", Snackbar.LENGTH_LONG)
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



    }








