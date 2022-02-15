package com.example.presentation.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.domain.entities.LoginResponse;
import com.example.domain.entities.Question;
import com.example.domain.entities.StackOverflowAPI;
import com.example.domain.entities.StackOverflowQuestions;
import com.example.domain.entities.Users;
import com.example.domain.entities.Video;
import com.example.domain.entities.VideoList;
import com.example.domain.network.Network;
import com.example.presentation.MainActivity;
import com.example.presentation.R;
import com.example.presentation.databinding.FragmentLoginBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    private Network network;
    public int issucess = 0;
    public String ssid;
    public List<String> urllist = new ArrayList<String>();
    BottomNavigationView bottomNavigationView;
    EditText txid,txpass;
    public Toolbar mToolbar;
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bottomNavigationView =  (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        mToolbar.setVisibility(View.INVISIBLE);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        txid = binding.txtid;
        txpass = binding.txtpass;
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Khởi tạo Retrofit để gán API ENDPOINT (Domain URL) cho Retrofit 2.0
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://iotd.terasoftvn.com")
                        // Sử dụng GSON cho việc parse và maps JSON data tới Object
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Khởi tạo các cuộc gọi cho Retrofit 2.0
                StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);

                Call<LoginResponse> call = stackOverflowAPI.login(txid.toString(),txpass.toString());

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.i("Response:",String.valueOf(response.code()));
                            return;
                        }else
                        {
                              Log.i("Response:",response.toString());
                            Toast.makeText(getActivity().getApplicationContext(), "Successfully Connected",Toast.LENGTH_SHORT);
                            issucess = 1;
                            ssid = response.body().getSsid();
//                            ((MainActivity) getActivity()).displayView(6);

                            Call<ResponseBody> call1 = stackOverflowAPI.getVideolist("user0","0", "10");
                            call1.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    Log.i("Response code:",response.toString());
                                    if(response.isSuccessful()) {
                                        try {
                                            String sbody = (response.body().string()); // keep this line before working with JSON body
                                            Log.i("Response body:", sbody);

                                            //Json mess forwarding
                                            Bundle bundle = new Bundle();
                                            bundle.putString("videos",sbody);
                                            MainFragment mainFragment = new MainFragment();
                                            mainFragment.setArguments(bundle);

                                            ((MainActivity) getActivity()).videolist = sbody;

                                            bottomNavigationView =  (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
                                            bottomNavigationView.setVisibility(View.VISIBLE);
                                            ((MainActivity) getActivity()).displayView(6);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            Log.e("Response body:", e.toString());

                                        }

                                    }
                                    else{

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Loading Video Fail",Toast.LENGTH_SHORT);
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), "Connecting Fail",Toast.LENGTH_SHORT);
                    }
                });
//
                if(issucess==1){

                }

//                // Khởi tạo các cuộc gọi cho Retrofit 2.0
//                StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);
//
//                Call<Users> call1 = stackOverflowAPI.isValidUser("abc123");
                // Cuộc gọi bất đồng bọ (chạy dưới background)
//                call1.enqueue(new Callback<StackOverflowQuestions>() {
//                    @Override
//                    public void onResponse(Call<StackOverflowQuestions> call, Response<StackOverflowQuestions> response) {
////                        if (!response.isSuccessful()) {
////                            Log.i("Response:",String.valueOf(response.code()));
////                            return;
////                        }
//
//                        Log.i("Response:",response.toString());
//                        NavHostFragment.findNavController(LoginFragment.this)
//                                .navigate(R.id.action_LoginFragment_to_MainFragment);
//                    }
//
//                    @Override
//                    public void onFailure(Call<StackOverflowQuestions> call, Throwable t) {
//
//                    }
//                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
