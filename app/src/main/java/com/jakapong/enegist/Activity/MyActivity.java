package com.jakapong.enegist.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.jakapong.enegist.Adapter.SectionsPagerAdapter;
import com.jakapong.enegist.Dataloader.ExaminationLoader;
import com.jakapong.enegist.Dataloader.ModelStatusListener;
import com.jakapong.enegist.Entries.ExaminationData;
import com.jakapong.enegist.Entries.ExaminationEntry;
import com.jakapong.enegist.Fragment.ExaminationFragment;
import com.jakapong.enegist.R;
import com.jakapong.enegist.utils.SessionManager;


public class MyActivity extends Activity implements ModelStatusListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int mFocusedPage;
    private int number;
    private ExaminationLoader examinationLoader;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ArrayList<ExaminationEntry> arrItems = new ArrayList<ExaminationEntry>();
    protected SessionManager session;
    private  AlertDialog.Builder adb;
    private int  PageFogus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);  // Actual Layout Name
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.custom_title_bar);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        adb = new AlertDialog.Builder(this);

        // create session and set start page
        session = new SessionManager(getApplicationContext());
        session.createPageSession(1);

        // Load ExaminationLoader .
        examinationLoader = new ExaminationLoader(MyActivity.this);
        examinationLoader.setModelStatusListener(this);
        examinationLoader.load();

    }

    @Override
    public void onLoadDataSuccess(String key, Object ts) {

        PageFogus  = session.pref.getInt(session.PAGE_FOGUS, 1);
        arrItems.addAll((ArrayList<ExaminationEntry>) ts);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getFragmentManager(), arrItems ,mViewPager);
         // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(PageFogus);
        mViewPager.setOnPageChangeListener(mSectionsPagerAdapter);
        mSectionsPagerAdapter.autosilde(PageFogus+1);


    }


    @Override
    public void onLoadDataFailed(String key) {
        Log.e("onLoadDataFailed", "onLoadDataFailed");
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        adb.setTitle(getString(R.string.exitAlertTitle));
        adb.setMessage(getString(R.string.exitAlertText));
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                session.deleteAllSession();
                finish();
            }
        });
        adb.show();
     }

    public void onResume(){
        if(arrItems.size() > 0){
            PageFogus  = session.pref.getInt(session.PAGE_FOGUS, 1);
            mSectionsPagerAdapter.autosilde(PageFogus+1);
        }

        super.onResume();

    };

    public void onPause(){

        mSectionsPagerAdapter.stopAutosilde();
        super.onPause();


    };
    public void onStop(){

        super.onStop();


    };
}