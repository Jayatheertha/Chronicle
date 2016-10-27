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

public class TagFragment extends Fragment {
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
                nexter.putExtra("tag",listarr.get(i));
                startActivity(nexter);

            }
        });
        return v;
    }
    public ArrayList<String> getTags(){
        ArrayList<String> ra=new ArrayList<>();
        Cursor c = Chronicle.db.rawQuery("select * from Chronicledb ",null);
        if(c.moveToNext()){
            do{
                if(c.getString(1).length()>0)
                ra.add(c.getString(1));
            }while (c.moveToNext());
        }
        for(int i=0;i<ra.size();i++)
            for(int j=0;j<ra.size();j++) {
                if (ra.get(i).trim().equals(ra.get(j).trim()) && i != j)
                {
                    ra.remove(i);
                }
            }
        Collections.sort(ra);
        return ra;
    }
}
