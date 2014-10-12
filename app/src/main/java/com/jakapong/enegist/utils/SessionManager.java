package com.jakapong.enegist.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import java.security.PublicKey;
import java.util.HashMap;

/**
 * Created by jakapong on 10/12/14 AD.
 */
public class SessionManager {

     //Share Preferences
    public static SharedPreferences pref;

    //Context
    protected Context context;

    //Shared pref mode
    protected int PRIVATE_MODE = 0;

    //Editor for Share Preferences
    protected SharedPreferences.Editor editor;

    //Shared pref name
    protected static String MY_PREF_NAME = "Examination";

    //Shared PAGE_FOGUS ID
    public static final String PAGE_FOGUS = "page";

    //Shared FIRST_START FirstStart
    public static final String FIRST_START = "FirstStart";

    protected int page = 0;


    //Constructor
    public SessionManager(Context context){

        try {

            this.context = context;
            MY_PREF_NAME = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
            pref = context.getSharedPreferences(MY_PREF_NAME,PRIVATE_MODE);


        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Create PAGE_FOGUS session
     * @param page PAGE_FOGUS
      *
     * */

    public void createPageSession(int page ){
        deleteAllSession();
        editor = pref.edit();
        editor.putBoolean(FIRST_START, true);
        editor.putInt(PAGE_FOGUS,  page );
        editor.commit();

    }

    public void UpdatePageSession(int page){
        editor = pref.edit();
        editor.putBoolean(FIRST_START, false);
        editor.putInt(PAGE_FOGUS,  page );
        editor.commit();

    }

    public void changeonPause(boolean onPause){
        editor = pref.edit();
        editor.putBoolean(FIRST_START, onPause);
        editor.commit();

    }
    /**

    /**
     * Remove All Session from device
     */
    public void deleteAllSession(){

        editor = pref.edit();
        editor.clear();
        editor.commit();

    }

}
