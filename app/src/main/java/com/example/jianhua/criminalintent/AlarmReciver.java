package com.example.jianhua.criminalintent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciver extends BroadcastReceiver {

    public AlarmReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context,AlarmActivity.class);
        boolean  isslect= intent.getBooleanExtra(CrimeFragment.EXTRA_IS_SELECT,false);
        if(isslect)
            i.putExtra(CrimeFragment.EXTRA_URL_STR,intent.getStringExtra(CrimeFragment.EXTRA_URL_STR));
        System.out.println("select val"+isslect);

        i.putExtra(CrimeFragment.EXTRA_IS_SELECT,isslect);
        i.putExtra(CrimeFragment.EXTRA_CRIME_TITLE,intent.getStringExtra(CrimeFragment.EXTRA_CRIME_TITLE));

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
