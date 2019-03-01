package com.projects.trang.biorhythm.render;

import android.graphics.Color;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Trang on 2/17/2019.
 */

public class BarChartRender {
    private int phy;
    private int emo;
    private int intel;
    private HorizontalBarChart barChart;

    public BarChartRender( HorizontalBarChart barChart, int phy, int emo, int intel) {
        this.barChart = barChart;
        this.phy = phy;
        this.emo = emo;
        this.intel = intel;
    }

    public void drawBarChart(){

        // http://blog.appliedinformaticsinc.com/how-to-plot-charts-graphs-in-android-with-mpandroidchart-library/
        //https://github.com/PhilJay/MPAndroidChart/issues/3380
        //https://www.studytutorial.in/android-bar-chart-or-bar-graph-using-mpandroid-library-tutorial
        //legend tutorial : https://github.com/PhilJay/MPAndroidChart/wiki/Legend

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Physical");
        labels.add("Emotional");
        labels.add("Intellectual");

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(phy, 0));
        entries.add(new BarEntry(emo, 1));
        entries.add(new BarEntry(intel, 2));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        barChart.invalidate();
        //show percentage format
        data.setValueFormatter(new PercentFormatter());
        //data.setValueFormatter(new DefaultValueFormatter(1));

        // set text size for data
        data.setValueTextSize(10f);

        // Hide grid lines
        //barChart.getAxisLeft().setEnabled(false);
        //barChart.getAxisRight().setEnabled(false);

        // Display scores inside the bars
        barChart.setDrawValueAboveBar(false);

        // reduce width of bar
        bardataset.setBarSpacePercent(30f);

        // Design
        bardataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        barChart.setDescription("BioRyhthm Calculator");
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
    }
}
