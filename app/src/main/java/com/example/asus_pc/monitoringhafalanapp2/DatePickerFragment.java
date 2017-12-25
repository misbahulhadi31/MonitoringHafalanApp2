package com.example.asus_pc.monitoringhafalanapp2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by misbahul on 10/18/17.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private String strNow;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int mont, int day) {

        if (datePicker.getMonth() == 0){
            strNow = datePicker.getDayOfMonth() + " Januari " + datePicker.getYear();
        } else if (datePicker.getMonth() == 1){
            strNow = datePicker.getDayOfMonth() + " Februari " + datePicker.getYear();
        } else if (datePicker.getMonth() == 2){
            strNow = datePicker.getDayOfMonth() + " Maret " + datePicker.getYear();
        } else if (datePicker.getMonth() == 3){
            strNow = datePicker.getDayOfMonth() + " April " + datePicker.getYear();
        } else if (datePicker.getMonth() == 4){
            strNow = datePicker.getDayOfMonth() + " Mei " + datePicker.getYear();
        } else if (datePicker.getMonth() == 5){
            strNow = datePicker.getDayOfMonth() + " Juni " + datePicker.getYear();
        } else if (datePicker.getMonth() == 6){
            strNow = datePicker.getDayOfMonth() + " Juli " + datePicker.getYear();
        } else if (datePicker.getMonth() == 7){
            strNow = datePicker.getDayOfMonth() + " Agustus " + datePicker.getYear();
        } else if (datePicker.getMonth() == 8){
            strNow = datePicker.getDayOfMonth() + " September " + datePicker.getYear();
        } else if (datePicker.getMonth() == 9){
            strNow = datePicker.getDayOfMonth() + " Oktober " + datePicker.getYear();
        } else if (datePicker.getMonth() == 10){
            strNow = datePicker.getDayOfMonth() + " November " + datePicker.getYear();
        } else if (datePicker.getMonth() == 11){
            strNow = datePicker.getDayOfMonth() + " Desember " + datePicker.getYear();
        }

        String strData = datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth();
        Log.d("date selected", strData);

        EditText editTextBirthDate = getActivity().findViewById(R.id.tanggal);
        editTextBirthDate.setText(strData);
    }
}
