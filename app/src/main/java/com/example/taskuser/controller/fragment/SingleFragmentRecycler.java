package com.example.taskuser.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskuser.R;
import com.example.taskuser.controller.ViewPagerAdapter;
import com.example.taskuser.model.Repository;
import com.example.taskuser.model.Task;
import com.example.taskuser.model.TaskState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public abstract class SingleFragmentRecycler extends Fragment {

    public static final String NEW_TASK_FRAGMENT_TAG = "New_Task_Fragment";
    public static final int REQUEST_CODE = 0;
    private RecyclerView mRecyclerView;
    private TextView mNotFoundTaskTextView;
    private CardView mCardView, iconState, cardView_ItemList;
    private ImageView mNotFoundImageView;
    public FloatingActionButton mFAB;

    private UUID userUUID;
    private List<Task> mTaskList;
    public abstract List<Task> setTaskList(List<Task> taskList);

    public abstract TaskState viewPagerTaskState();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userUUID = Repository.getInstance().checkUserExist("Admin");
         mTaskList =
                Repository.getInstance().getTaskList(
                        userUUID);

        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_task);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNotFoundImageView = view.findViewById(R.id.not_found_task_ImageView);
        mNotFoundTaskTextView = view.findViewById(R.id.not_found_task_textView);
        mCardView = view.findViewById(R.id.not_found_task_CardView);
        mFAB = view.findViewById(R.id.fab_Btn);

        // Inflate the layout for this fragment
        bindRecyclerView(mTaskList);
        return view;
    }


    public void bindRecyclerView(List<Task> taskList) {
        TaskAdapter taskAdapter = new TaskAdapter(taskList);
        mRecyclerView.setAdapter(taskAdapter);
        if (!taskList.isEmpty()) {
            mCardView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mCardView.setVisibility(View.VISIBLE);
        }


    }

    private class TaskHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mDescription, mTitleIcon;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_text_view);
            mDescription = itemView.findViewById(R.id.date_text_view);
            mTitleIcon = itemView.findViewById(R.id.title_icon_textview);
            iconState = itemView.findViewById(R.id.cardView_IconState);
            cardView_ItemList = itemView.findViewById(R.id.cardView_ItemList);

        }

        private void bindTask(Task task) {
            mTitle.setText(task.getTitle());
            mDescription.setText(task.getDescription());

            if (mTitleIcon != null)
                mTitleIcon.setText(task.getTitle().substring(0, 1));
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void createTask() {
           /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            */
            Date date = new Date(System.currentTimeMillis());

            Task newTask = new Task();
            newTask.setTitle("Mahdi");
            newTask.setDescription("this is ok");
            newTask.setUserIdForeign(userUUID);
            newTask.setState(viewPagerTaskState());
           /* newTask.setDateTask(dateFormat.format(date));
            newTask.setTimeTask(timeFormat.format(date));*/
            Repository.getInstance().insertTask(newTask);

        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        List<Task> mTaskList;


        public TaskAdapter(List<Task> taskList) {

            this.mTaskList = setTaskList(taskList);
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.item_list, parent, false);
            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final TaskHolder holder, int position) {

            mFAB.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                   NewTaskFragment newTaskFragment= NewTaskFragment.newInstance(viewPagerTaskState(),userUUID);
                   newTaskFragment.setTargetFragment(SingleFragmentRecycler.this, REQUEST_CODE);
                   newTaskFragment.show(getFragmentManager(), NEW_TASK_FRAGMENT_TAG);
                   Log.d("qqq", "on Click");
                  /*  Log.d("qqq", "on Click " + mTaskList.size());
                   addNewTask(holder);*/
                }
            });
            holder.bindTask(mTaskList.get(position));
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        void addNewTask(@NonNull TaskHolder holder) {
            holder.createTask();
            //bind recyclerViewItem's
            bindRecyclerView(Repository.getInstance().getTaskList(userUUID));
            //bind new task
            holder.bindTask(mTaskList.get(getItemCount() - 1));
            //go to bottom of recycler
            mRecyclerView.scrollToPosition(getItemCount() - 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK||data==null)
            return;
        if (requestCode==REQUEST_CODE)
        {
Task task=(Task)data.getSerializableExtra(NewTaskFragment.EXTRA_NEW_TASK);
mTaskList.add(task);
bindRecyclerView(mTaskList);
        }
    }
}
