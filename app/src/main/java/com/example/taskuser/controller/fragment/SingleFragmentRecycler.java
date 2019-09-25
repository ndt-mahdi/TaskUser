package com.example.taskuser.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskuser.R;
import com.example.taskuser.model.Repository;
import com.example.taskuser.model.Task;
import com.example.taskuser.model.TaskState;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleFragmentRecycler extends Fragment {
    
    private RecyclerView mRecyclerView;
    public abstract List<Task> setTaskList(List<Task> taskList);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<Task> mTaskList=Repository.getInstance().getTaskList(
                Repository.getInstance().checkUserExist("Admin"));


        View view= inflater.inflate(R.layout.fragment_recycler_list, container, false);
        mRecyclerView=view.findViewById(R.id.recycler_view_task);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Inflate the layout for this fragment
        bindRecyclerView(mTaskList);
        return view;
    }

    public void bindRecyclerView(List<Task> taskList) {
        if(taskList.isEmpty()){
        TaskAdapter taskAdapter = new TaskAdapter(taskList);
        mRecyclerView.setAdapter(taskAdapter);}


    }

    private class TaskHolder extends RecyclerView.ViewHolder
    {
        TextView mTitle,mDescription,mTitleIcon;
        CardView mCardView;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.title_text_view);
            mDescription=itemView.findViewById(R.id.date_text_view);
            mTitleIcon=itemView.findViewById(R.id.title_icon_textview);
            mCardView=itemView.findViewById(R.id.cardView_ItemList);

        }
        private void bindTask(Task task)
        {
            mTitle.setText(task.getTitle());
            mDescription.setText(task.getDescription());
            if(mTitleIcon!=null)
                mTitleIcon.setText(task.getTitle().substring(0,1));
        }
    }
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>
    {
        List<Task> mTaskList;

        public TaskAdapter(List<Task> mTaskList) {

            this.mTaskList = setTaskList(mTaskList);
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());

            View view=layoutInflater.inflate(R.layout.item_list,parent,false);
            TaskHolder taskHolder=new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {

            holder.bindTask(mTaskList.get(position));
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
    }
}
