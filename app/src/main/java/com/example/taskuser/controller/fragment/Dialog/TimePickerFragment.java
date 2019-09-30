package com.example.taskuser.controller.fragment.Dialog;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import com.example.taskuser.R;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    private static final String ARG_NEW_TASK_Time = "newTaskTime";

    public static String getEXTRA_NEW_TASK_Time() {
        return EXTRA_NEW_TASK_Time;
    }

    private static final String EXTRA_NEW_TASK_Time ="TaskTime" ;
    private TimePicker mTimePicker;
    private Date mTime;
    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(Date time) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_NEW_TASK_Time,time);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTime=(Date) getArguments().getSerializable(ARG_NEW_TASK_Time);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker,null,false);
        //Next Line Is Like "Find By ID"
        mTimePicker=(TimePicker) view;
        initTimePicker();
        return  new AlertDialog.Builder(getActivity())
                .setTitle("Date Of Task")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date time = extractTime();
                        sendResult(time);
                    }
                })
                .setView(view)
                .create();
    }

    private void initTimePicker() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mTime);
        mTimePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentHour(calendar.get(Calendar.MINUTE));
        mTimePicker.setIs24HourView(true);
    }
    private Date extractTime() {
        int hour=mTimePicker.getCurrentHour();
        int minute=mTimePicker.getCurrentMinute();
        Date time=new Time(hour,minute,0);
        return time;
    }
    private void sendResult(Date time)
    {
        Fragment fragment=getTargetFragment();
        Intent intent=new Intent();
        intent.putExtra(EXTRA_NEW_TASK_Time,time);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}
