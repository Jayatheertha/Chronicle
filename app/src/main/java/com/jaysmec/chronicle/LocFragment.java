package com.jaysmec.chronicle;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jayat on 10-Oct-16.
 */

public class LocFragment extends Fragment {
    ListView taglv;
    ArrayList<String> listarr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tags,container,false);
        taglv= (ListView) v.findViewById(R.id.taglv);
        listarr=getTags();
        ArrayAdapter<String> jaya=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,listarr);
        taglv.setAdapter(jaya);
        taglv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nexter=new Intent(getActivity(),Tags.class);
                nexter.putExtra("loc",listarr.get(i));
                startActivity(nexter);

            }
        });
        return v;
    }
    public ArrayList<String> getTags(){
        ArrayList<String> raw=new ArrayList<>();
        Cursor c = Chronicle.db.rawQuery("select * from Chronicledb ",null);
        if(c.moveToNext()){
            do{
                raw.add(c.getString(4));
            }while (c.moveToNext());
        }
       for(int ii=0;ii<raw.size();ii++)
           for(int jj=0;jj<raw.size();jj++)
           {
               if(raw.get(ii).trim().equals(raw.get(jj).trim())&& ii!=jj)
               {
                   raw.remove(ii);
               }

           }

        for(int ii=0;ii<raw.size();ii++)
            for(int jj=0;jj<raw.size();jj++)
            {
                if(raw.get(ii).trim().equals(raw.get(jj).trim())&& ii!=jj)
                {
                    raw.remove(ii);
                }

            }
        Collections.sort(raw);
        return raw;
    }
}
