package com.projects.trang.biorhythm.render;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Trang on 2/28/2019.
 */

public class LineChartRender {
    HashMap<String, Integer> lst_data = new HashMap<String, Integer>(); // <date:phy>
    private LineChart lineChart;

    public LineChartRender( LineChart lineChart, HashMap<String, Integer> lst_data) {
        this.lst_data = lst_data;
        this.lineChart = lineChart;
    }

    public void drawLineChart() {
    }

    // documents: https://www.studytutorial.in/android-line-chart-or-line-graph-using-mpandroid-library-tutorial
    private ArrayList<String> setXAxisValues() {
        ArrayList<String> xVals = new ArrayList<String>();

        for (Object a_date : lst_data.keySet()) {
            xVals.add(String.valueOf(a_date));
        }

        return xVals;
    }

    // This is used to store Y-axis values
    private ArrayList<Entry> setYAxisValues() {
        // Y SHOW DATE EX. =03/03, show keys of lst_days map
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        int i = 0;
        for (Object value : lst_data.values()) {
            yVals.add(new Entry((float) value, i));
            i++;
        }
        return yVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        lineChart.setData(data);
    }
}