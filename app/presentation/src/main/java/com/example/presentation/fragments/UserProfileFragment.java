package com.example.presentation.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.presentation.R;
import com.example.presentation.databinding.FragmentUserprofileBinding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class UserProfileFragment extends Fragment {
    private FragmentUserprofileBinding binding;

    public static UserProfileFragment newInstance(){
        UserProfileFragment fragment = new UserProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState    ) {

//        binding = FragmentUserprofileBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_userprofile, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
