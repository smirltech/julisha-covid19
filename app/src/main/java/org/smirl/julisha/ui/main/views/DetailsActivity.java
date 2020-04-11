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
import org.smirl.julisha.ui.main.models.TableData;
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
        getSupportActionBar().setTitle(pname);

        int _i = 1;
        for(final TableData c : Julisha.getVillesTableData(provinceid)){
            TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.item_row_model, null, false);
            TextView tv0 = row.findViewById(R.id.tr_prov);
            tv0.setText(c.name.toUpperCase());
            ((TextView) row.findViewById(R.id.tr_no)).setText((_i++) + "");
            ((TextView)row.findViewById(R.id.tr_inf)).setText(c.infected + "");
            ((TextView)row.findViewById(R.id.tr_dec)).setText(c.dead + "");
            ((TextView)row.findViewById(R.id.tr_guer)).setText(c.healed + "");
            tableDisplay.addView(row);
        }
    }
}
