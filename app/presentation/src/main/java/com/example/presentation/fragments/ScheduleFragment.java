package com.example.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.presentation.databinding.FragmentScheduleBinding;

public class ScheduleFragment extends Fragment {
    FragmentScheduleBinding bingding;


    public static Fragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bingding = FragmentScheduleBinding.inflate(inflater,container,false);


        return bingding.getRoot();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
