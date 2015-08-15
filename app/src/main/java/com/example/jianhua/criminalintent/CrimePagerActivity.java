package com.example.jianhua.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by jianhua on 2015/8/2.
 */
public class CrimePagerActivity extends FragmentActivity{
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mCrimes = CrimeLab.get(this).getmCrimes();
        FragmentManager fm = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                //notifyDataSetChanged();
                return  CrimeFragment.newInstance(crime.getmId());
            }

            @Override
            public int getCount() {
                //notifyDataSetChanged();
                return mCrimes.size();

            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Crime crime = mCrimes.get(position);
                if(crime.getmTitle()!=null){
                    setTitle(crime.getmTitle());
                }
                mViewPager.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for(int i =0 ;i<mCrimes.size();i++){
            if(mCrimes.get(i).getmId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }


        }
      mViewPager.getAdapter().notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewPager.getAdapter().notifyDataSetChanged();
    }
}
