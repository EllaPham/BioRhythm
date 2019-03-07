package com.projects.trang.biorhythm.biorhythmcalculator;

import android.util.Log;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Trang on 2/13/2019.
 */

public class BioRhythmCalculator {

    private String TAG = "BioRhythmCalculator";
    private long t;
    private String start_date;
    private String end_date;
    private  LinkedHashMap<String,Long> dict_T =new LinkedHashMap<>();

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
    public float getPhysicalValue(Long t) {
        return (float) Math.round(100 *Math.sin((2 * Math.PI * t) / 23));
    }

    public double getEmotionalValue(Long t) {
        return  (int) Math.round(100 *Math.sin((2 * Math.PI *t) / 28));
    }

    public double getIntellectualValue(Long t) {
        return  (int) Math.round(100 *Math.sin((2 * Math.PI * t) / 33));
    }

    // t = number of days from birthday to selected date, get t from this function
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
  // use this function to get T, with e_date = selected_date or current_date
    private long numberOfDaysFromBirth(String s_date, String e_date) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date startDate = sdf.parse(s_date);
            Date endDate = sdf.parse(e_date);
            long diff = endDate.getTime() - startDate.getTime();
            Log.d(TAG,"endDate.getTime() : " + endDate.getTime());
            Log.d(TAG,"startDate.getTime() : " + startDate.getTime());
            Log.d("BioRhythmCalculator: ", "t = " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public LinkedHashMap<String,Long> getListOfTFromCurrentDate(){

        // getcurrent_date
        Format f = new SimpleDateFormat("MM/dd/yyyy");
        String current_date = f.format(new Date());
        Log.d(TAG, "*****CURRENT DATE: "+ current_date);
        String dt = current_date;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        for(int i = 1;i<31;i++){
            Log.d(TAG,"T from birthday to dt: " + numberOfDaysFromBirth(start_date,dt));
            dict_T.put(dt, numberOfDaysFromBirth(start_date,dt));

            try {
                c.setTime(sdf.parse(dt));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DAY_OF_YEAR, 1);  // number of days to add
            dt = sdf.format(c.getTime());  // dt is now the new date
        }
        Log.d(TAG, "DICT_T = "+ dict_T);
        Log.d(TAG, "DICT_T keys = "+ dict_T.keySet());
        Log.d(TAG,"DICT_T values = " + dict_T.values());
        // now we have dt(the next date) and t
        //return a map <date,T>
            return dict_T;
        }
    public LinkedHashMap<String,Float> getPhyForAMonth(){
        LinkedHashMap<String,Float> dict_phy = new LinkedHashMap<>();
        this.getListOfTFromCurrentDate();
        for(String str: dict_T.keySet()){
            dict_phy.put(str,this.getPhysicalValue(dict_T.get(str)));
        }
        Log.d(TAG,"DICT_PHY = " + dict_phy);
        return dict_phy;
    }
    public LinkedHashMap<String,Double> getEmoForAMonth(){
        return null;
    }
    public LinkedHashMap<String,Double> getIntelForAmonth(){
        return null;
    }

}
