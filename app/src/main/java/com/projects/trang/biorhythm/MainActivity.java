package com.projects.trang.biorhythm;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.projects.trang.biorhythm.biorhythmcalculator.BioRhythmCalculator;
import com.projects.trang.biorhythm.render.BarChartRender;
import com.projects.trang.biorhythm.render.LineChartRender;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "MainActivity";
    private TextView tvBirthday;
    private DatePickerDialog.OnDateSetListener dpBirthday;
    private TextView tvSelectedDate;
    private DatePickerDialog.OnDateSetListener dpSelectedDate;
    private TextView tvPhysical;
    private TextView tvEmotional;
    private TextView tvIntellectual;
    private Button btnCalculate;
    private BarChart chart;
    private  String selected_date;
    private  String birth_date;
    private HorizontalBarChart barChart;
    private LineChart lineChart;
    private BioRhythmCalculator aCalculator = new BioRhythmCalculator();
    private Spinner spinner;
    private String item;
    private HashMap<String, Integer> lst_data = new HashMap<String, Integer>(); // <date:phy>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBirthday = (TextView)findViewById(R.id.tvBirthday);
        tvSelectedDate = (TextView)findViewById(R.id.tvSelectedDate);

        tvPhysical = (TextView)findViewById(R.id.tvPhysical);
        tvEmotional = (TextView)findViewById(R.id.tvEmotional);
        tvIntellectual = (TextView)findViewById(R.id.tvIntellectual);
        btnCalculate = (Button)findViewById(R.id.btnCalculate);

        // Initialize barchart & linechart
        barChart = (HorizontalBarChart) findViewById(R.id.barchart);
        lineChart = (LineChart) findViewById(R.id.linechart);

        //Birthday textview
        tvBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog1 = new DatePickerDialog(
                            MainActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            dpBirthday,year,day,month);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog1.show();
            }

        });
        dpBirthday = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                birth_date = month + "/"+ day + "/"+ year;
                Log.d(TAG,"birth date: " + birth_date);
                tvBirthday.setText("Your birthday: " + birth_date);
                aCalculator.setStart_date(birth_date);
            }
        };

        //SelectedDate textview
        tvSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog2 = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dpSelectedDate,year,day,month);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog2.show();
            }

        });
        dpSelectedDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                selected_date = month + "/"+ day + "/"+ year;
                tvSelectedDate.setText("Your selected Date: " + selected_date);
                aCalculator.setEnd_date(selected_date);
            }
        };

        //Spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout

        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // Spinner Drop down elements

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options, android.R.layout.simple_spinner_item);
        /* simple_spinner_item = No line space.
            simple_spinner_dropdown_item = 1 line space.
            simple_dropdown_item_1line = 1.5 line space. */
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(0);


        //Onclick button to show results
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tvBirthday.getText().equals("") || tvSelectedDate.getText().equals("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Please select birthdate and date you want to see biorhythm");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Toast.makeText(MainActivity.this, "You clicked yes ", Toast.LENGTH_LONG).show();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    if(item.equals("the selected day")){
                        int phy = (int) Math.round(100 * aCalculator.getPhysicalValue());
                        int emo = (int) Math.round(100 * aCalculator.getEmotionalValue());
                        int intel = (int) Math.round(100 * aCalculator.getIntellectualValue());
                        // Code here executes on main thread after user presses button
                        Log.d(TAG, "t = " + aCalculator.getT());
                        Log.d(TAG, "*****START date: " + aCalculator.getStart_date());
                        Log.d(TAG, "*****END date: " + aCalculator.getEnd_date());
                        BarChartRender aBarChart = new BarChartRender(barChart,phy,emo,intel);

                        aBarChart.drawBarChart();
                        Log.d(TAG, "PHY = "+ phy);
                        Log.d(TAG, "EMO = "+ emo);
                        Log.d(TAG, "INTEL = "+ intel);

                        tvPhysical.setText(String.format("Physical: %s%%", phy));
                        tvPhysical.setTextColor(Color.rgb(204,51,102));
                        tvEmotional.setText(String.format("Emotion: %s%%", emo));
                        tvEmotional.setTextColor(Color.rgb(255,69,0));
                        tvIntellectual.setText(String.format("Inteligence: %s%%", intel));
                        tvIntellectual.setTextColor(Color.rgb(255,215,0));


                    }else if(item.equals("A month")){
                        barChart.clear();
                        tvPhysical.setText("");
                        tvEmotional.setText("");
                        tvIntellectual.setText("");
                        Toast.makeText(MainActivity.this, "Show data in a month for you", Toast.LENGTH_LONG).show();
                        HashMap<String,Integer> lst_T = aCalculator.getListOfTFromCurrentDate();

                        //from lst_T, calculate phy, output a map <date,phy>

                        // draw linechart
                        LineChartRender aLineChart = new LineChartRender(lineChart,lst_data);

                    }

                }
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}
