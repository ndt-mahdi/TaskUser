package com.example.taskuser.controller;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.taskuser.R;
import com.example.taskuser.controller.fragment.Dialog.NewTaskFragment;
import com.example.taskuser.controller.fragment.SingleFragmentRecycler;
import com.example.taskuser.model.Repository;
import com.example.taskuser.model.TaskState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.UUID;


public class MainActivity extends AppCompatActivity implements NewTaskFragment.NewTaskInterface {
    FloatingActionButton mFAB;
    public static final String NEW_TASK_FRAGMENT_TAG = "New_Task_Fragment";
    public static final int REQUEST_CODE_NEW_TASK = 10;
    private ViewPagerAdapter pagerAdapter;

    UUID userId= Repository.getInstance().checkUserExist("Admin");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.view_pager);
        pagerAdapter  = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
       mFAB = findViewById(R.id.fab_Btn);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stateVal = tabLayout.getSelectedTabPosition();
                TaskState state;
                switch (stateVal) {
                    case 0: {
                        state = TaskState.Todo;
                        break;
                    }
                    case 1: {
                        state = TaskState.Doing;
                        break;
                    }
                    case 2: {
                        state = TaskState.Done;
                        break;
                    }
                    default:
                        state = TaskState.Todo;
                }
               /* NewTaskFragment newTaskFragment= NewTaskFragment.newInstance(state,userId);
                newTaskFragment.setTargetFragment(SingleFragmentRecycler, REQUEST_CODE_NEW_TASK);
                newTaskFragment.show(getFragmentManager(), NEW_TASK_FRAGMENT_TAG);*/

                FragmentManager fm = getSupportFragmentManager();
                NewTaskFragment newTaskFragment = NewTaskFragment.newInstance(state, userId);
               // newTaskFragment.setTargetFragment(SingleFragmentRecycler, REQUEST_CODE_NEW_TASK);

                newTaskFragment.show(fm, NEW_TASK_FRAGMENT_TAG);
            }
        });

    }

    @Override
    public void onSavedClicked() {
        pagerAdapter.notifyDataSetChanged();
    }
}
