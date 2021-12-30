package com.example.presentation;

import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.presentation.databinding.ActivityMainBinding;
import com.example.presentation.fragments.CameraViewFragment;
import com.example.presentation.fragments.LoginFragment;
import com.example.presentation.fragments.MainFragment;
import com.example.presentation.fragments.UserProfileFragment;
import com.example.presentation.fragments.VisualAcuityFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Fragment mFragment;
    private UserProfileFragment fragment;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                binding=null;

                switch (item.getItemId()){
                    case R.id.page_1:{
                        displayView(1);
                        break;
                    }
                    case R.id.page_2:{
                        displayView(2);

                        break;

                    }
                    case R.id.page_3:{
                        displayView(3);
                        break;

                    }
                    case R.id.page_4:{
                        displayView(4);
                        break;

                    }
                    default: {
                        displayView(1);
                        break;
                    }

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            CameraViewActivity cameraViewActivity = new CameraViewActivity();
//            FragmentManager manager = getFragmentManager();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.nav_host_fragment_content_main, CameraViewActivity.newInstance());
//            transaction.addToBackStack(null);
//            transaction.commit();
            displayView(1);
//            Log.i("MainActivity:","User Setting");

            return true;
        }

        if (id == R.id.action_cameraview) {
            displayView(2);
//            CameraViewActivity cameraViewActivity = new CameraViewActivity();
//            FragmentManager manager = getFragmentManager();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.nav_host_fragment_content_main, CameraViewActivity.newInstance());
//            transaction.addToBackStack(null);
//            transaction.commit();
//            displayView(1);
//            Log.i("MainActivity:","User Setting");
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void displayView(int position) {
        mFragment = null;
        switch (position){
            case 1:
                mFragment = UserProfileFragment.newInstance();
                break;
            case 2:
                mFragment = VisualAcuityFragment.newInstance();
                break;
            case 3:
                mFragment = CameraViewFragment.newInstance();
                break;
            case 4:
                mFragment = CameraViewFragment.newInstance();
                break;
            default:
                mFragment = MainFragment.newInstance();
                break;
        }

        if (mFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .add(R.id.nav_host_fragment_content_main, mFragment).commit();
                androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, mFragment);
                fragmentTransaction.commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    protected void onDestroy() {
        binding=null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        binding=null;
        super.onBackPressed();
    }
}