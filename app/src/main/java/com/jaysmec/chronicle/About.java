package com.jaysmec.chronicle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = this.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.parseColor("#B3A700"));
            }
        }


        TextView tx= (TextView) findViewById(R.id.WE);
        TextView tx1= (TextView) findViewById(R.id.make);
        TextView sol= (TextView) findViewById(R.id.sollu);
        LinearLayout plus1= (LinearLayout) findViewById(R.id.gplus1);
        LinearLayout plus2= (LinearLayout) findViewById(R.id.gplus2);
        LinearLayout plus3= (LinearLayout) findViewById(R.id.gplus3);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Light.ttf");
        Typeface bold=Typeface.createFromAsset(getAssets(),"fonts/Sansation_Bold.ttf");
        Typeface reg=Typeface.createFromAsset(getAssets(),"fonts/Sansation_Regular.ttf");
        tx1.setTypeface(bold);
        sol.setTypeface(reg);
        tx.setTypeface(custom_font);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/+jayatheerthajagirdar")));

            }
        });
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/115319115597001179021")));

            }
        });
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/116552842265987898959")));

            }
        });
    }



    @Override
    public void onBackPressed() {
        finish();
    }
}
