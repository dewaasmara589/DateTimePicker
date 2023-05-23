package com.example.testdatetime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText etTimeDate;
    private Button btnHasil;
    private TextView tvHasil, tvHasilSimpan;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTimeDate = findViewById(R.id.etTimeDate);
        btnHasil = findViewById(R.id.btnHasil);
        tvHasil = findViewById(R.id.tvHasil);
        tvHasilSimpan = findViewById(R.id.tvHasilSimpan);

        // Edit text tanggal untuk etTglBerangkat
        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy HH:mm",new Locale("ID"));

                        etTimeDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(MainActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        };

        etTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<List<String>> myArray = new ArrayList<>();

                String tglPilihan = etTimeDate.getText().toString();
                myArray.add(Arrays.asList(tglPilihan, "task1"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", new Locale("ID"));
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(simpleDateFormat.parse(tglPilihan));
                    cal.add(Calendar.HOUR_OF_DAY,1);
                    cal.add(Calendar.MINUTE,15);
                    tvHasil.setText(simpleDateFormat.format(cal.getTime()));

                    String newTglPilihan = simpleDateFormat.format(cal.getTime());
                    myArray.add(Arrays.asList(newTglPilihan, "task2"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String replace = myArray.toString().replace("[","");
                String replace1 = replace.replace(",","");
                List<String> myListPilihan = new ArrayList<String>(Arrays.asList(replace1.split("]")));

                tvHasilSimpan.setText(myListPilihan.toString());
            }
        });
    }
}