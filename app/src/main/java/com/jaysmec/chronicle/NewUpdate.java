package com.jaysmec.chronicle;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class NewUpdate extends AppCompatActivity {

    Button locate,temp;
    Toolbar tb;
    EditText entr,tag;
    TimePicker timep;
    DatePicker dp;
    boolean dateafter;
    int hour,minute,day,month,year;
    String datee,timee,locee,tempee,newdate,newtime;
    long timilee;
    RelativeLayout rl;
    LinearLayout l1;
    boolean valid;
    Data b;
    long curid;

    @Override
    public void onBackPressed() {
        if(valid){
            ContentValues cv = new ContentValues();
            cv.put("entry",entr.getText().toString().trim());
            cv.put("tag",tag.getText().toString().trim());
            cv.put("time",newtime);
            cv.put("date",newdate);
            cv.put("location",locee);
            cv.put("temp",tempee);
            cv.put("tim",timilee);
            Chronicle.db.update("Chronicledb", cv, "cid="+curid, null);
            Toast.makeText(this, "Edit successful", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {

            finish();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_update);
        timep=new TimePicker(this);
        dp=new DatePicker(this);

        hour=timep.getCurrentHour();
        minute=timep.getCurrentMinute();
        day=dp.getDayOfMonth();
        month=dp.getMonth();
        year=dp.getYear();



        tb= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        locate= (Button) findViewById(R.id.locate);
        temp= (Button) findViewById(R.id.tempentry);
        entr= (EditText) findViewById(R.id.text2);
        rl=(RelativeLayout)findViewById(R.id.activity_entry);
        l1=(LinearLayout) findViewById(R.id.tagandloc);
        tag= (EditText) findViewById(R.id.tagentry);
        final EditText t=entr;
        tempee="24";
        Intent editerd=this.getIntent();
        b=Tags.curr.get(editerd.getExtras().getInt("positions"));
        entr.setText(b.entry);
        locee=b.address;
        tempee=b.temp;
        tag.setText(b.tag);
        timee=b.time;
        timilee=b.tim;
        curid=b.cid;
        datee=b.date;
        getSupportActionBar().setTitle(sub(datee)+" "+timee);
        newdate=datee;
        newtime=timee;
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_done);
        valid=true;







        entr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(entr.getText().toString().length()>0){
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_done);
                    valid=true;
                }
                else
                {
                    valid=false;
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
                }


            }
        });

        KeyboardVisibilityEvent.setEventListener(
                NewUpdate.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen)
                            t.setMaxLines(10);
                        else
                            t.setMaxLines(23);
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {


            case android.R.id.home:
                if(valid){
                    ContentValues cv = new ContentValues();
                    cv.put("entry",entr.getText().toString().trim());
                    cv.put("tag",tag.getText().toString().trim());
                    cv.put("time",newtime);
                    cv.put("date",newdate);
                    cv.put("location",locee);
                    cv.put("temp",tempee);
                    cv.put("tim",timilee);
                    Chronicle.db.update("Chronicledb", cv, "cid="+curid, null);
                    Toast.makeText(this, "Edit successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Intent chr=new Intent(this,Chronicle.class);
                    startActivity(chr);
                    finish();
                }

                break;

            case R.id.entrydate:
                DatePickerDialog dpd=new DatePickerDialog(NewUpdate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        day=i2;
                        month=i1;
                        year=i;
                        timilee=(year-1900-1)*365*24*60+getdayofyear(year,month+1,day)*24*60+hour*60+minute;
                        dateafter=true;
                        getSupportActionBar().setTitle(day+" "+getMonth(month)+" "+timecon(hour,minute));
                        newdate=day+" "+getMonth(month)+", "+year;

                        if(dateafter)
                        {
                            TimePickerDialog tpd=new TimePickerDialog(NewUpdate.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                    hour=i;
                                    minute=i1;
                                    timilee=(year-1900-1)*365*24*60+getdayofyear(year,month+1,day)*24*60+hour*60+minute;
                                    getSupportActionBar().setTitle(day+" "+getMonth(month)+" "+timecon(hour,minute));
                                    newtime=timecon(hour,minute);


                                }
                            }, hour, minute, false);tpd.show();

                        }

                    }
                }, year, month , day);dpd.show();

                break;
            case R.id.Discard:
                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(this)
                                .setTitle("Discard")
                                .setMessage("Are you sure ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent chr=new Intent(NewUpdate.this,Chronicle.class);
                                        startActivity(chr);
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.show();
                break;
            case R.id.Night:
                item.setChecked(!item.isChecked());
                if(item.isChecked()){
                    rl.setBackgroundColor(Color.parseColor("#303030"));
                    l1.setBackgroundColor(Color.parseColor("#303030"));
                    tag.setTextColor(Color.parseColor("#000000"));
                    entr.setTextColor(Color.parseColor("#FFFFFF"));
                    entr.setHintTextColor(Color.parseColor("#FFFFFF"));
                    locate.setBackgroundResource(R.drawable.ic_near_me_white_24dp);
                    temp.setBackgroundResource(R.drawable.ic_cloud_white_24dp);
                }
                else
                {
                    rl.setBackgroundColor(Color.parseColor("#00000000"));
                    tag.setTextColor(Color.parseColor("#000000"));
                    l1.setBackgroundColor(Color.parseColor("#00000000"));
                    entr.setTextColor(Color.parseColor("#000000"));
                    locate.setBackgroundResource(R.drawable.ic_near_me_black_24dp);
                    temp.setBackgroundResource(R.drawable.ic_cloud_black_24dp);
                    entr.setHintTextColor(Color.parseColor("#808080"));
                }
                break;


        }

        return true;
    }
    public String timecon(int hour,int minte){
        String h="";
        String minute="";
        if(minte<10)
            minute+="0"+minte;
        else
            minute+=""+minte;

        if(hour==0)
            h+="12"+":"+minute+"am";
        else if(hour>12)
            h+=(hour-12)+":"+minute+"pm";
        else if(hour==12)
            h+= h+="12"+":"+minute+"pm";
        else
            h+=hour+":"+minute+"am";
        return h;

    }
    public String getMonth(int month)
    {
        String monthString="";
        switch (month+1) {
            case 1:  monthString = "January";       break;
            case 2:  monthString = "February";      break;
            case 3:  monthString = "March";         break;
            case 4:  monthString = "April";         break;
            case 5:  monthString = "May";           break;
            case 6:  monthString = "June";          break;
            case 7:  monthString = "July";          break;
            case 8:  monthString = "August";        break;
            case 9:  monthString = "September";     break;
            case 10: monthString = "October";       break;
            case 11: monthString = "November";      break;
            case 12: monthString = "December";      break;
        }
        return monthString;
    }
    public String sub(String v)
    {
        String j="";
        for(int i=0;i<v.length()-6;i++)
            j+=String.valueOf(v.charAt(i));
        return j;


    }
    public int getdayofyear(int yea,int num,int num2)
    {
        int sum=0;
        int [] Days;
        Days = new int [12];
        Days [0] = 31;
        if(((yea%4==0)&&(yea%100!=0))||(yea%400==0))
            Days [1] = 29;
        else
            Days[1]=28;
        Days [2] = 31;
        Days [3] = 30;
        Days [4] = 31;
        Days [5] = 30;
        Days [6] = 31;
        Days [7] = 31;
        Days [8] = 30;
        Days [9] = 31;
        Days [10] = 30;
        Days [11] = 31;
        for(int i=1;i<=num-1;i++) {
            sum += Days[i-1];
        }


        return sum+num2;
    }
    public void editloc(View view) {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater lin = LayoutInflater.from(this);
        View promptsViewn = lin.inflate(R.layout.editloc, null);
        builder.setView(promptsViewn);
        builder.setTitle("Change Location");
        final EditText original = (EditText) promptsViewn
                .findViewById(R.id.editloce);
        original.setText(locee);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(original.getText().toString().trim().length()>0)
                    locee=original.getText().toString().trim();
            }
        });
        builder.show();

    }
}
