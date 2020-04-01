package org.smirl.julisha.ui.main.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.smirl.julisha.R;
import org.smirl.julisha.core.Fragmentation;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.ui.main.models.Cases;
import org.smirl.julisha.ui.main.models.Provinces;
import org.smirl.julisha.ui.main.views.DetailsActivity;
import org.smirl.julisha.ui.main.views.StatisticsViewModel;

import java.util.HashSet;

/**
 * A placeholder fragment containing a simple view.
 */
public class StatisticsFragment extends Fragment implements Fragmentation {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private StatisticsViewModel statisticsViewModel;

    TableLayout tableDisplay;

    public static StatisticsFragment newInstance(int index) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        statisticsViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_fragment_main, container, false);
        tableDisplay = root.findViewById(R.id.tableDisplay);
       refreshMe();

        return root;
    }

    @Override
    public void refreshMe() {
        tableDisplay.removeViews(1, tableDisplay.getChildCount()-1);

        Cases cs = Julisha.cases();
        Provinces vl = Julisha.provinces();

        HashSet<Integer> fs = cs.getProvinceIds();
        for(final int c : fs.toArray(new Integer[]{})){
            TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.item_row_model, null, false);
            TextView tv0 = row.findViewById(R.id.tr_prov);
            tv0.setText(vl.getProvince(c).nom.toUpperCase());
            tv0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(getActivity(), DetailsActivity.class);
                    ii.putExtra("province_id", c);
                    getContext().startActivity(ii);
                }
            });
            ((TextView)row.findViewById(R.id.tr_inf)).setText(cs.numberP(c, 1) + "");
            ((TextView)row.findViewById(R.id.tr_dec)).setText(cs.numberP(c,2) + "");
            ((TextView)row.findViewById(R.id.tr_guer)).setText(cs.numberP(c,3) + "");
            tableDisplay.addView(row);
        }
    }
}