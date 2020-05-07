package org.smirl.julisha.ui.main.views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.smirl.julisha.R;
import org.smirl.julisha.ui.main.models.CaseGraph;
import org.smirl.julisha.ui.main.models.CaseGraphs;

import java.util.ArrayList;

public class LoneGraph {
    private LayoutInflater inflater;
    CaseGraphs caseGraphs;
    String title;

    private TextView titleView;
    private LineChart loneChart;
    public LoneGraph(LayoutInflater inflater, String title, CaseGraphs caseGraphs){
        this.inflater = inflater;
        this.caseGraphs = caseGraphs;
        this.title = title;
        setView();
    }

    private void setView(){
       View view = inflater.inflate(R.layout.lone_graph, null, false);
       titleView = view.findViewById(R.id.lone_chart_title);
       loneChart = view.findViewById(R.id.lone_chart);

       titleView.setText(title);
       setUpChart(loneChart, caseGraphs);

        AlertDialog dialog =  new AlertDialog.Builder(inflater.getContext())
                .setView(view)
                .show();
    }

    private void setUpChart(LineChart chart, CaseGraphs caseGraphs) {
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

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        //xAxis.setTypeface(tfLight);
        xAxis.setTextSize(8f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.rgb(3, 3, 3));
        xAxis.setCenterAxisLabels(false);
        xAxis.setAxisMinimum(-2f);
        xAxis.setAxisMaximum(caseGraphs.size() * 1f);
        xAxis.setGranularity(1f);
        // xAxis.setLabelRotationAngle(-45f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);// one hour
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                CaseGraph cgg = caseGraphs.getCaseGraph((int) value);
                if (cgg == null) {
                    return "Aujourd'hui";
                }
                return cgg.date.replaceFirst("2020-", "");
            }
        });

        float _max = (float) caseGraphs.getMax();
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //leftAxis.setTypeface(tfLight);
        //leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(true);

        leftAxis.setAxisMinimum(_max / (-15));
        leftAxis.setAxisMaximum(_max + 0f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(3, 3, 3));

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        setData(loneChart, caseGraphs);
    }

    private final int[] colors = new int[]{
            ColorTemplate.rgb("#1E88E5"),
            ColorTemplate.rgb("#D32F2F"),
            ColorTemplate.rgb("#7CB342")
    };

    private void setData(LineChart chart, CaseGraphs caseGraphs) {

        // List<DataEntry> anyData = new ArrayList<>();


// create a data object with the data sets
        LineDataSet infectLD = getLineDateSet(caseGraphs, 1, colors[0]);
        LineDataSet deadLD = getLineDateSet(caseGraphs, 2, colors[1]);
        LineDataSet healLD = getLineDateSet(caseGraphs, 3, colors[2]);

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

    private LineDataSet getLineDateSet(CaseGraphs caseGraphs, int caseType, int colorTemplate) {
        ArrayList<Entry> values = new ArrayList<>();
        String lbl = "Actifs";
        for (CaseGraph c : caseGraphs) {
            switch (caseType) {
                case 1:
                    values.add(new Entry(c.id, (c.infected - c.dead - c.healed)));
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
                    values.add(new Entry(c.id, (c.infected - c.dead - c.healed)));
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
