package com.example.jianhua.criminalintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by jianhua on 2015/7/31.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;

    private static final  String TAG="CrimeLab";
    private static final  String  FILENAME = "crimes.json";
    private  CriminalIntentJSONSerializer mSerializer;


    private CrimeLab(Context appContext){
        mAppContext = appContext;
        mSerializer= new CriminalIntentJSONSerializer(mAppContext,FILENAME);
        try{
            mCrimes=mSerializer.loadCrimes();
            Log.d(TAG,"success");
        }catch (Exception e){
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG,"Error loading crimes:",e);
        }

    }
    public  static CrimeLab get(Context c){
         if(sCrimeLab==null){
             sCrimeLab =new CrimeLab(c.getApplicationContext());
         }
        return sCrimeLab;
    }

    public  ArrayList<Crime> getmCrimes(){
        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for(Crime c : mCrimes )
            if(c.getmId().equals(id))
                return c;
        return null;
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }
    public boolean  saveCrimes(){
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG,"crimes saved to file");
            return true;
        }catch (Exception e){
            Log.e(TAG,"Error saving crimes:",e);
            return false;
        }
    }
    public  void  deleteCrime(Crime c){
         mCrimes.remove(c);
    }


}
