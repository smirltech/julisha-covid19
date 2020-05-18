package org.smirl.julisha.ui.main.views;

import android.graphics.Color;
import android.view.View;

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
import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.ui.main.models.CaseGraph;
import org.smirl.julisha.ui.main.models.CaseGraphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class GraphManager {
    final private View view;
    final private LineChart chart, chart2;

    public GraphManager(View view) {
        this.view = view;
        chart = view.findViewById(R.id.mchart);
        chart2 = view.findViewById(R.id.mchart2);
    }

    public void generateGraph() {
        setUpChart(chart, Julisha.getCaseGraphs(), true);
        setUpChart(chart2, Julisha.getCaseGraphs2(), false);
    }

    public void refreshGraph() {
        chart.notifyDataSetChanged();
        chart2.notifyDataSetChanged();
    }

    private void setUpChart(LineChart charty, CaseGraphs caseGraphs, boolean actif) {
        charty.getDescription().setEnabled(false);
        charty.setDrawBorders(false);
        charty.setTouchEnabled(true);
        charty.setPinchZoom(true);
        charty.setHorizontalScrollBarEnabled(true);
        charty.setVerticalScrollBarEnabled(true);
        charty.setDragDecelerationFrictionCoef(0.9f);
        // charty.setDragEnabled(true);
        charty.setScaleEnabled(true);
        charty.setDrawGridBackground(false);
        charty.setHighlightPerDragEnabled(true);

        charty.setBackgroundColor(Color.WHITE);
        charty.setViewPortOffsets(0f, 0f, 0f, 0f);

        // get the legend (only possible after setting data)
        Legend l = charty.getLegend();
        l.setEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXOffset(5F);

        // System.out.println("CaseGraphs : " + Julisha.getCaseGraphs().getCaseGraph(0).date);

        XAxis xAxis = charty.getXAxis();
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
                    return "*";
                }
                return cgg.date.replaceFirst("2020-", "");
            }
        });

        float _max = (float) caseGraphs.getMax();
        YAxis leftAxis = charty.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //leftAxis.setTypeface(tfLight);
        //leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(true);

        leftAxis.setAxisMinimum(_max / (-15));
        //leftAxis.setAxisMaximum(_max + 0f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(3, 3, 3));

        YAxis rightAxis = charty.getAxisRight();
        rightAxis.setEnabled(false);

        setData(charty, caseGraphs, actif);
    }

    private final int[] colors = new int[]{
            ColorTemplate.rgb("#1E88E5"),
            ColorTemplate.rgb("#D32F2F"),
            ColorTemplate.rgb("#7CB342")
    };

    private void setData(LineChart charty, CaseGraphs caseGraphs, boolean actif) {
        // create a data object with the data sets
        LineDataSet infectLD = getLineDateSet(caseGraphs, 1, colors[0], actif);
        LineDataSet deadLD = getLineDateSet(caseGraphs, 2, colors[1], actif);
        LineDataSet healLD = getLineDateSet(caseGraphs, 3, colors[2], actif);

        LineData data = new LineData();

        data.addDataSet(infectLD);
        data.addDataSet(deadLD);
        data.addDataSet(healLD);

        data.setValueTextColor(Color.RED);
        data.setValueTextSize(9f);
        data.setHighlightEnabled(true);

        // set data
        charty.setData(data);
    }

    private LineDataSet getLineDateSet(CaseGraphs caseGraphs, int caseType, int colorTemplate, boolean actif) {
        ArrayList<Entry> values = new ArrayList<>();
        String lbl = actif ? "Actifs" : "Infectés";
        int act = 0;
        ListIterator<CaseGraph> iterator = caseGraphs.listIterator();
        while (iterator.hasNext()){
            CaseGraph c = iterator.next();
                switch (caseType) {
                    case 1:
                        act = actif ? (c.infected - c.dead - c.healed) : c.infected;
                        values.add(new Entry(c.id, act));
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
                        act = actif ? (c.infected - c.dead - c.healed) : c.infected;
                        values.add(new Entry(c.id, act));
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
