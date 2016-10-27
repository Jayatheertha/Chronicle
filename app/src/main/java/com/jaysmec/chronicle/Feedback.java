package com.jaysmec.chronicle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


public class Feedback extends Activity {
    RatingBar rb;
    Button fbut;
    EditText ftex;
    String[] emails={"jayatheertha3@gmail.com","tolishurilavanya@gmail.com"};
float rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = this.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.parseColor("#541C76"));
            }
        }
        rb= (RatingBar) findViewById(R.id.ratebar);
        fbut= (Button) findViewById(R.id.sendingf);
        ftex= (EditText) findViewById(R.id.feedba);
        LayerDrawable layerDrawable=(LayerDrawable)rb.getProgressDrawable();
        layerDrawable.getDrawable(2).setColorFilter(Color.parseColor
                ("#69F0AE"),    PorterDuff.Mode.SRC_ATOP);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(b){
                    rating=v;
                    if(rating==5.0)
                        Snackbar.make(findViewById(R.id.activity_feedback),"Thank You! You're awesome.",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        fbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ftex.getText().toString().length()!=0) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, emails);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Chronicle");
                    intent.putExtra(Intent.EXTRA_TEXT, "The rating is " + rating + "\n\n" + ftex.getText().toString());

                    startActivity(Intent.createChooser(intent, "Send Email"));
                }
                else
                    ftex.setError("required");

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
