package com.example.presentation.fragments;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.presentation.R;
import com.example.presentation.databinding.FragmentMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    public NumberPicker picker;
    public String[] pickerVals = new String[] {"50", "20", "100", "0", "100","20","50"};
    public Button bttstart;
    public ListView videolist;
    public ArrayAdapter<String> adapter;
    String link,iotd;
    String[] listItem;

    BottomNavigationView bottomNavigationView;

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState    ) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = FragmentMainBinding.inflate(inflater, container, false);
        picker = binding.iotdPicker;
        videolist = binding.videoList;

        picker.setMaxValue(6);
        picker.setMinValue(0);
        picker.setDisplayedValues(pickerVals);



        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();

            if (files.length !=0)
            {
                listItem = new String[files.length];

                for (int i = 0; i < files.length; i++)
                {
                    Log.d("Files", "FileName:" + files[i].getName());
                    listItem[i] = files[i].getName();
                }
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        listItem);

            }else Log.e("Files", "Folder not found:");
        }
        catch (Exception e){
            Log.e("Files", "Folder not found:");
        }

        videolist.setAdapter(adapter);
        videolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                link = (String) parent.getItemAtPosition(position);
                iotd = pickerVals[picker.getValue()];
                Log.i("Select:",link);
            }
        });


        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.btStartview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_MainFragment_to_videoViewActivity);
                Log.i("Mainview:","startvideo");
            }
        });
        bottomNavigationView =  (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}