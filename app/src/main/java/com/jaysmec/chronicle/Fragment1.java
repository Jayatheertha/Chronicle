package com.jaysmec.chronicle;



import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;


/**
 * Created by jayat on 24-Sep-16.
 */

public class Fragment1 extends Fragment {
    RecyclerView lv;
   static ArrayList<Data> datfrag;
    ChronicleAdapter jay;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, container, false);
        lv= (RecyclerView) view.findViewById(R.id.lv);
         datfrag=getData();
        jay=new ChronicleAdapter(getData(),getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(mLayoutManager);
        lv.setItemAnimator(new DefaultItemAnimator());
        setAdpt();
        return view;
    }
    public static ArrayList<Data> getData()
    {
        ArrayList<Data> ra=new ArrayList<>();
        Cursor c = Chronicle.db.rawQuery("select * from Chronicledb ",null);
        if(c.moveToNext()){
            do{
                Data h=new Data(c.getString(0),c.getString(4),c.getString(3),c.getString(2),c.getString(1),c.getString(5),c.getLong(6),c.getLong(7));
               ra.add(h);
            }while (c.moveToNext());

        }
           Data temp;
        for (int i = 0; i < ( ra.size() - 1 ); i++) {
            for (int d = 0; d < ra.size() - i - 1; d++) {
                if (ra.get(d).tim <= ra.get(d+1).tim)
                {
                    temp = ra.get(d);
                    ra.set(d,ra.get(d+1));
                    ra.set(d+1,temp);
                }
            }
        }

        return ra;
    }

    @Override
    public void onResume() {
        super.onResume();
        jay=new ChronicleAdapter(getData(),getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(mLayoutManager);
        lv.setItemAnimator(new DefaultItemAnimator());
        setAdpt();
    }

    public void setAdpt()
    {
        lv.setAdapter(jay);

    }



}
