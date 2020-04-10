package org.smirl.julisha.ui.main.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.smirl.julisha.R;

import java.util.Objects;

public class AppelerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeler);
        Toolbar toolbar=(findViewById(R.id.toolbarCovid));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.urgence));
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void numero_1(View view) {
        String numero = "101";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
        startActivity(intent);
    }

    public void numero_2(View view) {
        String numero = "109";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
        startActivity(intent);
    }

    public void numero_3(View view) {
        String numero = "110";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null));
        startActivity(intent);
    }
}