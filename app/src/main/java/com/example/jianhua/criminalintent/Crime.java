package com.example.jianhua.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jianhua on 2015/7/31.
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private  boolean mSolved;
    public  static int count=0;
    private String musicname="系统默认";
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_MUSIC = "music";
    private int alarmid;

    public Crime(){
        mId = UUID.randomUUID();
        mDate=new Date();
        alarmid =count++;
        if(count==Integer.MAX_VALUE)
            count=0;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public Date getmDate() {
        return mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public String toString(){
        return  mTitle;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID,mId.toString());
        json.put(JSON_TITLE,mTitle);
        json.put(JSON_SOLVED,mSolved);
        json.put(JSON_DATE,mDate.getTime());
        json.put(JSON_MUSIC,musicname);
        //json.put(JSON_DATE,1);
        return json;
    }
    public  Crime(JSONObject json) throws JSONException{
        mId =  UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        mSolved =json.getBoolean(JSON_SOLVED);
        mDate =new Date(Long.parseLong(json.getString(JSON_DATE)));
        if(json.has(JSON_MUSIC))
        musicname=json.getString(JSON_MUSIC);

    }

    public int getAlarmid() {
        return alarmid;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }

    public String getMusicname() {
        return musicname;
    }

}
