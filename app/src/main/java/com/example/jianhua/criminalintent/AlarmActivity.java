package com.example.jianhua.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import java.io.IOException;

/**
 * Created by jianhua on 2015/8/5.
 */
public class AlarmActivity extends Activity {

    private  String title=null;
    MediaPlayer alarmMusic;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url=null;
        //显示对话框
        title = getIntent().getStringExtra(CrimeFragment.EXTRA_CRIME_TITLE);
        boolean isselect =getIntent().getBooleanExtra(CrimeFragment.EXTRA_IS_SELECT,false);
        if(isselect)
            url=getIntent().getStringExtra(CrimeFragment.EXTRA_URL_STR);

       // System.out.println(url+"kkk"+isselect);
        if(isselect){
            alarmMusic =new MediaPlayer();
            try {
                alarmMusic.setDataSource(this,Uri.parse(url));
                System.out.println(url+"kkk"+isselect);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR");
            }
        }
         else{
            alarmMusic = MediaPlayer.create(this, R.raw.alarm);
        }
        alarmMusic.setLooping(true);
        try {
            alarmMusic.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        alarmMusic.start();
        new AlertDialog.Builder(AlarmActivity.this).
                setTitle("Now").//设置标题
                setMessage("It's time to do"+title+"!").//设置内容
                setPositiveButton("知道了", new DialogInterface.OnClickListener() {//设置按钮
            public void onClick(DialogInterface dialog, int which) {
                alarmMusic.stop();
                AlarmActivity.this.finish();//关闭Activity
            }
        }).create().show();
    }
}
