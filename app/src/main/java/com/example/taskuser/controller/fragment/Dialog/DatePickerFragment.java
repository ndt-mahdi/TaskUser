package com.example.taskuser.controller.fragment.Dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.taskuser.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {


    private static final String ARG_NEW_TASK_DATE = "newTaskDate";
    private static final String EXTRA_NEW_TASK_DATE ="TaskDate" ;
    private DatePicker mDatePicker;
    private Date mDate;

    public static String getExtraNewTaskDate() {
        return EXTRA_NEW_TASK_DATE;
    }

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
args.putSerializable(ARG_NEW_TASK_DATE,date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate=(Date)getArguments().getSerializable(ARG_NEW_TASK_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_date_picker,null,false);
    //Next Line Is Like "Find By ID"
      mDatePicker=(DatePicker)view;
        initDatePicker();
        return  new AlertDialog.Builder(getActivity())
               .setTitle("Date Of Task")
               .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       Date date = extractDate();
                       sendResult(date);
                   }
               })
               .setView(view)
               .create();

    }

    private Date extractDate() {
        int year=mDatePicker.getYear();
        int monthOfYear=mDatePicker.getMonth();
        int dayOfMonth=  mDatePicker.getDayOfMonth();
        GregorianCalendar gregorianCalendar=new GregorianCalendar(year,monthOfYear,dayOfMonth);
        return gregorianCalendar.getTime();
    }

    private void initDatePicker() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=  calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year,monthOfYear,dayOfMonth,null);
    }
    private void sendResult(Date date)
    {
Fragment fragment=getTargetFragment();
        Intent intent=new Intent();
        intent.putExtra(EXTRA_NEW_TASK_DATE,date);
    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}
