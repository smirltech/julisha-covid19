package org.smirl.julisha.ui.main.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.smirl.julisha.MainActivity;
import org.smirl.julisha.R;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.ui.main.models.CaseGraphs;
import org.smirl.julisha.ui.main.models.TableData;
import org.smirl.julisha.ui.main.views.LoneGraph;
import org.smirl.julisha.ui.main.views.StatisticsViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class StatisticsVillesFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private MainActivity mActivity;
    private NestedScrollView svfNestedSV;

    private StatisticsViewModel statisticsViewModel;

    TableLayout tableDisplay;

    public static StatisticsVillesFragment newInstance(MainActivity activity, int index) {
        StatisticsVillesFragment fragment = new StatisticsVillesFragment();
        fragment.mActivity = activity;
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
        View root = inflater.inflate(R.layout.statistics_villes_fragment_main, container, false);
        svfNestedSV = root.findViewById(R.id.svf_nested_sv);
        tableDisplay = root.findViewById(R.id.tableDisplay);
        tableDisplay.removeViews(1, tableDisplay.getChildCount() - 1);
        populateTable();

        svfNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    mActivity.toggleFab(false);
                } else {
                    mActivity.toggleFab(true);
                }
            }
        });
        return root;
    }

    public void populateTable() {

        int _i = 1;
        for (final TableData c : Julisha.getVillesTableData(0)) {
            try {
                TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.item_row_model, null, false);
                TextView tv0 = row.findViewById(R.id.tr_prov);

                tv0.setText(c.name.toUpperCase());
                ((TextView) row.findViewById(R.id.tr_no)).setText((_i++) + "");
                ((TextView) row.findViewById(R.id.tr_inf)).setText(c.infected + "");
                ((TextView) row.findViewById(R.id.tr_dec)).setText(c.dead + "");
                ((TextView) row.findViewById(R.id.tr_guer)).setText(c.healed + "");

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      CaseGraphs vcg =  Julisha.getVilleCaseGraphs(c.id);
                        new LoneGraph(getLayoutInflater(), c.name.toUpperCase(), vcg);
                    }
                });
                tableDisplay.addView(row);

                tableDisplay.invalidate();
                tableDisplay.refreshDrawableState();
            } catch (Exception eerr) {
            }
        }
    }
}