package com.example.taskuser.controller.fragment;



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

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskuser.R;
import com.example.taskuser.controller.MainActivity;
import com.example.taskuser.model.Task;
import com.example.taskuser.model.TaskState;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewTaskFragment extends DialogFragment {
    public static final String USER_ID_ARGS = "User_Id_Args";
    public static final String TASK_STATE_ARGS = "Task_State_Args";
    public static final String EXTRA_NEW_TASK = "New_Task";
    private MaterialButton mButtonDatePicker,mButtonTimePicker;
    private EditText mTitleEditText,mDescriptionEditText;
    public static NewTaskFragment newInstance(TaskState taskState, UUID userId) {
        
        Bundle args = new Bundle();
        args.putSerializable(TASK_STATE_ARGS,taskState);
        args.putSerializable(USER_ID_ARGS,userId);
        NewTaskFragment fragment = new NewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NewTaskFragment() {
        // Required empty public constructor
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_new_task,null,false);
        mButtonDatePicker=view.findViewById(R.id.dateButton);
        mButtonTimePicker=view.findViewById(R.id.timeButton);
        mTitleEditText=view.findViewById(R.id.titleEditText);
        mDescriptionEditText=view.findViewById(R.id.descEditText);
         AlertDialog.Builder newAlert =new AlertDialog.Builder(getActivity());
        newAlert.setTitle(R.string.new_task);
        newAlert.setView(view);
        newAlert .setPositiveButton(R.string.save, null);
        newAlert.setNegativeButton(android.R.string.cancel,null);
                final  AlertDialog alertDialog=newAlert.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button=alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (checkEditText())
                                {
                                     sendResult(createTask());
                                alertDialog.dismiss();}
                                else
                                    Toast.makeText(getActivity(),"Title Is Empty",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                alertDialog.show();
                return alertDialog;
    }
    private Boolean checkEditText()
    {
if(mTitleEditText.getText().toString().equals(""))
    return false;
else
return true;
    }
    private Task createTask() {
        TaskState state=(TaskState)getArguments().getSerializable(TASK_STATE_ARGS);
        UUID userUUID=(UUID)getArguments().getSerializable(USER_ID_ARGS);

            Task task = new Task();
            task.setTitle(mTitleEditText.getText().toString());
            task.setDescription(mDescriptionEditText.getText().toString());
            task.setDateTask(mButtonDatePicker.getText().toString());
            task.setTimeTask(mButtonTimePicker.getText().toString());
            task.setState(state);
            task.setUserIdForeign(userUUID);
            return task;

    }
    private void sendResult(Task task)
    {
Fragment fragment=getTargetFragment();
        Intent intent=new Intent();
        intent.putExtra(EXTRA_NEW_TASK, (Serializable) task);
fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}
