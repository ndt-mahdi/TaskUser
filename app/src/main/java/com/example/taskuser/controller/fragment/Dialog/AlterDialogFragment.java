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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.taskuser.R;
import com.example.taskuser.controller.fragment.SingleFragmentRecycler;
import com.example.taskuser.model.Repository;
import com.example.taskuser.model.Task;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlterDialogFragment extends DialogFragment {
   private static final String TASK_ID_ARGS = "task_id";

   private static final String TIME_PICKER = "time_picker";
   private static final String DATE_PICKER = "date_picker";
   private static final String EXTRA_ALTER_TASK = "com.example.taskuser.controller.fragment.Dialog.Alter_Task";
   private static final int REQUEST_CODE_DATE_PICKER = 0;
   private static final int REQUEST_CODE_TIME_PICKER = 1;


    private MaterialButton mButtonDatePicker, mButtonTimePicker;
    private EditText mTitleEditText, mDescriptionEditText;
    private SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
    private RadioGroup mRadioGroupTaskState;
private RadioButton mRadioBTN;

Task oldTask=new Task();
    public AlterDialogFragment() {
        // Required empty public constructor
    }

    public static AlterDialogFragment newInstance(UUID taskId) {

        Bundle args = new Bundle();
        args.putSerializable(TASK_ID_ARGS, taskId);
        AlterDialogFragment fragment = new AlterDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_alter_dialog, null, false);

        oldTask=Repository.getInstance().getTask((UUID)getArguments().getSerializable(TASK_ID_ARGS));
        mRadioGroupTaskState=view.findViewById(R.id.taskStateRG);
        mTitleEditText = view.findViewById(R.id.titleEditTextAlter);
        mDescriptionEditText = view.findViewById(R.id.descEditTextAlter);
        mButtonDatePicker = view.findViewById(R.id.dateButton);
        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = null;
                try {
                    date = formatterDate.parse(mButtonDatePicker.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(date);
                datePickerFragment.setTargetFragment(AlterDialogFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DATE_PICKER);
            }
        });
        mButtonTimePicker = view.findViewById(R.id.timeButton);
        mButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date time = null;
                try {
                    time = formatterTime.parse(mButtonTimePicker.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(time);
                timePickerFragment.setTargetFragment(AlterDialogFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), TIME_PICKER);
            }
        });



        AlertDialog.Builder newAlert = new AlertDialog.Builder(getActivity());
        setData();
        newAlert.setTitle(R.string.edit_task);
        newAlert.setView(view);
        newAlert.setPositiveButton(R.string.Delete, null);
        newAlert.setNeutralButton(R.string.Update,null);
        newAlert.setNegativeButton(R.string.Cancel, null);
        final AlertDialog alertDialog = newAlert.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

              //=============================================================================
                Button buttonUpdate = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkEditText()) {
                            sendResult(updateTask());
                            alertDialog.dismiss();
                        } else
                            Toast.makeText(getActivity(), "Title Is Empty", Toast.LENGTH_LONG).show();
                    }
                });

                //===============================================================================

                //______________________________________________________________________
                Button buttonDelete=alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Repository.getInstance().deleteTask(oldTask);
                            alertDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                //_________________________________________________________________________
            }
        });

        alertDialog.show();
        return alertDialog;

    }
    private void setData() {
        Task task=oldTask;
        long milis = System.currentTimeMillis();
        Date date = new Date(milis);
        mButtonDatePicker.setText(formatterDate.format(date));
        mButtonTimePicker.setText(formatterTime.format(date));
        mTitleEditText.setText(task.getTitle().toString());
        mDescriptionEditText.setText(task.getDescription().toString());
    }
    private Task updateTask() {
        Task task = oldTask;
        task.setTitle(mTitleEditText.getText().toString());
        task.setDescription(mDescriptionEditText.getText().toString());
        task.setDateTask(mButtonDatePicker.getText().toString());
        task.setTimeTask(mButtonTimePicker.getText().toString());

       /* task.setState(state);
        task.setUserIdForeign(userUUID);*/
        return task;

    }
    private Boolean checkEditText() {
        if (mTitleEditText.getText().toString().equals(""))
            return false;
        else
            return true;
    }
    private void sendResult(Task task) {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ALTER_TASK, (Serializable) task);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.getExtraNewTaskDate());
            mButtonDatePicker.setText(formatterDate.format(date));
        }
        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.getEXTRA_NEW_TASK_Time());
            mButtonTimePicker.setText(formatterDate.format(date));
        }

    }

}
