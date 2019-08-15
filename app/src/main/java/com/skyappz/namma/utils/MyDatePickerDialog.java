package com.skyappz.namma.utils;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

public class MyDatePickerDialog {

    private int mYear;
    private int mMonth;
    private int mDay;

    public void showDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}