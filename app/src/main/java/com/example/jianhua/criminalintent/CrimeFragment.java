package com.example.jianhua.criminalintent;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;



/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeFragment extends Fragment implements  TimePickerDialog.OnTimeSetListener{


    private static final int REQUEST_CODE_PICK_RINGTONE = 1;
    private String mRingtoneUri = null;

    private String Uristring=null;

    public static   AlarmManager alarmManager= null;
    Calendar cal=Calendar.getInstance();

    private  Date TDate=null;
    public   static final  String EXTRA_CRIME_ID="com.example.jianhua.criminalintent.crime_id";
    public   static final  String EXTRA_IS_SELECT="com.example.jianhua.criminalintent.select";
    public   static final  String EXTRA_URL_STR="com.example.jianhua.criminalintent.Musin_urlstr";
    public   static final  String EXTRA_CRIME_TITLE="com.example.jianhua.criminalintent.crime_title";
    private  Crime mcrime;
    private EditText mTitleField;
    private Button mDateButton,finishButton,musinSetBtn;
    private CheckBox mSolvedCheckBox;
    private  static final String DIALOG_DATE="date";
    private static final int REQUEST_DATE = 0;
    private  int Chour,Cminite;
    private  boolean isSelect= false;



    public CrimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_crime,container,false);




        musinSetBtn = (Button)v.findViewById(R.id.btnmusicset);
        musinSetBtn.setText(mcrime.getMusicname());
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mcrime.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 mcrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        musinSetBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doPickRingtone();
            }
        });




        mDateButton=(Button)v.findViewById(R.id.crime_date);
       // mDateButton.setText(mcrime.getmDate().toString());
        mDateButton.setText(new SimpleDateFormat("HH:mm:ss").format(mcrime.getmDate()));
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                now.setTime(mcrime.getmDate());
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        CrimeFragment.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setThemeDark(false);
                tpd.vibrate(false);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");


            }
        });


        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mcrime.ismSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mcrime.setmSolved(isChecked);
            }
        });
        finishButton = (Button) v.findViewById(R.id.btnfinish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KKKK",mTitleField.getText().toString());
                 if(mTitleField.getText().toString().equals("")){
                     Toast toast =Toast.makeText(getActivity(),"what do you wanna do ?",Toast.LENGTH_LONG);
                     toast.show();
                     Log.d("KKKK","ooo");
                 }
                else {

                     if (TDate != null) {
                         Calendar c = Calendar.getInstance();
                         c.setTimeInMillis(System.currentTimeMillis());
                         c.setTime(TDate);
                         mcrime.setmDate(TDate);
                         c.set(Calendar.SECOND, 0);
                         Intent intent = new Intent(getActivity(), AlarmReciver.class);
                         intent.putExtra(EXTRA_CRIME_TITLE, mcrime.getmTitle());
                         intent.putExtra(EXTRA_IS_SELECT,isSelect);
                         if(Uristring!=null)
                         intent.putExtra(EXTRA_URL_STR,Uristring);
                         PendingIntent pi = PendingIntent.getBroadcast(getActivity(), mcrime.getAlarmid(), intent, 0);
                         alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                         Log.d("KKKK", "" + mcrime.getAlarmid());
                     }

                     getActivity().finish();
                 }
            }
        });


        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);

        mcrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    public  static CrimeFragment newInstance(UUID crimeId){
        Bundle args= new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment =new CrimeFragment();
        fragment.setArguments(args);
        return  fragment;

    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity())!=null){
                NavUtils.navigateUpFromSameTask(getActivity());
            }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public  void onPause(){
        super.onPause();
        if(mTitleField.getText().toString().equals("")){
            CrimeLab.get(getActivity()).deleteCrime(mcrime);
        }
        CrimeLab.get(getActivity()).saveCrimes();
    }

    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        Chour=hourOfDay;
        Cminite=minute;
        Calendar temp =Calendar.getInstance();
        TDate =  new GregorianCalendar(temp.get(Calendar.YEAR),temp.get(Calendar.MONTH),temp.get(Calendar.DAY_OF_MONTH),hourOfDay,minute).getTime();
        mDateButton.setText(new SimpleDateFormat("HH:mm").format(TDate));

    }

    private void doPickRingtone() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);

        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);

        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
                RingtoneManager.TYPE_RINGTONE);

        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);

        Uri ringtoneUri;
        if (mRingtoneUri != null) {
            ringtoneUri = Uri.parse(mRingtoneUri);
        } else {

            ringtoneUri = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
                ringtoneUri);
        startActivityForResult(intent, REQUEST_CODE_PICK_RINGTONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_PICK_RINGTONE: {
                Uri pickedUri = data
                        .getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                isSelect=true;
                Uristring =pickedUri.toString();
                mcrime.setMusicname(RingtoneManager.getRingtone(getActivity(), pickedUri).getTitle(getActivity()));
                musinSetBtn.setText(mcrime.getMusicname());

                break;
            }
        }
    }






}
