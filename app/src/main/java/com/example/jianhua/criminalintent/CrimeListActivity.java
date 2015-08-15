package com.example.jianhua.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by jianhua on 2015/7/31.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
