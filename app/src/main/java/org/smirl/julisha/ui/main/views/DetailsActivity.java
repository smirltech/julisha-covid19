package org.smirl.julisha.ui.main.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.text.TextUtilsCompat;
import org.smirl.julisha.R;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.ui.main.models.Cases;
import org.smirl.julisha.ui.main.models.Villes;

import java.util.HashSet;

public class DetailsActivity extends AppCompatActivity {
int provinceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        final TableLayout tableDisplay = findViewById(R.id.tableDisplay);

        provinceid = getIntent().getIntExtra("province_id", 0);

        if(provinceid == 0)finish();

        String pname = Julisha.getProvince(provinceid).nom.toUpperCase();
        getSupportActionBar().setTitle("Province : " + pname);

        Cases cs = Julisha.cases(provinceid);
        Villes vl = Julisha.villes();

        HashSet<Integer> fs = cs.getVilleIds();
        for(final int c : fs.toArray(new Integer[]{})){
            TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.item_row_model, null, false);
            TextView tv0 = row.findViewById(R.id.tr_prov);
            tv0.setText(vl.getVille(c).nom.toUpperCase());
            ((TextView)row.findViewById(R.id.tr_inf)).setText(cs.number(c, 1) + "");
            ((TextView)row.findViewById(R.id.tr_dec)).setText(cs.number(c,2) + "");
            ((TextView)row.findViewById(R.id.tr_guer)).setText(cs.number(c,3) + "");
            tableDisplay.addView(row);
        }
    }
}
