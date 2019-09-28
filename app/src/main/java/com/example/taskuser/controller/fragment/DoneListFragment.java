package com.example.taskuser.controller.fragment;


import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.taskuser.model.Task;
import com.example.taskuser.model.TaskState;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoneListFragment extends SingleFragmentRecycler {


    public DoneListFragment() {
        // Required empty public constructor
    }

    public static DoneListFragment newInstance() {

        Bundle args = new Bundle();

        DoneListFragment fragment = new DoneListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public List<Task> setTaskList(List<Task> taskList) {
        List<Task> newTaskList = new ArrayList<>();
        for (Task e : taskList) {
            if (e.getState().equals(TaskState.Done))
                newTaskList.add(e);
        }
        return newTaskList;

    }

    @Override
    public TaskState viewPagerTaskState() {
        return TaskState.Done;
    }
}
