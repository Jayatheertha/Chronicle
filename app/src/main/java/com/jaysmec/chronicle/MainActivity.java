package com.jaysmec.chronicle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    int i=0,test=MainActed.sp.getInt("password",0000);
    int[] pas=new int[4];
    Button o1,o2,o3,o4,o5,o6,o7,o8,o9,o0,forgot;
    ImageView back,confirm;
    TextView one,two,thr,fou,pass;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = this.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.parseColor("#13163B"));
            }
        }
        o0= (Button) findViewById(R.id.o0);
        o1= (Button) findViewById(R.id.o1);
        o2= (Button) findViewById(R.id.o2);
        o3= (Button) findViewById(R.id.o3);
        o4= (Button) findViewById(R.id.o4);
        o5= (Button) findViewById(R.id.o5);
        o6= (Button) findViewById(R.id.o6);
        o7= (Button) findViewById(R.id.o7);
        o8= (Button) findViewById(R.id.o8);
        o9= (Button) findViewById(R.id.o9);
        forgot= (Button) findViewById(R.id.forgot);
        confirm= (ImageView) findViewById(R.id.con);
        back= (ImageView) findViewById(R.id.back);
        one= (TextView) findViewById(R.id.one);
        two= (TextView) findViewById(R.id.two);
        thr= (TextView) findViewById(R.id.thr);
        fou= (TextView) findViewById(R.id.fou);
        pass= (TextView) findViewById(R.id.pass);
        in= new Intent(MainActivity.this,Chronicle.class);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsViewn = li.inflate(R.layout.changename, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(promptsViewn);
                builder.setTitle("Change Name");
                final EditText original = (EditText) promptsViewn
                        .findViewById(R.id.changename);
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


                    }

                });
                original.setText(MainActed.sp.getString("Name","Jay"));
                builder.show();


            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                final View promptsViewn = li.inflate(R.layout.forgetpass, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(promptsViewn);
                final EditText answernig = (EditText) promptsViewn
                        .findViewById(R.id.ansernigga);
                TextView text= (TextView) promptsViewn.findViewById(R.id.textviewnab);

                text.setText(MainActed.secque[MainActed.sp.getInt("secques",8)]);

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

                        if(answernig.getText().toString().trim().equals(MainActed.sp.getString("answer","jayatheertha")))
                        {
                            Toast.makeText(MainActivity.this, "Password is : "+MainActed.sp.getInt("password",12457), Toast.LENGTH_SHORT).show();
                            startActivity(in);
                            finish();

                        }
                        else
                            Toast.makeText(MainActivity.this, "Answer is not correct", Toast.LENGTH_SHORT).show();
                    }

                });
                builder.show();

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i-1!=-1)
                {
                    pas[i-1]=0;
                    switch(i-1)
                    {
                        case 0:one.setTextColor(Color.parseColor("#2196f3"));break;

                        case 1:two.setTextColor(Color.parseColor("#2196f3"));break;

                        case 2:thr.setTextColor(Color.parseColor("#2196f3"));break;

                        case 3:fou.setTextColor(Color.parseColor("#2196f3"));break;

                    }
                    i--;

                }


            }
        });

        o1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butpress(1);

            }
        });
        o2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butpress(2);

            }
        });
        o3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butpress(3);

            }
        });
        o4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               butpress(4);

            }
        });
        o5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butpress(5);

            }
        });
        o6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butpress(6);

            }
        });
        o7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butpress(7);

            }
        });
        o8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butpress(8);

            }
        });
        o9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               butpress(9);

            }
        });
        o0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              butpress(0);

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cal(pas)== test && i==4){
                    startActivity(in);
                    finish();
                }
                else{
                    pass.setText("Wrong Password");
                    forgot.setVisibility(View.VISIBLE);
                    pass.setTextColor(Color.RED);
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    i=0;
                    one.setTextColor(Color.parseColor("#2196f3"));

                    two.setTextColor(Color.parseColor("#2196f3"));

                    thr.setTextColor(Color.parseColor("#2196f3"));

                    fou.setTextColor(Color.parseColor("#2196f3"));

                    v.vibrate(200);}

            }
        });





    }
    public int cal(int[] a)
    {
        int s=0;
        for(int i=0;i<a.length;i++) {
            s += a[i];
            s*=10;
        }
        return  s/10;
    }
    public void butpress(int j)
    {
     if(i<4)
        switch(i)
        {
            case 0:one.setTextColor(Color.parseColor("#f50057"));
                pas[i++]=j;
                break;
            case 1:two.setTextColor(Color.parseColor("#f50057"));
                pas[i++]=j;break;
            case 2:thr.setTextColor(Color.parseColor("#f50057"));
                pas[i++]=j;break;
            case 3:fou.setTextColor(Color.parseColor("#f50057"));
                pas[i++]=j;break;
        }if(cal(pas)== test && i==4)
    {
        startActivity(in);
        finish();
    }

    }

}
