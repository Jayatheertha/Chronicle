package com.jaysmec.chronicle;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Entry extends AppCompatActivity implements LocationListener {
    Button locate, temp;
    Toolbar tb;
    EditText entr, tag;
    TimePicker timep;
    DatePicker dp;
    boolean dateafter;
    int hour, minute, day, month, year;
    String  datee, timee, locee, tempee;
    RelativeLayout rl;
    LinearLayout l1;
    boolean valid;
    long timil, id;
    ProgressBar bar;

    @Override
    public void onBackPressed() {
        if (valid) {
            Intent inte = new Intent(Entry.this, Chronicle.class);
            String sql = "INSERT INTO Chronicledb VALUES (?, ?,?,?,?,?,?,?)";
            SQLiteStatement statement = Chronicle.db.compileStatement(sql);
            statement.bindString(1, entr.getText().toString().trim());
            statement.bindString(2, tag.getText().toString().trim());
            statement.bindString(3, timecon(hour, minute));
            statement.bindString(4, day + " " + getMonth(month) + ", " + year);
            statement.bindString(5, locee);
            statement.bindLong(6, 24);
            statement.bindLong(7, id);
            statement.bindLong(8, (year - 1900 - 1) * 365 * 24 * 60 + getdayofyear(year, month + 1, day) * 24 * 60 + hour * 60 + minute);
            startActivity(inte);
            MainActed.spe.remove("cid");
            MainActed.spe.putLong("cid", id + 1);
            MainActed.spe.commit();
            finish();
        } else {
            Intent discard = new Intent(Entry.this, Chronicle.class);
            startActivity(discard);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_entry);
        id = MainActed.sp.getLong("cid", 12345);
        timep = new TimePicker(this);
        dp = new DatePicker(this);

        hour = timep.getCurrentHour();
        minute = timep.getCurrentMinute();
        day = dp.getDayOfMonth();
        month = dp.getMonth();
        year = dp.getYear();


        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(day + " " + getMonth(month) + " " + timecon(hour, minute));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        locate = (Button) findViewById(R.id.locate);
        temp = (Button) findViewById(R.id.tempentry);
        entr = (EditText) findViewById(R.id.text2);
        rl = (RelativeLayout) findViewById(R.id.activity_entry);
        l1 = (LinearLayout) findViewById(R.id.tagandloc);
        tag = (EditText) findViewById(R.id.tagentry);
        bar = (ProgressBar) findViewById(R.id.barnigga);
        bar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#009688"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        final EditText t = entr;
        timee = timecon(hour, minute);
        datee = day + " " + getMonth(month) + ", " + year;
        timil = (year - 1900 - 1) * 365 * 24 * 60 + getdayofyear(year, month, day) * 24 * 60 + hour * 60 + minute;
        locee = "Unknown";
        tempee = "24";
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                if(isOnline()) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (null != listAddresses && listAddresses.size() > 0) {
                            locee = (listAddresses.get(0).getAddressLine(1));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    locee = "" + longitude + ", " + latitude;
                    locate.setBackgroundResource(R.drawable.place_disabled24dp);
                    locate.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(R.id.activity_entry),"Internet is Disabled",Snackbar.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                }
            }
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 500, (LocationListener) this);



        entr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (entr.getText().toString().length() > 0) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_done);
                    valid = true;
                } else {
                    valid = false;
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
                }


            }
        });

        KeyboardVisibilityEvent.setEventListener(
                Entry.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen)
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
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

        double latitude = (double) (location.getLatitude());
        double longitude = (double) (location.getLongitude());

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (null != listAddresses && listAddresses.size() > 0) {
                locee = (listAddresses.get(0).getAddressLine(1));
                bar.setVisibility(View.GONE);
                Toast.makeText(this, "Location is : " + locee, Toast.LENGTH_SHORT).show();
                locate.setVisibility(View.VISIBLE);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onProviderDisabled(String provider) {
        Snackbar.make(findViewById(R.id.activity_entry),"Location is Disabled",Snackbar.LENGTH_SHORT).show();
        locate.setBackgroundResource(R.drawable.place_disabled24dp);
        locate.setVisibility(View.VISIBLE);
        bar.setVisibility(View.GONE);
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case android.R.id.home:
                if (valid) {
                    Intent inte = new Intent(Entry.this, Chronicle.class);
                    String sql = "INSERT INTO Chronicledb VALUES (?,?,?,?,?,?,?,?)";
                    SQLiteStatement statement = Chronicle.db.compileStatement(sql);
                    statement.bindString(1, entr.getText().toString().trim());
                    statement.bindString(2, tag.getText().toString().trim());
                    statement.bindString(3, timee);
                    statement.bindString(4, datee);
                    statement.bindString(5, locee);
                    statement.bindLong(6, Integer.valueOf(tempee));
                    statement.bindLong(7, id);
                    statement.bindLong(8, (year - 1900 - 1) * 365 * 24 * 60 + getdayofyear(year, month + 1, day) * 24 * 60 + hour * 60 + minute);
                    long rowId = statement.executeInsert();
                    startActivity(inte);
                    MainActed.spe.remove("cid");
                    MainActed.spe.putLong("cid", id + 1).commit();



                    finish();
                }
                else
                {
                    Intent inte=new Intent(Entry.this,Chronicle.class);
                    startActivity(inte);
                    finish();
                }

                break;

            case R.id.entrydate:
                DatePickerDialog dpd=new DatePickerDialog(Entry.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        day=i2;
                        month=i1;
                        year=i;

                        datee=day+" "+getMonth(month)+", "+year;
                        dateafter=true;
                        getSupportActionBar().setTitle(day+" "+getMonth(month)+" "+timecon(hour,minute));
                        if(dateafter==true)
                        {
                            TimePickerDialog tpd=new TimePickerDialog(Entry.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                    hour=i;
                                    minute=i1;

                                    timee=timecon(hour,minute);
                                    getSupportActionBar().setTitle(day+" "+getMonth(month)+" "+timecon(hour,minute));


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
                                        Intent discard=new Intent(Entry.this,Chronicle.class);
                                        startActivity(discard);
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
                    temp.setBackgroundResource(R.drawable.ic_cloud_white_24dp);
                }
                else
                {
                    rl.setBackgroundColor(Color.parseColor("#00000000"));
                    tag.setTextColor(Color.parseColor("#000000"));
                    l1.setBackgroundColor(Color.parseColor("#00000000"));
                    entr.setTextColor(Color.parseColor("#000000"));
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
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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

