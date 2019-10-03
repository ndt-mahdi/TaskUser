package com.example.taskuser.controller.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskuser.R;
import com.example.taskuser.controller.MainActivity;
import com.example.taskuser.controller.fragment.Dialog.AlterDialogFragment;
import com.example.taskuser.controller.fragment.Dialog.NewTaskFragment;
import com.example.taskuser.model.Repository;
import com.example.taskuser.model.Task;
import com.example.taskuser.model.TaskState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class SingleFragmentRecycler extends Fragment {

    public static final String NEW_TASK_FRAGMENT_TAG = "New_Task_Fragment";
    public static final String ALTER_DIALOG_FRAGMENT_TAG = "Alter_Dialog_Fragment";
    public static final int REQUEST_CODE_NEW_TASK = 0;
    public static final int REQUEST_CODE_ALTER_DIALOG = 1;

    public static final String EXTER_USER_ID = "com.example.taskuser.controller.fragment.user_id";
    private RecyclerView mRecyclerView;
    private TextView mNotFoundTaskTextView;
    private CardView mCardView, iconState, cardView_ItemList;
    private ImageView mNotFoundImageView;

    private UUID userUUID;
    private List<Task> mTaskList;

    public abstract List<Task> setTaskList(List<Task> taskList);

    public abstract TaskState viewPagerTaskState();

    public void bindRecyclerView(List<Task> taskList) {
        TaskAdapter taskAdapter = new TaskAdapter(taskList);
        if (!taskAdapter.taskAdapterList.isEmpty()) {
            mCardView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setAdapter(taskAdapter);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mCardView.setVisibility(View.VISIBLE);
        }


    }

    public static Intent newIntent(Context context, UUID id) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTER_USER_ID, id);
        return intent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userUUID = Repository.getInstance().checkUserExist("Admin");
        mTaskList = Repository.getInstance().getTaskList(userUUID);

        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_task);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNotFoundImageView = view.findViewById(R.id.not_found_task_ImageView);
        mNotFoundTaskTextView = view.findViewById(R.id.not_found_task_textView);
        mCardView = view.findViewById(R.id.not_found_task_CardView);


        // Inflate the layout for this fragment
        bindRecyclerView(mTaskList);
        return view;
    }


    private class TaskHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mDescription, mTitleIcon;
        private Task mTask;
        public TaskHolder(@NonNull final View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_text_view);
            mDescription = itemView.findViewById(R.id.date_text_view);
            mTitleIcon = itemView.findViewById(R.id.title_icon_textview);
            iconState = itemView.findViewById(R.id.cardView_IconState);
            cardView_ItemList = itemView.findViewById(R.id.cardView_ItemList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlterDialogFragment alterDialogFragment= AlterDialogFragment.newInstance(mTask.getTaskID());
                    alterDialogFragment.setTargetFragment(SingleFragmentRecycler.this, REQUEST_CODE_ALTER_DIALOG);
                    alterDialogFragment.show(getFragmentManager(), ALTER_DIALOG_FRAGMENT_TAG);


                }
            });

        }

        private void bindTask(Task task) {
            mTitle.setText(task.getTitle());
            mDescription.setText(task.getDescription());

            if (mTitleIcon != null) {

                mTitleIcon.setText(task.getTitle().substring(0, 1));
            }
            mTask=task;
        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        List<Task> taskAdapterList;


        public TaskAdapter(List<Task> taskList) {

            taskAdapterList = setTaskList(taskList);
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


            holder.bindTask(taskAdapterList.get(position));
           // iconState.setCardBackgroundColor(getRandomColor(getActivity()));
        }

        @Override
        public int getItemCount() {
            return taskAdapterList.size();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_NEW_TASK) {
            Task task = (Task) data.getSerializableExtra(NewTaskFragment.getExtraNewTask());
            mTaskList.add(task);
            bindRecyclerView(mTaskList);
        }
        if (requestCode==REQUEST_CODE_ALTER_DIALOG)
        {
            Task task = (Task) data.getSerializableExtra(NewTaskFragment.getExtraNewTask());
            bindRecyclerView(mTaskList);
        }
    }

    private int getRandomColor(Context context) {

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        return randomAndroidColor;
    }

}
