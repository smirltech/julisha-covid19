package org.smirl.julisha.ui.main.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.smirl.julisha.R;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.ui.main.models.Case;

import java.util.Objects;

public class LogActivity extends AppCompatActivity {

    private TextView m_infected;
    private TextView m_dead;
    private TextView m_healed;

    TableLayout tableLayout;
    String themeKey = "currentTheme";
    String thememode = "currentSTyle";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(
                "ThemePref",
                Context.MODE_PRIVATE
        );
        applyStyle();
        setContentView(R.layout.activity_log);
        Toolbar toolbar = (findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        }
       getSupportActionBar().setTitle(R.string.app_log_name);
        tableLayout = findViewById(R.id.table_layout);

       loadData();
    }


    private void loadData() {
        try {
            tableLayout.removeAllViews();
        } catch (Exception ee) {
        }
        for (final Case cas : Julisha.cases().copy()) {
            TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.row_model, null);
            TextView case_date = row.findViewById(R.id.cas_date);
            TextView case_type = row.findViewById(R.id.case_type);
            TextView case_ville = row.findViewById(R.id.case_ville);
            TextView case_province = row.findViewById(R.id.case_province);
            TextView case_nombre = row.findViewById(R.id.case_nombre);

            String _type = "Infectés";
            switch (cas.type) {
                case 2:
                    _type = "Décédés";
                    break;
                case 3:
                    _type = "Guéris";
                    break;
                default:
                    _type = "Infectés";
            }

            case_date.setText(cas.date);
            case_type.setText(_type);
            case_ville.setText(Julisha.getVille(cas.ville_id).nom.toUpperCase());
            case_province.setText(Julisha.getProvince(cas.province_id).nom.toUpperCase());
            case_nombre.setText(cas.nombre + "");
            tableLayout.addView(row);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void applyStyle () {

        switch (sharedPreferences.getInt(themeKey, 0)) {
            case 0: {
                getTheme().applyStyle(R.style.AppTheme_NoActionBar, true);
                break;

            }
            case 1: {
                getTheme().applyStyle(R.style.ThemeMoveNoActionBar, true);
                break;
            }
            case 2: {
                getTheme().applyStyle(R.style.ThemeLimeNoActionBar, true);


                break;

            }
            case 3: {
                getTheme().applyStyle(R.style.ThemeDarkNoActionBar, true);
                break;

            }

        }
    }
}