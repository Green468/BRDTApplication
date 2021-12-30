package com.example.presentation.fragments;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.presentation.R;
import com.example.presentation.databinding.FragmentMainBinding;
import com.example.presentation.databinding.FragmentVisualAcuityTestBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisualAcuityTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisualAcuityTestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public int cnt=0;
//    Drawable iml = ContextCompat.getDrawable(getContext(),R.drawable.left);
//    Drawable imr = ContextCompat.getDrawable(getContext(),R.drawable.left);
//    Drawable imu = ContextCompat.getDrawable(getContext(),R.drawable.left);
//    Drawable imd = ContextCompat.getDrawable(getContext(),R.drawable.left);
//    Drawable imll = ContextCompat.getDrawable(getContext(),R.drawable.left);
//    Drawable imlh = ContextCompat.getDrawable(getContext(),R.drawable.left);
//    Drawable imrl = ContextCompat.getDrawable(getContext(),R.drawable.left);
//    Drawable imrh = ContextCompat.getDrawable(getContext(),R.drawable.left);

    Drawable iml, imr, imu, imd, imll, imlh, imrl, imrh;

    @NonNull
    FragmentVisualAcuityTestBinding binding;
    ImageView imgviewl,imgviewr;
    float density;
    Chronometer chrom;
    BottomNavigationView bottomNavigationView;
    List<String> iconlist = Arrays.asList("left","right","up","down");
    public ProgressBar progressBar1,progressBar2 ;
    public VisualAcuityTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisualAcuityTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VisualAcuityTestFragment newInstance(String param1, String param2) {
        VisualAcuityTestFragment fragment = new VisualAcuityTestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        VisualAcuityTestFragment fragment = new VisualAcuityTestFragment();
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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Inflate the layout for this fragment
        binding = FragmentVisualAcuityTestBinding.inflate(inflater, container, false);

        bottomNavigationView =  (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        iml = ContextCompat.getDrawable(getContext(),R.drawable.left);
        imr = ContextCompat.getDrawable(getContext(),R.drawable.right);
        imu = ContextCompat.getDrawable(getContext(),R.drawable.up);
        imd = ContextCompat.getDrawable(getContext(),R.drawable.down);
        imll = ContextCompat.getDrawable(getContext(),R.drawable.lowleft);
        imlh = ContextCompat.getDrawable(getContext(),R.drawable.highleft);
        imrl = ContextCompat.getDrawable(getContext(),R.drawable.lowright);
        imrh = ContextCompat.getDrawable(getContext(),R.drawable.highright);


        imgviewl = binding.leftview;
        imgviewr = binding.rightview;
        //Progress Bar
        progressBar1 = binding.processbarl;
        progressBar2 = binding.processbar2;
        density = ScreenInfo();

        chrom = binding.chrono;
        chrom.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                cnt= cnt+1;
                Random r = new Random();
                //left eye test
                if(17>cnt && cnt>2) {
                    imgviewr.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);
                    if (cnt > 2) {
                        setimage("left", iconlist.get(r.nextInt(4)), 200);

                    }
                    if (cnt > 4) setimage("left", iconlist.get(r.nextInt(4)), 200);
                    if (cnt > 6) setimage("left", iconlist.get(r.nextInt(4)), 100);
                    if (cnt > 8) setimage("left", iconlist.get(r.nextInt(4)), 100);
                    if (cnt > 10) setimage("left", iconlist.get(r.nextInt(4)), 40);
                    if (cnt > 12) setimage("left", iconlist.get(r.nextInt(4)), 40);
                    if (cnt > 14) setimage("left", iconlist.get(r.nextInt(4)), 30);
                    if (cnt > 16) setimage("left", iconlist.get(r.nextInt(4)), 30);
                }
                if(cnt>17 && cnt<32){
                    //right eye test
                    imgviewr.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    imgviewl.setVisibility(View.GONE);
                    progressBar1.setVisibility(View.GONE);

                    if ( cnt>18) setimage("right",iconlist.get(r.nextInt(4)),200);
                    if ( cnt>20) setimage("right",iconlist.get(r.nextInt(4)),200);
                    if ( cnt>22) setimage("right",iconlist.get(r.nextInt(4)),100);
                    if ( cnt>24) setimage("right",iconlist.get(r.nextInt(4)),100);
                    if ( cnt>26) setimage("right",iconlist.get(r.nextInt(4)),40);
                    if ( cnt>28) setimage("right",iconlist.get(r.nextInt(4)),40);
                    if ( cnt>30) setimage("right",iconlist.get(r.nextInt(4)),30);
                    if ( cnt>32) setimage("right",iconlist.get(r.nextInt(4)),30);
                }
                setProgress(cnt);

                Log.i("VAT test","Running:"+cnt);
            }
        });
        chrom.start();
        return binding.getRoot();
    }

    public float ScreenInfo(){
        int width = 0;
        int height = 0;
        float d;
        if (getSDK()>13) {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
            d = getResources().getDisplayMetrics().density;
        }else{
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            height = displaymetrics.heightPixels;
            width = displaymetrics.widthPixels;
            d = getResources().getDisplayMetrics().density;
        }
        return d;
    }

    public void ImageLoading(){

    }

    public void SpeechtoText(){

    }

    public int getSDK(){
        String manufacturer  = Build.MANUFACTURER;
        String model= Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        return version;
    }

    public void setimage(String side, String direct, int size) {
        if (side =="left"){
            switch (direct){
                case "left":
                    {
                        switch (size){
                            case 200:
                                {
                                    imgviewl.setScaleX(1F);
                                    imgviewl.setScaleY(1F);
                                    imgviewl.setImageDrawable(iml);
                                    break;
                                }
                            case 100:
                            {
                                imgviewl.setScaleX(0.5F);
                                imgviewl.setScaleY(0.5F);
                                imgviewl.setImageDrawable(iml);
                                break;
                            }
                            case 70:
                            {
                                imgviewl.setScaleX(0.3F);
                                imgviewl.setScaleY(0.3F);
                                imgviewl.setImageDrawable(iml);
                                break;
                            }
                            case 50:
                            {
                                imgviewl.setScaleX(0.2F);
                                imgviewl.setScaleY(0.2F);
                                imgviewl.setImageDrawable(iml);
                                break;
                            }
                            case 40:
                            {
                                imgviewl.setScaleX(0.1F);
                                imgviewl.setScaleY(0.1F);
                                imgviewl.setImageDrawable(iml);
                                break;
                            }
                            case 30:
                            {
                                imgviewl.setScaleX(0.05F);
                                imgviewl.setScaleY(0.05F);
                                imgviewl.setImageDrawable(iml);
                                break;
                            }
                            case 25:
                            {
                                imgviewl.setScaleX(0.03F);
                                imgviewl.setScaleY(0.03F);
                                imgviewl.setImageDrawable(iml);
                                break;
                            }
                            case 20:
                            {
                                imgviewl.setScaleX(0.01F);
                                imgviewl.setScaleY(0.01F);
                                imgviewl.setImageDrawable(iml);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                case "right":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewl.setScaleX(1F);
                                imgviewl.setScaleY(1F);
                                imgviewl.setImageDrawable(imr);
                                break;
                            }
                            case 100:
                            {
                                imgviewl.setScaleX(0.5F);
                                imgviewl.setScaleY(0.5F);
                                imgviewl.setImageDrawable(imr);
                                break;
                            }
                            case 70:
                            {
                                imgviewl.setScaleX(0.3F);
                                imgviewl.setScaleY(0.3F);
                                imgviewl.setImageDrawable(imr);
                                break;
                            }
                            case 50:
                            {
                                imgviewl.setScaleX(0.2F);
                                imgviewl.setScaleY(0.2F);
                                imgviewl.setImageDrawable(imr);
                                break;
                            }
                            case 40:
                            {
                                imgviewl.setScaleX(0.1F);
                                imgviewl.setScaleY(0.1F);
                                imgviewl.setImageDrawable(imr);
                                break;
                            }
                            case 30:
                            {
                                imgviewl.setScaleX(0.05F);
                                imgviewl.setScaleY(0.05F);
                                imgviewl.setImageDrawable(imr);
                                break;
                            }
                            case 25:
                            {
                                imgviewl.setScaleX(0.03F);
                                imgviewl.setScaleY(0.03F);
                                imgviewl.setImageDrawable(imr);
                                break;
                            }
                            case 20:
                            {
                                imgviewl.setScaleX(0.01F);
                                imgviewl.setScaleY(0.01F);
                                imgviewl.setImageDrawable(imr);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                case "up":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewl.setScaleX(1F);
                                imgviewl.setScaleY(1F);
                                imgviewl.setImageDrawable(imu);
                                break;
                            }
                            case 100:
                            {
                                imgviewl.setScaleX(0.5F);
                                imgviewl.setScaleY(0.5F);
                                imgviewl.setImageDrawable(imu);
                                break;
                            }
                            case 70:
                            {
                                imgviewl.setScaleX(0.3F);
                                imgviewl.setScaleY(0.3F);
                                imgviewl.setImageDrawable(imu);
                                break;
                            }
                            case 50:
                            {
                                imgviewl.setScaleX(0.2F);
                                imgviewl.setScaleY(0.2F);
                                imgviewl.setImageDrawable(imu);
                                break;
                            }
                            case 40:
                            {
                                imgviewl.setScaleX(0.1F);
                                imgviewl.setScaleY(0.1F);
                                imgviewl.setImageDrawable(imu);
                                break;
                            }
                            case 30:
                            {
                                imgviewl.setScaleX(0.05F);
                                imgviewl.setScaleY(0.05F);
                                imgviewl.setImageDrawable(imu);
                                break;
                            }
                            case 25:
                            {
                                imgviewl.setScaleX(0.03F);
                                imgviewl.setScaleY(0.03F);
                                imgviewl.setImageDrawable(imu);
                                break;
                            }
                            case 20:
                            {
                                imgviewl.setScaleX(0.01F);
                                imgviewl.setScaleY(0.01F);
                                imgviewl.setImageDrawable(imu);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                case "down":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewl.setScaleX(1F);
                                imgviewl.setScaleY(1F);
                                imgviewl.setImageDrawable(imd);
                                break;
                            }
                            case 100:
                            {
                                imgviewl.setScaleX(0.5F);
                                imgviewl.setScaleY(0.5F);
                                imgviewl.setImageDrawable(imd);
                                break;
                            }
                            case 70:
                            {
                                imgviewl.setScaleX(0.3F);
                                imgviewl.setScaleY(0.3F);
                                imgviewl.setImageDrawable(imd);
                                break;
                            }
                            case 50:
                            {
                                imgviewl.setScaleX(0.2F);
                                imgviewl.setScaleY(0.2F);
                                imgviewl.setImageDrawable(imd);
                                break;
                            }
                            case 40:
                            {
                                imgviewl.setScaleX(0.1F);
                                imgviewl.setScaleY(0.1F);
                                imgviewl.setImageDrawable(imd);
                                break;
                            }
                            case 30:
                            {
                                imgviewl.setScaleX(0.05F);
                                imgviewl.setScaleY(0.05F);
                                imgviewl.setImageDrawable(imd);
                                break;
                            }
                            case 25:
                            {
                                imgviewl.setScaleX(0.03F);
                                imgviewl.setScaleY(0.03F);
                                imgviewl.setImageDrawable(imd);
                                break;
                            }
                            case 20:
                            {
                                imgviewl.setScaleX(0.01F);
                                imgviewl.setScaleY(0.01F);
                                imgviewl.setImageDrawable(imd);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                case "lleft":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewl.setScaleX(1F);
                                imgviewl.setScaleY(1F);
                                imgviewl.setImageDrawable(imll);
                                break;
                            }
                            case 100:
                            {
                                imgviewl.setScaleX(0.5F);
                                imgviewl.setScaleY(0.5F);
                                imgviewl.setImageDrawable(imll);
                                break;
                            }
                            case 70:
                            {
                                imgviewl.setScaleX(0.3F);
                                imgviewl.setScaleY(0.3F);
                                imgviewl.setImageDrawable(imll);
                                break;
                            }
                            case 50:
                            {
                                imgviewl.setScaleX(0.2F);
                                imgviewl.setScaleY(0.2F);
                                imgviewl.setImageDrawable(imll);
                                break;
                            }
                            case 40:
                            {
                                imgviewl.setScaleX(0.1F);
                                imgviewl.setScaleY(0.1F);
                                imgviewl.setImageDrawable(imll);
                                break;
                            }
                            case 30:
                            {
                                imgviewl.setScaleX(0.05F);
                                imgviewl.setScaleY(0.05F);
                                imgviewl.setImageDrawable(imll);
                                break;
                            }
                            case 25:
                            {
                                imgviewl.setScaleX(0.03F);
                                imgviewl.setScaleY(0.03F);
                                imgviewl.setImageDrawable(imll);
                                break;
                            }
                            case 20:
                            {
                                imgviewl.setScaleX(0.01F);
                                imgviewl.setScaleY(0.01F);
                                imgviewl.setImageDrawable(imll);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                case "hleft":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewl.setScaleX(1F);
                                imgviewl.setScaleY(1F);
                                imgviewl.setImageDrawable(imlh);
                                break;
                            }
                            case 100:
                            {
                                imgviewl.setScaleX(0.5F);
                                imgviewl.setScaleY(0.5F);
                                imgviewl.setImageDrawable(imlh);
                                break;
                            }
                            case 70:
                            {
                                imgviewl.setScaleX(0.3F);
                                imgviewl.setScaleY(0.3F);
                                imgviewl.setImageDrawable(imlh);
                                break;
                            }
                            case 50:
                            {
                                imgviewl.setScaleX(0.2F);
                                imgviewl.setScaleY(0.2F);
                                imgviewl.setImageDrawable(imlh);
                                break;
                            }
                            case 40:
                            {
                                imgviewl.setScaleX(0.1F);
                                imgviewl.setScaleY(0.1F);
                                imgviewl.setImageDrawable(imlh);
                                break;
                            }
                            case 30:
                            {
                                imgviewl.setScaleX(0.05F);
                                imgviewl.setScaleY(0.05F);
                                imgviewl.setImageDrawable(imlh);
                                break;
                            }
                            case 25:
                            {
                                imgviewl.setScaleX(0.03F);
                                imgviewl.setScaleY(0.03F);
                                imgviewl.setImageDrawable(imlh);
                                break;
                            }
                            case 20:
                            {
                                imgviewl.setScaleX(0.01F);
                                imgviewl.setScaleY(0.01F);
                                imgviewl.setImageDrawable(imlh);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                case "lright":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewl.setScaleX(1F);
                                imgviewl.setScaleY(1F);
                                imgviewl.setImageDrawable(imrl);
                                break;
                            }
                            case 100:
                            {
                                imgviewl.setScaleX(0.5F);
                                imgviewl.setScaleY(0.5F);
                                imgviewl.setImageDrawable(imrl);
                                break;
                            }
                            case 70:
                            {
                                imgviewl.setScaleX(0.3F);
                                imgviewl.setScaleY(0.3F);
                                imgviewl.setImageDrawable(imrl);
                                break;
                            }
                            case 50:
                            {
                                imgviewl.setScaleX(0.2F);
                                imgviewl.setScaleY(0.2F);
                                imgviewl.setImageDrawable(imrl);
                                break;
                            }
                            case 40:
                            {
                                imgviewl.setScaleX(0.1F);
                                imgviewl.setScaleY(0.1F);
                                imgviewl.setImageDrawable(imrl);
                                break;
                            }
                            case 30:
                            {
                                imgviewl.setScaleX(0.05F);
                                imgviewl.setScaleY(0.05F);
                                imgviewl.setImageDrawable(imrl);
                                break;
                            }
                            case 25:
                            {
                                imgviewl.setScaleX(0.03F);
                                imgviewl.setScaleY(0.03F);
                                imgviewl.setImageDrawable(imrl);
                                break;
                            }
                            case 20:
                            {
                                imgviewl.setScaleX(0.01F);
                                imgviewl.setScaleY(0.01F);
                                imgviewl.setImageDrawable(imrl);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                case "hright":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewl.setScaleX(1F);
                                imgviewl.setScaleY(1F);
                                imgviewl.setImageDrawable(imrh);
                                break;
                            }
                            case 100:
                            {
                                imgviewl.setScaleX(0.5F);
                                imgviewl.setScaleY(0.5F);
                                imgviewl.setImageDrawable(imrh);
                                break;
                            }
                            case 70:
                            {
                                imgviewl.setScaleX(0.3F);
                                imgviewl.setScaleY(0.3F);
                                imgviewl.setImageDrawable(imrh);
                                break;
                            }
                            case 50:
                            {
                                imgviewl.setScaleX(0.2F);
                                imgviewl.setScaleY(0.2F);
                                imgviewl.setImageDrawable(imrh);
                                break;
                            }
                            case 40:
                            {
                                imgviewl.setScaleX(0.1F);
                                imgviewl.setScaleY(0.1F);
                                imgviewl.setImageDrawable(imrh);
                                break;
                            }
                            case 30:
                            {
                                imgviewl.setScaleX(0.05F);
                                imgviewl.setScaleY(0.05F);
                                imgviewl.setImageDrawable(imrh);
                                break;
                            }
                            case 25:
                            {
                                imgviewl.setScaleX(0.03F);
                                imgviewl.setScaleY(0.03F);
                                imgviewl.setImageDrawable(imrh);
                                break;
                            }
                            case 20:
                            {
                                imgviewl.setScaleX(0.01F);
                                imgviewl.setScaleY(0.01F);
                                imgviewl.setImageDrawable(imrh);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                default:
                    break;
            }


        }
        else{
            {
                switch (direct){
                    case "left":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewr.setScaleX(1F);
                                imgviewr.setScaleY(1F);
                                imgviewr.setImageDrawable(iml);
                                break;
                            }
                            case 100:
                            {
                                imgviewr.setScaleX(0.5F);
                                imgviewr.setScaleY(0.5F);
                                imgviewr.setImageDrawable(iml);
                                break;
                            }
                            case 70:
                            {
                                imgviewr.setScaleX(0.3F);
                                imgviewr.setScaleY(0.3F);
                                imgviewr.setImageDrawable(iml);
                                break;
                            }
                            case 50:
                            {
                                imgviewr.setScaleX(0.2F);
                                imgviewr.setScaleY(0.2F);
                                imgviewr.setImageDrawable(iml);
                                break;
                            }
                            case 40:
                            {
                                imgviewr.setScaleX(0.1F);
                                imgviewr.setScaleY(0.1F);
                                imgviewr.setImageDrawable(iml);
                                break;
                            }
                            case 30:
                            {
                                imgviewr.setScaleX(0.05F);
                                imgviewr.setScaleY(0.05F);
                                imgviewr.setImageDrawable(iml);
                                break;
                            }
                            case 25:
                            {
                                imgviewr.setScaleX(0.03F);
                                imgviewr.setScaleY(0.03F);
                                imgviewr.setImageDrawable(iml);
                                break;
                            }
                            case 20:
                            {
                                imgviewr.setScaleX(0.01F);
                                imgviewr.setScaleY(0.01F);
                                imgviewr.setImageDrawable(iml);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                    case "right":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewr.setScaleX(1F);
                                imgviewr.setScaleY(1F);
                                imgviewr.setImageDrawable(imr);
                                break;
                            }
                            case 100:
                            {
                                imgviewr.setScaleX(0.5F);
                                imgviewr.setScaleY(0.5F);
                                imgviewr.setImageDrawable(imr);
                                break;
                            }
                            case 70:
                            {
                                imgviewr.setScaleX(0.3F);
                                imgviewr.setScaleY(0.3F);
                                imgviewr.setImageDrawable(imr);
                                break;
                            }
                            case 50:
                            {
                                imgviewr.setScaleX(0.2F);
                                imgviewr.setScaleY(0.2F);
                                imgviewr.setImageDrawable(imr);
                                break;
                            }
                            case 40:
                            {
                                imgviewr.setScaleX(0.1F);
                                imgviewr.setScaleY(0.1F);
                                imgviewr.setImageDrawable(imr);
                                break;
                            }
                            case 30:
                            {
                                imgviewr.setScaleX(0.05F);
                                imgviewr.setScaleY(0.05F);
                                imgviewr.setImageDrawable(imr);
                                break;
                            }
                            case 25:
                            {
                                imgviewr.setScaleX(0.03F);
                                imgviewr.setScaleY(0.03F);
                                imgviewr.setImageDrawable(imr);
                                break;
                            }
                            case 20:
                            {
                                imgviewr.setScaleX(0.01F);
                                imgviewr.setScaleY(0.01F);
                                imgviewr.setImageDrawable(imr);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                    case "up":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewr.setScaleX(1F);
                                imgviewr.setScaleY(1F);
                                imgviewr.setImageDrawable(imu);
                                break;
                            }
                            case 100:
                            {
                                imgviewr.setScaleX(0.5F);
                                imgviewr.setScaleY(0.5F);
                                imgviewr.setImageDrawable(imu);
                                break;
                            }
                            case 70:
                            {
                                imgviewr.setScaleX(0.3F);
                                imgviewr.setScaleY(0.3F);
                                imgviewr.setImageDrawable(imu);
                                break;
                            }
                            case 50:
                            {
                                imgviewr.setScaleX(0.2F);
                                imgviewr.setScaleY(0.2F);
                                imgviewr.setImageDrawable(imu);
                                break;
                            }
                            case 40:
                            {
                                imgviewr.setScaleX(0.1F);
                                imgviewr.setScaleY(0.1F);
                                imgviewr.setImageDrawable(imu);
                                break;
                            }
                            case 30:
                            {
                                imgviewr.setScaleX(0.05F);
                                imgviewr.setScaleY(0.05F);
                                imgviewr.setImageDrawable(imu);
                                break;
                            }
                            case 25:
                            {
                                imgviewr.setScaleX(0.03F);
                                imgviewr.setScaleY(0.03F);
                                imgviewr.setImageDrawable(imu);
                                break;
                            }
                            case 20:
                            {
                                imgviewr.setScaleX(0.01F);
                                imgviewr.setScaleY(0.01F);
                                imgviewr.setImageDrawable(imu);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                    case "down":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewr.setScaleX(1F);
                                imgviewr.setScaleY(1F);
                                imgviewr.setImageDrawable(imd);
                                break;
                            }
                            case 100:
                            {
                                imgviewr.setScaleX(0.5F);
                                imgviewr.setScaleY(0.5F);
                                imgviewr.setImageDrawable(imd);
                                break;
                            }
                            case 70:
                            {
                                imgviewr.setScaleX(0.3F);
                                imgviewr.setScaleY(0.3F);
                                imgviewr.setImageDrawable(imd);
                                break;
                            }
                            case 50:
                            {
                                imgviewr.setScaleX(0.2F);
                                imgviewr.setScaleY(0.2F);
                                imgviewr.setImageDrawable(imd);
                                break;
                            }
                            case 40:
                            {
                                imgviewr.setScaleX(0.1F);
                                imgviewr.setScaleY(0.1F);
                                imgviewr.setImageDrawable(imd);
                                break;
                            }
                            case 30:
                            {
                                imgviewr.setScaleX(0.05F);
                                imgviewr.setScaleY(0.05F);
                                imgviewr.setImageDrawable(imd);
                                break;
                            }
                            case 25:
                            {
                                imgviewr.setScaleX(0.03F);
                                imgviewr.setScaleY(0.03F);
                                imgviewr.setImageDrawable(imd);
                                break;
                            }
                            case 20:
                            {
                                imgviewr.setScaleX(0.01F);
                                imgviewr.setScaleY(0.01F);
                                imgviewr.setImageDrawable(imd);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                    case "lleft":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewr.setScaleX(1F);
                                imgviewr.setScaleY(1F);
                                imgviewr.setImageDrawable(imll);
                                break;
                            }
                            case 100:
                            {
                                imgviewr.setScaleX(0.5F);
                                imgviewr.setScaleY(0.5F);
                                imgviewr.setImageDrawable(imll);
                                break;
                            }
                            case 70:
                            {
                                imgviewr.setScaleX(0.3F);
                                imgviewr.setScaleY(0.3F);
                                imgviewr.setImageDrawable(imll);
                                break;
                            }
                            case 50:
                            {
                                imgviewr.setScaleX(0.2F);
                                imgviewr.setScaleY(0.2F);
                                imgviewr.setImageDrawable(imll);
                                break;
                            }
                            case 40:
                            {
                                imgviewr.setScaleX(0.1F);
                                imgviewr.setScaleY(0.1F);
                                imgviewr.setImageDrawable(imll);
                                break;
                            }
                            case 30:
                            {
                                imgviewr.setScaleX(0.05F);
                                imgviewr.setScaleY(0.05F);
                                imgviewr.setImageDrawable(imll);
                                break;
                            }
                            case 25:
                            {
                                imgviewr.setScaleX(0.03F);
                                imgviewr.setScaleY(0.03F);
                                imgviewr.setImageDrawable(imll);
                                break;
                            }
                            case 20:
                            {
                                imgviewr.setScaleX(0.01F);
                                imgviewr.setScaleY(0.01F);
                                imgviewr.setImageDrawable(imll);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                    case "hleft":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewr.setScaleX(1F);
                                imgviewr.setScaleY(1F);
                                imgviewr.setImageDrawable(imlh);
                                break;
                            }
                            case 100:
                            {
                                imgviewr.setScaleX(0.5F);
                                imgviewr.setScaleY(0.5F);
                                imgviewr.setImageDrawable(imlh);
                                break;
                            }
                            case 70:
                            {
                                imgviewr.setScaleX(0.3F);
                                imgviewr.setScaleY(0.3F);
                                imgviewr.setImageDrawable(imlh);
                                break;
                            }
                            case 50:
                            {
                                imgviewr.setScaleX(0.2F);
                                imgviewr.setScaleY(0.2F);
                                imgviewr.setImageDrawable(imlh);
                                break;
                            }
                            case 40:
                            {
                                imgviewr.setScaleX(0.1F);
                                imgviewr.setScaleY(0.1F);
                                imgviewr.setImageDrawable(imlh);
                                break;
                            }
                            case 30:
                            {
                                imgviewr.setScaleX(0.05F);
                                imgviewr.setScaleY(0.05F);
                                imgviewr.setImageDrawable(imlh);
                                break;
                            }
                            case 25:
                            {
                                imgviewr.setScaleX(0.03F);
                                imgviewr.setScaleY(0.03F);
                                imgviewr.setImageDrawable(imlh);
                                break;
                            }
                            case 20:
                            {
                                imgviewr.setScaleX(0.01F);
                                imgviewr.setScaleY(0.01F);
                                imgviewr.setImageDrawable(imlh);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                    case "lright":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewr.setScaleX(1F);
                                imgviewr.setScaleY(1F);
                                imgviewr.setImageDrawable(imrl);
                                break;
                            }
                            case 100:
                            {
                                imgviewr.setScaleX(0.5F);
                                imgviewr.setScaleY(0.5F);
                                imgviewr.setImageDrawable(imrl);
                                break;
                            }
                            case 70:
                            {
                                imgviewr.setScaleX(0.3F);
                                imgviewr.setScaleY(0.3F);
                                imgviewr.setImageDrawable(imrl);
                                break;
                            }
                            case 50:
                            {
                                imgviewr.setScaleX(0.2F);
                                imgviewr.setScaleY(0.2F);
                                imgviewr.setImageDrawable(imrl);
                                break;
                            }
                            case 40:
                            {
                                imgviewr.setScaleX(0.1F);
                                imgviewr.setScaleY(0.1F);
                                imgviewr.setImageDrawable(imrl);
                                break;
                            }
                            case 30:
                            {
                                imgviewr.setScaleX(0.05F);
                                imgviewr.setScaleY(0.05F);
                                imgviewr.setImageDrawable(imrl);
                                break;
                            }
                            case 25:
                            {
                                imgviewr.setScaleX(0.03F);
                                imgviewr.setScaleY(0.03F);
                                imgviewr.setImageDrawable(imrl);
                                break;
                            }
                            case 20:
                            {
                                imgviewr.setScaleX(0.01F);
                                imgviewr.setScaleY(0.01F);
                                imgviewr.setImageDrawable(imrl);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                    case "hright":
                    {
                        switch (size){
                            case 200:
                            {
                                imgviewr.setScaleX(1F);
                                imgviewr.setScaleY(1F);
                                imgviewr.setImageDrawable(imrh);
                                break;
                            }
                            case 100:
                            {
                                imgviewr.setScaleX(0.5F);
                                imgviewr.setScaleY(0.5F);
                                imgviewr.setImageDrawable(imrh);
                                break;
                            }
                            case 70:
                            {
                                imgviewr.setScaleX(0.3F);
                                imgviewr.setScaleY(0.3F);
                                imgviewr.setImageDrawable(imrh);
                                break;
                            }
                            case 50:
                            {
                                imgviewr.setScaleX(0.2F);
                                imgviewr.setScaleY(0.2F);
                                imgviewr.setImageDrawable(imrh);
                                break;
                            }
                            case 40:
                            {
                                imgviewr.setScaleX(0.1F);
                                imgviewr.setScaleY(0.1F);
                                imgviewr.setImageDrawable(imrh);
                                break;
                            }
                            case 30:
                            {
                                imgviewr.setScaleX(0.05F);
                                imgviewr.setScaleY(0.05F);
                                imgviewr.setImageDrawable(imrh);
                                break;
                            }
                            case 25:
                            {
                                imgviewr.setScaleX(0.03F);
                                imgviewr.setScaleY(0.03F);
                                imgviewr.setImageDrawable(imrh);
                                break;
                            }
                            case 20:
                            {
                                imgviewr.setScaleX(0.01F);
                                imgviewr.setScaleY(0.01F);
                                imgviewr.setImageDrawable(imrh);
                                break;
                            }
                            default: break;

                        }
                        break;
                    }
                    default:
                        break;
                }
            }

        }

    }
    void setProgress(int cnt){
        progressBar1.setMax(2);
        progressBar2.setMax(2);
        progressBar1.setProgress(cnt%2);
        progressBar2.setProgress(cnt%2);

    }
}
