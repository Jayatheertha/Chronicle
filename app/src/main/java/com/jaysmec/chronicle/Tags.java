package com.jaysmec.chronicle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import java.util.ArrayList;

public class Tags extends AppCompatActivity {
    RecyclerView rv;
    static ArrayList<Data> curr;
    Toolbar tb;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tags);
        rv= (RecyclerView) findViewById(R.id.rvch);
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Tags");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        curr=new ArrayList<>();
        Intent nexter=getIntent();
        if(nexter.getExtras().getString("tag","").length()>0) {
            getSupportActionBar().setTitle(nexter.getExtras().getString("tag",""));
            for (int i = 0; i < Fragment1.datfrag.size(); i++)
            {
                if(Fragment1.datfrag.get(i).tag.equals(nexter.getExtras().getString("tag")) && Fragment1.datfrag.get(i).address.length()>0)
                {
                    curr.add(Fragment1.datfrag.get(i));
                }
            }

        }
        else if(nexter.getExtras().getString("loc").length()>0)
        {
            getSupportActionBar().setTitle(nexter.getExtras().getString("loc",""));
            curr=new ArrayList<>();
            for (int i = 0; i < Fragment1.datfrag.size(); i++)
            {
                if(Fragment1.datfrag.get(i).address.equals(nexter.getExtras().getString("loc"))&& Fragment1.datfrag.get(i).address.length()>0)
                {
                    curr.add(Fragment1.datfrag.get(i));
                }
            }


        }
        NewChronicleAdapter ju=new NewChronicleAdapter(curr,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ju);
    }
}
