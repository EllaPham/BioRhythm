package com.projects.trang.biorhythm.biorhythmcalculator;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Trang on 2/13/2019.
 */

public class BioRhythmCalculator {

    private long t;
    private String start_date;
    private String end_date;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public double getPhysicalValue() {
        return Math.sin((2 * Math.PI * this.numberOfDaysFromBirth()) / 23);
    }

    public double getEmotionalValue() {
        return Math.sin((2 * Math.PI * this.numberOfDaysFromBirth()) / 28);
    }

    public double getIntellectualValue() {
        return Math.sin((2 * Math.PI * this.numberOfDaysFromBirth()) / 33);
    }

    private long numberOfDaysFromBirth() {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date startSate = sdf.parse(start_date);
            Date endDate = sdf.parse(end_date);
            long diff = endDate.getTime() - startSate.getTime();
            Log.d("BioRhythmCalculator: ", "t = " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private long numberOfDaysFromBirth(String s_date, String e_date) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date startSate = sdf.parse(s_date);
            Date endDate = sdf.parse(e_date);
            long diff = endDate.getTime() - startSate.getTime();
            Log.d("BioRhythmCalculator: ", "t = " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public HashMap<String,Integer> getListOfTFromCurrentDate(){
        String current_date="";
        // getcurrent_date

        //getT; T: days from  birthday to current date.
        long T = numberOfDaysFromBirth(start_date,current_date);

        //return a map <date,T>
            return null;
        }

}
