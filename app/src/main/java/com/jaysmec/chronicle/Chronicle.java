package com.jaysmec.chronicle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class Chronicle extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static ChronicleDb chronicleDb;
    static SQLiteDatabase db;
    TextView user;
Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronicle);
        chronicleDb = new ChronicleDb(this);
        db = chronicleDb.getWritableDatabase();
        tb= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        NavigationView navigationVie = (NavigationView) findViewById(R.id.nav_view);
        navigationVie.setNavigationItemSelectedListener(this);
        View header=navigationVie.getHeaderView(0);
        user= (TextView) header.findViewById(R.id.headusername);
        user.setText(MainActed.sp.getString("Name","no"));
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(getData().size()!=0) {

            ft.replace(R.id.frime, new Fragment1());
            ft.commit();
        }
        else
        {
            ft.replace(R.id.frime,new NodataFragment());
            ft.commit();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editerd=new Intent(Chronicle.this,Entry.class);
                startActivity(editerd);
                editerd.putExtra("positions",0);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chronicle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.ChangeName)
        {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsViewn = li.inflate(R.layout.changename, null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    MainActed.spe.remove("Name");
                    MainActed.spe.putString("Name",original.getText().toString().trim()).commit();
                    user.setText(MainActed.sp.getString("Name","no"));
                   Snackbar.make(findViewById(R.id.linear),"Name Changed Sucessfully",Snackbar.LENGTH_SHORT).setActionTextColor(Color.CYAN).show();
                }

            });
            original.setText(MainActed.sp.getString("Name","Jay"));
            builder.show();

        }

        if (id == R.id.change_password) {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.passwordchange, null);

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater lin = LayoutInflater.from(this);
            View promptsViewn = li.inflate(R.layout.passwordchange, null);
            builder.setView(promptsViewn);
            builder.setTitle("Change password");
            final EditText original = (EditText) promptsViewn
                    .findViewById(R.id.original);
            final EditText newpass = (EditText) promptsViewn
                    .findViewById(R.id.newp);
            final EditText newcpass = (EditText) promptsViewn
                    .findViewById(R.id.cnew);
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
                    if(Integer.parseInt(original.getText().toString())==(MainActed.sp.getInt("password",47000)))
                    {
                        if(newpass.getText().toString().equals(newcpass.getText().toString()) && (newpass.getText().toString().length()==4))
                        {
                            Snackbar.make(findViewById(R.id.linear),"Password succesfully changed",Snackbar.LENGTH_SHORT).show();
                            MainActed.spe.remove("password");
                            MainActed.spe.putInt("password",Integer.parseInt(newcpass.getText().toString().trim()));
                            MainActed.spe.commit();
                        }
                    }
                    else {
                        original.setError("Wrong password");
                        Snackbar.make(findViewById(R.id.linear),"Password not changed try again",Snackbar.LENGTH_SHORT).show();

                    }
                }
            });
            builder.show();




        }

        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.chronicle)
        {
            getSupportActionBar().setTitle("Chronicle");
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if(getData().size()!=0) {

                ft.replace(R.id.frime, new Fragment1());
                ft.commit();
            }
            else
            {
                ft.replace(R.id.frime,new NodataFragment());
                ft.commit();
            }

        }

         if (id == R.id.about) {
            Intent about=new Intent(this,About.class);
            startActivity(about);

        }
        if (id == R.id.Feedback) {
            Intent feed=new Intent(this,Feedback.class);
            startActivity(feed);

        }
        if(id==R.id.tag1)
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            getSupportActionBar().setTitle("Tags");
            if(getData().size()!=0) {

                ft.replace(R.id.frime, new TagFragment());
                ft.commit();
            }
            else
            {
                ft.replace(R.id.frime,new NodataFragment());
                ft.commit();
            }

        }

        if(id==R.id.tag2)
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            getSupportActionBar().setTitle("Location Tags");
            if(getData().size()!=0) {

                ft.replace(R.id.frime, new LocFragment());
                ft.commit();
            }
            else
            {
                ft.replace(R.id.frime,new NodataFragment());
                ft.commit();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    public ArrayList<Data> getData()
    {
        ArrayList<Data> ra=new ArrayList<>();
        Cursor c = Chronicle.db.rawQuery("select * from Chronicledb ",null);
        if(c.moveToNext()){
            do{
                Data h=new Data(c.getString(0),c.getString(4),c.getString(3),c.getString(2),c.getString(1),c.getString(5),c.getLong(6),c.getLong(7));
                ra.add(h);
            }while (c.moveToNext());

        }


        return ra;
    }

}
