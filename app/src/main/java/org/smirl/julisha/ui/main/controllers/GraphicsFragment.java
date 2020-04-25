package org.smirl.julisha.ui.main.controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.smirl.julisha.MainActivity;
import org.smirl.julisha.R;
import org.smirl.julisha.core.DataUpdater;
import org.smirl.julisha.core.DateUtils;
import org.smirl.julisha.core.Fragmentation;
import org.smirl.julisha.Julisha;
import org.smirl.julisha.core.Utilities;
import org.smirl.julisha.ui.main.models.CaseGraph;
import org.smirl.julisha.ui.main.models.CaseGraphs;
import org.smirl.julisha.ui.main.models.CasesSummary;
import org.smirl.julisha.ui.main.views.GraphManager;
import org.smirl.julisha.ui.main.views.PageViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * A placeholder fragment containing a simple view.
 */
public class GraphicsFragment extends Fragment implements Fragmentation {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private MainActivity mActivity;
    private SwipeRefreshLayout gfSwipe;
    private NestedScrollView gfNestedSV;

    private PageViewModel pageViewModel;
    private GraphManager graphManager;

    TextView infectionLabel;
    TextView deadLabel;
    TextView healedLabel;
    TextView majDate;

    private LineChart chart, chart2;
    //private AnyChartView anyChartView;

    public static GraphicsFragment newInstance(MainActivity activity, int index) {
        GraphicsFragment fragment = new GraphicsFragment();
        fragment.mActivity = activity;
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.graphics_fragment_main, container, false);
        gfSwipe = root.findViewById(R.id.gf_swipe);
        gfNestedSV = root.findViewById(R.id.gf_nested_sv);

        final TextView textView = root.findViewById(R.id.section_label);
        infectionLabel = root.findViewById(R.id.infection_label);
        deadLabel = root.findViewById(R.id.dead_label);
        healedLabel = root.findViewById(R.id.healed_label);
        majDate = root.findViewById(R.id.maj_date);
        chart = root.findViewById(R.id.mchart);
        chart2 = root.findViewById(R.id.mchart2);
        //anyChartView = (AnyChartView)root.findViewById(R.id.any_chart_view);

        graphManager = new GraphManager(root);
        graphManager.generateGraph();

        refreshMe();

        gfNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    //Utilities.toastIt(getContext(), "bottom scroll reached !");
                    mActivity.toggleFab(false);
                }else{
                    mActivity.toggleFab(true);
                }
            }
        });

        gfSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNext();
            }
        });
        gfSwipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return root;
    }

    @Override
    public void refreshMe() {
        CasesSummary cc = Julisha.countrySummary();
        infectionLabel.setText("" + cc.infected);
        deadLabel.setText("" + cc.dead);
        healedLabel.setText("" + cc.healed);
        majDate.setText("Dernière mise à jour : " + DateUtils.formatDate(Julisha.getLastUpdate(), "E dd MMM yyyy HH:mm:ss", Locale.FRANCE));

        gfSwipe.setRefreshing(false);

    }

    public void refreshNext() {
        DataUpdater.populateCases(getContext(), new DataUpdater.UpdaterListener() {
            @Override
            public void onCompleted() {
                refreshMe();
                graphManager.refreshGraph();
                mActivity.refreshThem();
                Utilities.toastIt(mActivity, "Rafraîchissement effectué!");
            }

            @Override
            public void onFailed() {
                refreshMe();
                graphManager.refreshGraph();
                mActivity.refreshThem();
                Utilities.toastIt(mActivity, "Échec de Rafraîchissement!");
            }
        });

    }

}