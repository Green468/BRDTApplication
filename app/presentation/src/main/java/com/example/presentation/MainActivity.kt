package com.example.presentation

import android.content.ContentValues
import android.content.Context
import com.example.presentation.fragments.MainFragment.Companion.newInstance
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.presentation.fragments.UserProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.os.Bundle
import com.example.presentation.R
import com.google.android.material.navigation.NavigationBarView
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.PorterDuff
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.presentation.fragments.VisualAcuityFragment
import com.example.presentation.fragments.ScheduleFragment
import com.example.presentation.fragments.LoginFragment
import com.example.presentation.fragments.MainFragment
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    var islogin = 0
    @JvmField
    var videolist: String? = null
    private val mContext: Context? = null
    var mToolbar: Toolbar? = null
    private val appBarConfiguration: AppBarConfiguration? = null
    private var mFragment: Fragment? = null
    private val fragment: UserProfileFragment? = null
    var bottomNavigationView: BottomNavigationView? = null
    val VIDEO_LIST_KEY:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        createActionBar()

//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView!!.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    displayView(6)
                }
                R.id.page_2 -> {
                    displayView(2)
                }
                R.id.page_3 -> {
                    displayView(3)
                }
                else -> {
                    displayView(1)
                }
            }
            false
        })
        bottomNavigationView!!.setVisibility(View.INVISIBLE)

        // Receive mess from other activity
        var tag: String? = ""
        tag = intent.getStringExtra("fragment");
        when (tag) {
            "6" -> {
                displayView(6)
            }
            "4" ->{
                if (savedInstanceState == null) {
                    displayView(4)
                }
            }
            else -> displayView(6)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState, outPersistentState)

        outState?.run {
            putString(VIDEO_LIST_KEY, videolist)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        videolist = savedInstanceState?.getString(VIDEO_LIST_KEY)
    }

    override fun onStop() {

        super.onStop()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            displayView(1)
            Log.i("MainActivity:", "User Setting")
            return true
        }
        if (id == android.R.id.home) {
            displayView(5)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createActionBar() {
        mToolbar = findViewById<View>(R.id.toolbar_actionbar) as Toolbar
        mToolbar!!.setTitleTextColor(Color.WHITE)
        setSupportActionBar(mToolbar)
        val icon = resources.getDrawable(R.drawable.homeicon)
        val bitmap = (icon as BitmapDrawable).bitmap
        val newicon: Drawable =
            BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 80, 80, true))
        newicon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(newicon)
        mToolbar!!.visibility = View.INVISIBLE
    }

    private fun clearBackStack() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun backAsToolbar() {
        supportFragmentManager.popBackStack()
    }

    //    @Override
    //    public boolean onSupportNavigateUp() {
    //        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    //        return NavigationUI.navigateUp(navController, appBarConfiguration)
    //                || super.onSupportNavigateUp();
    //    }
    fun displayView(position: Int) {
        mFragment = null
        mFragment = when (position) {
            1 -> UserProfileFragment.newInstance()
            2 -> VisualAcuityFragment.newInstance()
            3 -> ScheduleFragment.newInstance()
            4 -> LoginFragment.newInstance()
            else -> MainFragment.newInstance()
        }
        if (mFragment != null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.nav_host_fragment_content_main, mFragment!!)
            fragmentTransaction.commit()
        } else {
            Log.e("MainActivity", "Error in creating fragment")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (mFragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().remove(mFragment!!).commit()
        }
        if (supportFragmentManager.backStackEntryCount < 3) {
            finish()
        } else {
            super.onBackPressed()
        }
        super.onBackPressed()
    }

    companion object {

        const val DISPLAY_CONNECT_SCREEN = 1
        const val DISPLAY_INTRO_SCREEN = 2
        const val DISPLAY_INSTOCK_SCREEN = 3
        const val DISPLAY_OUTSTOCK_SCREEN = 4
        const val DISPLAY_RETURN_SCREEN = 5
        const val DISPLAY_PITEST_SCREEN = 6
    }
}