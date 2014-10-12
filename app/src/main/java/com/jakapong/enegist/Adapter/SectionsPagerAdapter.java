package com.jakapong.enegist.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakapong.enegist.Activity.MyActivity;
import com.jakapong.enegist.Entries.ExaminationData;
import com.jakapong.enegist.Entries.ExaminationEntry;
import com.jakapong.enegist.Fragment.ExaminationFragment;
import com.jakapong.enegist.R;
import com.jakapong.enegist.utils.Constants;
import com.jakapong.enegist.utils.SessionManager;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by jakapong on 10/11/14 AD.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    protected Context mContext;
    public int NumberOfData;
    private ArrayList<ExaminationEntry> arrItems = new ArrayList<ExaminationEntry>();
    protected SessionManager session;
    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private ViewPager mViewPager;
    private Handler handler = new Handler();
    private Runnable animateViewPager  ;

    public SectionsPagerAdapter(Context context,FragmentManager fm,ArrayList<ExaminationEntry> data ,ViewPager mViewPager) {
        super(fm);
        this.mContext = context;
        this.arrItems = data;
        this.setNumberOfData(this.arrItems.size());
        this.mViewPager = mViewPager;
    }

    public void  autosilde(final int index ){

        this.stopAutosilde();
        handler.postDelayed(animateViewPager = new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                mViewPager.setCurrentItem(index);
               // Toast.makeText(getApplicationContext(), "Page " + index, Toast.LENGTH_SHORT).show();

            }
        }, ANIM_VIEWPAGER_DELAY);

    }

    public  void  stopAutosilde(){
        handler.removeCallbacks(animateViewPager);

    }

    @Override
    public Fragment getItem(int position) {
        session = new SessionManager(this.mContext);
        ExaminationFragment fragment = new ExaminationFragment();
        Bundle bundle = new Bundle();
        int index = position ;
        if(position == 0){
            index = this.arrItems.size()-1;
        } else if(position == this.arrItems.size() + 1) {
            index =  0;
        } else {
            index = position-1;
        }
        // check start page 1
        boolean aBoolean = session.pref.getBoolean(session.FIRST_START,false);
        if(aBoolean){
            bundle.putString(Constants.ExaminationFOGUS, String.valueOf(1));
            //this.autosilde(2);
        } else {
            bundle.putString(Constants.ExaminationFOGUS, String.valueOf(index));
            //this.autosilde(index + 1);
        }

        bundle.putString(Constants.ExaminationTEXT, this.arrItems.get(index).getText());
        bundle.putString(Constants.ExaminationURL, this.arrItems.get(index).getUrl());
        bundle.putString(Constants.ExaminationLINK, this.arrItems.get(index).getLink());
        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public int getCount() {
       return this.getNumberOfData()+2   ;

    }
    @Override
    public CharSequence getPageTitle(int position) {

        return "Page " + position  ;

    }

    public int getNumberOfData() {
        return NumberOfData;
    }

    public void setNumberOfData(int numberOfData) {
        NumberOfData = numberOfData;
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(final int position) {

        int index = position + 1;
        if (position == 0) {
            index = this.getNumberOfData();
        } else if (position == this.getNumberOfData() + 1) {
            index = 1;
        } else {

        }

        this.autosilde(index);

    }
    @Override
    public void onPageScrollStateChanged(int state) {

        if (state == ViewPager.SCROLL_STATE_IDLE) {

            int curr = mViewPager.getCurrentItem();
            int lastReal = mViewPager.getAdapter().getCount() - 2;
            if (curr == 0) {
                mViewPager.setCurrentItem(lastReal, false);
            } else if (curr > lastReal) {
                mViewPager.setCurrentItem(1, false);
            }
        }
    }

}