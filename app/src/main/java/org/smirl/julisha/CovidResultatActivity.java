package org.smirl.julisha;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class CovidResultatActivity extends AppCompatActivity {
    TextView alertmsg,messageap;
    Button btnRestart, btnexit,urgence_btn;
    ImageView imagealert;
    MediaPlayer alert;
    Context contex = this;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_resultat);
        Toolbar toolbar=(findViewById(R.id.toolbarCovid));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Résultat de Diagnostic ");


        alertmsg = (TextView) findViewById(R.id.alertmsg);
        messageap = (TextView) findViewById(R.id.messageap);

        btnRestart = (Button) findViewById(R.id.btnRestart);
        btnexit = (Button) findViewById(R.id.btnexit);
        urgence_btn=findViewById(R.id.urgence_btn);
        imagealert=(ImageView)findViewById(R.id.imagealert);

        /*

        Etat Negatif
         */


        if (QuestionsActivity.Positif == 0){
            alertmsg.setText("Vous ne présentez aucun symptômes de Covid19");
            alertmsg.setTextColor(getColor(R.color.black));
            alertmsg.setVisibility (View.VISIBLE);
            messageap.setText(" N’hésitez pas à contacter un médecin en cas de doute. \n \n Mesurez votre température deux fois par jour.\n" +
                    "\n" +
                    "Refaites ce test en cas de nouveau symptôme pour réévaluer la situation.\n" +
                    "\n" +
                    "\n" +
                    "#Restez chez vous, limitez les contacts avec d'autres personnes. Le virus peut être propagé par des porteurs ne montrant pas de symptômes. ");            messageap.setVisibility(View.VISIBLE);
            imagealert.setBackground(getDrawable(R.drawable.negatif));
            imagealert.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Vous ne présentez aucun symptômes", Toast.LENGTH_SHORT).show();
            alert = MediaPlayer.create (contex, R.raw.alert);
            alert.start ();

/*

        Etat pas sur
         */


        }
     else    if(QuestionsActivity.Positif <=4){
            alertmsg.setText("Surveillez attentivement votre état de santé !");
            alertmsg.setTextColor(getColor(R.color.black));

            alertmsg.setVisibility (View.VISIBLE);
            messageap.setText(" N’hésitez pas à contacter un médecin en cas de doute. \n \n Mesurez votre température deux fois par jour.\n" +
                    "\n" +
                    "Refaites ce test en cas de nouveau symptôme pour réévaluer la situation.\n" +
                    "\n" +
                    "Restez chez vous.\n" +
                    "\n" +
                    "#Restez chez vous, limitez les contacts avec d'autres personnes. Le virus peut être propagé par des porteurs ne montrant pas de symptômes. ");
            messageap.setVisibility(View.VISIBLE);
            imagealert.setBackground(getDrawable(R.drawable.ic_baseline_warning_blue));
            imagealert.setVisibility(View.VISIBLE);




        }

     /*

        Etat Avec de doute
         */

        if (QuestionsActivity.Positif >= 5) {
            Toast.makeText(getApplicationContext(), "Veillez consulter un medecin SVP!!!", Toast.LENGTH_SHORT).show();
            alertmsg.setText("Votre situation peut relever d’un COVID 19, vos symptômes nécessitent une prise en charge médicale.");
            alertmsg.setVisibility(View.VISIBLE);
            alertmsg.setTextColor(getColor(R.color.red));
            messageap.setText("#Limitez les contacts avec d'autres personnes.\n \n" +
                    "Ce message ne qualifie en aucun cas un diagnostic individuel ou une prescription médicale et ne remplace en aucun cas une prise en charge médicale par un professionnel de santé compétent. Ce message repose sur les recommandations du ministère de la santé."+ "\n \nVeillez consulter un medecin SVP!!!");
            messageap.setVisibility(View.VISIBLE);
            imagealert.setBackground(getDrawable(R.drawable.ic_baseline_warning_red));
            imagealert.setVisibility(View.VISIBLE);



        }

        /*

        Etat Positif
         */
        if (QuestionsActivity.Positif >=7 ){
                Toast.makeText(getApplicationContext(), "Veillez contacter un medecin immédiatement SVP!!!", Toast.LENGTH_SHORT).show();
                alertmsg.setText("Vous décrivez des symptômes compatibles avec un syndrome de détresse respiratoire aiguë devant faire l’objet d'une prise en charge médicale en urgence.");
                alertmsg.setVisibility(View.VISIBLE);
                alertmsg.setTextColor(getColor(R.color.red));
                messageap.setText("Ce message ne qualifie en aucun cas un diagnostic individuel ou une prescription médicale et ne remplace en aucun cas une prise en charge médicale par un professionnel de santé compétent. Ce message repose sur les recommandations du ministère de la santé.");
                messageap.setVisibility(View.VISIBLE);
                imagealert.setBackground(getDrawable(R.drawable.ic_baseline_add_alert_red));
                imagealert.setVisibility(View.VISIBLE);
            urgence_btn.setVisibility(View.VISIBLE);
            alert = MediaPlayer.create (contex, R.raw.badalert);
            alert.start ();


        }

        QuestionsActivity.Positif=0;
        QuestionsActivity.Nagatif = 0;



        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(), QuestionsActivity.class);
                startActivity(in);
                finish();

            }
        });


        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                finish();
            }


        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void urgence_btn(View view){
        startActivity(new Intent(this,AppelerActivity.class));
        finish();
    }

}