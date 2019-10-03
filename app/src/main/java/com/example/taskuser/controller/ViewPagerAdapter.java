package com.example.taskuser.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taskuser.controller.fragment.DoingListFragment;
import com.example.taskuser.controller.fragment.DoneListFragment;
import com.example.taskuser.controller.fragment.TodoListFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 1:return DoingListFragment.newInstance();
            case 2:return DoneListFragment.newInstance();
            default:return TodoListFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:return "Todo";
            case 1:return "Doing";
            case 2:return "Done";
            default:return "";
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE ;
    }
}
