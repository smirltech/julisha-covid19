package org.smirl.julisha.ui.main.controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
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
import org.smirl.julisha.core.Fragmentation;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.core.Utilities;
import org.smirl.julisha.ui.main.models.CaseGraph;
import org.smirl.julisha.ui.main.models.CasesSummary;
import org.smirl.julisha.ui.main.views.PageViewModel;

import java.util.ArrayList;
import java.util.Date;


/**
 * A placeholder fragment containing a simple view.
 */
public class GraphicsFragment extends Fragment implements Fragmentation {

  private static final String ARG_SECTION_NUMBER = "section_number";
  private MainActivity mActivity;
  private SwipeRefreshLayout gfSwipe;

  private PageViewModel pageViewModel;

  TextView infectionLabel;
  TextView deadLabel;
  TextView healedLabel;
  TextView majDate;

  private LineChart chart;
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

    final TextView textView = root.findViewById(R.id.section_label);
    infectionLabel = root.findViewById(R.id.infection_label);
    deadLabel = root.findViewById(R.id.dead_label);
    healedLabel = root.findViewById(R.id.healed_label);
    majDate = root.findViewById(R.id.maj_date);
    chart = root.findViewById(R.id.mchart);
    //anyChartView = (AnyChartView)root.findViewById(R.id.any_chart_view);
    setUpChart();
    setData();

    refreshMe();

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
    majDate.setText("Dernière mise à jour : " + new Date(Julisha.getLastUpdate()).toString());

    gfSwipe.setRefreshing(false);

  }

  public void refreshNext() {
    DataUpdater.populateCases(getContext(), new DataUpdater.UpdaterListener() {
      @Override
      public void onCompleted() {
        refreshMe();
        chart.notifyDataSetChanged();
        mActivity.refreshThem();
        Utilities.toastIt(mActivity, "Refresh done!");
      }

      @Override
      public void onFailed() {
        refreshMe();
        chart.notifyDataSetChanged();
        mActivity.refreshThem();
        Utilities.toastIt(mActivity, "Refresh done!");
      }
    });

  }

  private void setUpChart() {
    chart.getDescription().setEnabled(false);
    chart.setDrawBorders(false);
    chart.setTouchEnabled(true);
    chart.setPinchZoom(true);
    chart.setHorizontalScrollBarEnabled(true);
    chart.setVerticalScrollBarEnabled(true);
    chart.setDragDecelerationFrictionCoef(0.9f);
   // chart.setDragEnabled(true);
    chart.setScaleEnabled(true);
    chart.setDrawGridBackground(false);
    chart.setHighlightPerDragEnabled(true);

    chart.setBackgroundColor(Color.WHITE);
    chart.setViewPortOffsets(0f, 0f, 0f, 0f);

    // get the legend (only possible after setting data)
    Legend l = chart.getLegend();
    l.setEnabled(true);
    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
    l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    l.setDrawInside(false);
    l.setXOffset(5F);


    // System.out.println("CaseGraphs : " + Julisha.caseGraphs().getCaseGraph(0).date);

    XAxis xAxis = chart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
    //xAxis.setTypeface(tfLight);
    xAxis.setTextSize(10f);
    xAxis.setDrawAxisLine(false);
    xAxis.setDrawGridLines(false);
    xAxis.setTextColor(Color.rgb(3, 3, 3));
    xAxis.setCenterAxisLabels(false);
    xAxis.setAxisMinimum(-1f);
    xAxis.setAxisMaximum(Julisha.caseGraphs().size() * 1f);
    xAxis.setGranularity(1f);
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);// one hour
    xAxis.setValueFormatter(new ValueFormatter() {

      @Override
      public String getFormattedValue(float value) {
        CaseGraph cgg = Julisha.caseGraphs().getCaseGraph((int) value);
        // if (cgg == null){
        //   System.out.println("wrong id = " + value);
        //}
        return cgg.date;
      }
    });

    YAxis leftAxis = chart.getAxisLeft();
    leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
    //leftAxis.setTypeface(tfLight);
    //leftAxis.setTextColor(ColorTemplate.getHoloBlue());
    leftAxis.setDrawGridLines(false);
    leftAxis.setGranularityEnabled(true);
    leftAxis.setAxisMinimum(-1f);
    leftAxis.setAxisMaximum((float) Julisha.maxCase() + 1f);
    leftAxis.setYOffset(-9f);
    leftAxis.setTextColor(Color.rgb(3, 3, 3));

    YAxis rightAxis = chart.getAxisRight();
    rightAxis.setEnabled(false);
  }

  private final int[] colors = new int[]{
      ColorTemplate.rgb("#0000ff"),
      ColorTemplate.rgb("#ff0000"),
      ColorTemplate.rgb("#00ff00")
  };

  private void setData() {

    // List<DataEntry> anyData = new ArrayList<>();


// create a data object with the data sets
    LineDataSet infectLD = getLineDateSet(1, colors[0]);
    LineDataSet deadLD = getLineDateSet(2, colors[1]);
    LineDataSet healLD = getLineDateSet(3, colors[2]);

    LineData data = new LineData();

    data.addDataSet(infectLD);
    data.addDataSet(deadLD);
    data.addDataSet(healLD);

    data.setValueTextColor(Color.RED);
    data.setValueTextSize(9f);
    data.setHighlightEnabled(true);

    // set data
    chart.setData(data);
  }


  private LineDataSet getLineDateSet(int caseType, int colorTemplate) {
    ArrayList<Entry> values = new ArrayList<>();
    String lbl = "Infectés";
    for (CaseGraph c : Julisha.caseGraphs()) {
      switch (caseType) {
        case 1:
          values.add(new Entry(c.id, c.infected));
          break;
        case 2:
          values.add(new Entry(c.id, c.dead));
          lbl = "Décédés";
          break;
        case 3:
          values.add(new Entry(c.id, c.healed));
          lbl = "Guéris";
          break;
        default:
          values.add(new Entry(c.id, c.infected));
      }


    }

    // create a dataset and give it a type
    LineDataSet d = new LineDataSet(values, lbl);
    d.setCircleRadius(1f);
    d.setAxisDependency(YAxis.AxisDependency.LEFT);
    d.setColor(colorTemplate);
    d.setValueTextColor(colorTemplate);
    d.setLineWidth(2f);
    d.setDrawCircles(false);
    d.setDrawValues(false);
    d.setFillAlpha(65);
    d.setFillColor(colorTemplate);
    d.setHighLightColor(Color.rgb(244, 117, 117));
    d.setDrawCircleHole(false);
    d.setCubicIntensity(1f);
    d.setHighlightEnabled(true);
    d.setDrawHighlightIndicators(true);

    return d;
  }
}