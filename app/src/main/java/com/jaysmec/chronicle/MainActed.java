package com.jaysmec.chronicle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class MainActed extends Activity {
    TextView welcome;
    ImageView image;
    Button but;
    EditText name,pass,cpass,answer;
    Spinner spin;
    static SharedPreferences sp;
    static SharedPreferences.Editor spe;
   static String[] secque={ "What is your Favourite movie?","What was the name of your elementary / primary school?" , "What is the first and last name of your first boyfriend or girlfriend?"
            , "What is your mother's maiden name?","What is the name of your crush?" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acted);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = this.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.parseColor("#202020"));
            }
        }

        welcome= (TextView) findViewById(R.id.textView2);
        image= (ImageView) findViewById(R.id.visi);
        but= (Button) findViewById(R.id.proceed);
        name= (EditText) findViewById(R.id.Name);
        pass= (EditText) findViewById(R.id.pass);
        cpass=(EditText) findViewById(R.id.con);
        answer= (EditText) findViewById(R.id.answer);
        sp=this.getSharedPreferences("DATA",0);
        spe=sp.edit();
        spin= (Spinner) findViewById(R.id.spin);
        if(sp.getBoolean("firstskip",false))
        {
            Intent skip=new Intent(this,MainActivity.class);
            startActivity(skip);
            finish();
        }
        ArrayAdapter<String> jaya=new ArrayAdapter<String>(this,R.layout.spinningmil,secque);
        spin.setAdapter(jaya);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(name.getText().toString().trim().length()<3 &&name.getText().toString().trim().length()>21  || !(validname(name.getText().toString())))
                    name.setError("Name should be at least 3 characters length with no special characters");
            }
        });

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spe.putInt("secques",i).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spe.putInt("secques",0).commit();

            }
        });
        answer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {KeyboardVisibilityEvent.setEventListener(
                        MainActed.this,
                        new KeyboardVisibilityEventListener() {
                            @Override
                            public void onVisibilityChanged(boolean isOpen) {
                                if(isOpen)
                                {
                                    image.setVisibility(View.GONE);
                                    welcome.setVisibility(View.GONE);
                                }

                                else
                                {
                                    image.setVisibility(View.VISIBLE);
                                    welcome.setVisibility(View.VISIBLE);

                                }



                            }
                        });
            }
            else{ image.setVisibility(View.VISIBLE);
                    welcome.setVisibility(View.VISIBLE);}
                }
        });




        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(pass.getText().length()<4)
                    pass.setError("4 integers");

            }
        });

        cpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if((pass.getText().toString().equals(cpass.getText().toString()))){}
                else
                    cpass.setError("passwords do not Match");


            }
        });


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().length()>2 && pass.getText().toString().equals(cpass.getText().toString())&& pass.getText().toString().length()==4 && answer.getText().toString().length()>0)
                {
                    spe.putBoolean("firstskip",true);
                    spe.putInt("password",Integer.parseInt(pass.getText().toString()));
                    spe.putString("Name",name.getText().toString().trim());
                    spe.putLong("cid",1);
                    spe.putString("answer",answer.getText().toString().trim());
                    spe.commit();
                    Intent bye=new Intent(MainActed.this,Chronicle.class);
                    startActivity(bye);
                    finish();
                }
                else
                {
                    if(name.getText().toString().length()==0)
                        name.setError("Required");
                    if(pass.getText().toString().length()==0)
                        pass.setError("Required");
                    if(cpass.getText().toString().length()==0)
                        cpass.setError("Required");
                    if(answer.getText().toString().length()==0)
                        answer.setText("Required");



                }


            }
        });


        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    image.setVisibility(View.GONE);}
                else
                    image.setVisibility(View.VISIBLE);
            }
        });
        cpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    KeyboardVisibilityEvent.setEventListener(
                            MainActed.this,
                            new KeyboardVisibilityEventListener() {
                                @Override
                                public void onVisibilityChanged(boolean isOpen) {
                                    if(isOpen)
                                    {
                                        image.setVisibility(View.GONE);
                                        welcome.setVisibility(View.GONE);
                                    }

                                    else
                                    {
                                        image.setVisibility(View.VISIBLE);
                                        welcome.setVisibility(View.VISIBLE);

                                    }

                }
            });

                    }
                else{
                    image.setVisibility(View.VISIBLE);
                    welcome.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public boolean validname(String t)
    {
        for(int i=0;i<t.length();i++)
        {
            if((t.charAt(i)>='A'&& t.charAt(i)<='Z') || (t.charAt(i)>='a'&& t.charAt(i)<='z') || t.charAt(i)==' ')
            {}
            else
                return false;
        }
        return true;

    }
}
