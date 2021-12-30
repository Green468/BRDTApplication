package com.example.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.presentation.R;
import com.example.presentation.databinding.FragmentVisualAcuityBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisualAcuityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisualAcuityFragment extends Fragment {

    FragmentVisualAcuityBinding binding;
    Button btvatstart;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VisualAcuityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisualAcuityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VisualAcuityFragment newInstance(String param1, String param2) {
        VisualAcuityFragment fragment = new VisualAcuityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        VisualAcuityFragment fragment = new VisualAcuityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVisualAcuityBinding.inflate(inflater, container, false);
        btvatstart = binding.btvatstart;
        btvatstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = VisualAcuityTestFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, mFragment).commit();
//                androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, mFragment);
//                fragmentTransaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();

    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}