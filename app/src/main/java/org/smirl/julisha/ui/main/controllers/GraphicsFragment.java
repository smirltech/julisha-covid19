package org.smirl.julisha.ui.main.controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.smirl.julisha.R;
import org.smirl.julisha.core.Fragmentation;
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.core.Utilities;
import org.smirl.julisha.ui.main.models.Case;
import org.smirl.julisha.ui.main.models.CasesSummary;
import org.smirl.julisha.ui.main.views.PageViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class GraphicsFragment extends Fragment implements Fragmentation {

  private static final String ARG_SECTION_NUMBER = "section_number";

  private PageViewModel pageViewModel;

  TextView infectionLabel;
  TextView deadLabel;
  TextView healedLabel;

  private LineChart chart;

  public static GraphicsFragment newInstance(int index) {
    GraphicsFragment fragment = new GraphicsFragment();
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
    final TextView textView = root.findViewById(R.id.section_label);
    infectionLabel = root.findViewById(R.id.infection_label);
    deadLabel = root.findViewById(R.id.dead_label);
    healedLabel = root.findViewById(R.id.healed_label);
    chart = root.findViewById(R.id.mchart);
    setUpChart();
    setData();

    refreshMe();
    return root;
  }

  @Override
  public void refreshMe() {
    CasesSummary cc = Julisha.countrySummary();

    infectionLabel.setText("" + cc.infected);
    deadLabel.setText("" + cc.dead);
    healedLabel.setText("" + cc.healed);
  }

  private void setUpChart() {
    chart.getDescription().setEnabled(false);
    chart.setDrawBorders(false);
    chart.setTouchEnabled(true);
    chart.setDragDecelerationFrictionCoef(0.9f);
    chart.setDragEnabled(true);
    chart.setScaleEnabled(true);
    chart.setDrawGridBackground(false);
    chart.setHighlightPerDragEnabled(true);

    chart.setBackgroundColor(Color.WHITE);
    chart.setViewPortOffsets(0f, 0f, 0f, 0f);

    // get the legend (only possible after setting data)
    Legend l = chart.getLegend();
    l.setEnabled(false);

    XAxis xAxis = chart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
    //xAxis.setTypeface(tfLight);
    xAxis.setTextSize(10f);
    xAxis.setTextColor(Color.WHITE);
    xAxis.setDrawAxisLine(false);
    xAxis.setDrawGridLines(false);
    xAxis.setTextColor(Color.rgb(255, 192, 56));
    xAxis.setCenterAxisLabels(false);
    xAxis.setGranularity(1f); // one hour
    xAxis.setValueFormatter(new ValueFormatter() {
      @Override
      public String getBarLabel(BarEntry barEntry) {
        return super.getBarLabel(barEntry);
      }
    });
   /* xAxis.setValueFormatter(new ValueFormatter() {

      private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

      @Override
      public String getFormattedValue(float value) {

        long millis = TimeUnit.HOURS.toMillis((long) value);
        return mFormat.format(new Date(millis));
      }
    });*/

    YAxis leftAxis = chart.getAxisLeft();
    leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
    //leftAxis.setTypeface(tfLight);
    leftAxis.setTextColor(ColorTemplate.getHoloBlue());
    leftAxis.setDrawGridLines(false);
    leftAxis.setGranularityEnabled(true);
    leftAxis.setAxisMinimum(0f);
    leftAxis.setAxisMaximum(20f);
    leftAxis.setYOffset(-9f);
    leftAxis.setTextColor(Color.rgb(255, 192, 56));

    YAxis rightAxis = chart.getAxisRight();
    rightAxis.setEnabled(false);
  }

  private final int[] colors = new int[]{
      ColorTemplate.VORDIPLOM_COLORS[0],
      ColorTemplate.VORDIPLOM_COLORS[1],
      ColorTemplate.VORDIPLOM_COLORS[2]
  };

  private void setData() {

    ArrayList<Entry> values = new ArrayList<>();

    for (Case c : Julisha.cases().casesBy(1)) {
      values.add(new Entry(1, c.nombre)); // add one entry per hour
    }

    // create a dataset and give it a type
    LineDataSet d = new LineDataSet(values, "DataSet 1");
    d.setLineWidth(2.5f);
    d.setCircleRadius(4f);
    d.setAxisDependency(YAxis.AxisDependency.LEFT);
    d.setColor(ColorTemplate.getHoloBlue());
    d.setValueTextColor(ColorTemplate.getHoloBlue());
    d.setLineWidth(1.5f);
    d.setDrawCircles(true);
    d.setDrawValues(false);
    d.setFillAlpha(65);
    d.setFillColor(ColorTemplate.getHoloBlue());
    d.setHighLightColor(Color.rgb(244, 117, 117));
    d.setDrawCircleHole(false);

    // create a data object with the data sets
    LineData data = new LineData(d);
    data.setValueTextColor(Color.WHITE);
    data.setValueTextSize(9f);

    // set data
    chart.setData(data);
  }

}