package com.jaysmec.chronicle;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Viewerch extends AppCompatActivity {

    Toolbar tbv;
    TextView vdate,vtime,ventry,vloc,vtemp,vtag;
    int pos;
    Data viewerda;
    long cur;
    RelativeLayout r1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewerch);
        tbv= (Toolbar) findViewById(R.id.toolbarView);
        setSupportActionBar(tbv);
        getSupportActionBar().setTitle("Chronicle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        vdate= (TextView) findViewById(R.id.viewDate);
        vtime= (TextView) findViewById(R.id.viewTime);
        ventry= (TextView) findViewById(R.id.viewEntry);
        vtag= (TextView) findViewById(R.id.viewtag);
        vloc= (TextView) findViewById(R.id.viewloc);
        vtemp= (TextView) findViewById(R.id.viewtemp);
        r1= (RelativeLayout) findViewById(R.id.activity_view);

        Intent viewer=getIntent();
       pos=viewer.getExtras().getInt("pos");
                viewerda=Fragment1.datfrag.get(pos);

        cur=viewerda.cid;
        vdate.setText(viewerda.date);
        vtime.setText(viewerda.time);
        ventry.setText(viewerda.entry);
        vloc.setText(viewerda.address);
        vtemp.setText(viewerda.temp+" °C");
        if(viewerda.tag.length()>0)
        vtag.setText("#"+viewerda.tag);
        else
        vtag.setText(" ");
        ventry.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy", ventry.getText().toString());
                clipboard.setPrimaryClip(clip);
                Snackbar.make(findViewById(R.id.activity_view),"Copied to clipboard.",Snackbar.LENGTH_SHORT).show();


                return true;
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
       finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Fragment1.datfrag=Fragment1.getData();
        Intent viewer=getIntent();
        pos=viewer.getExtras().getInt("pos");
        viewerda=Fragment1.datfrag.get(pos);
        vdate.setText(viewerda.date);
        vtime.setText(viewerda.time);
        ventry.setText(viewerda.entry);
        vloc.setText(viewerda.address);
        vtemp.setText(viewerda.temp+" °C");
        if(viewerda.tag.length()>0)
            vtag.setText("#"+viewerda.tag);
        else
        vtag.setText(" ");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.Night:
                item.setChecked(!item.isChecked());
                if(item.isChecked()){
                    r1.setBackgroundColor(Color.parseColor("#303030"));

                    vtag.setTextColor(Color.parseColor("#69F0AE"));
                    ventry.setTextColor(Color.parseColor("#FFFFFF"));

                }
                else
                {
                    r1.setBackgroundColor(Color.parseColor("#00000000"));
                    vtag.setTextColor(Color.parseColor("#757575"));
                    ventry.setTextColor(Color.parseColor("#000000"));

                }
                break;
            case R.id.Share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ventry.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Choose an App"));
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure you want to delete this?");

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Chronicle.db.delete("Chronicledb", "cid" + "=" +cur , null);
                        Intent backj=new Intent(Viewerch.this,Chronicle.class);
                        Toast.makeText(Viewerch.this, "Delete successful", Toast.LENGTH_SHORT).show();
                        Fragment1.datfrag.remove(pos);
                        startActivity(backj);
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCancelable(true);
                alertDialog.show();

                break;
            case R.id.copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy", ventry.getText().toString());
                clipboard.setPrimaryClip(clip);
                Snackbar.make(findViewById(R.id.activity_view),"Copied to clipboard.",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.edit:
                Intent editerd=new Intent(this,Updaterr.class);
                editerd.putExtra("positions",pos);
                startActivity(editerd);
                break;


        }return true;
    }
}
