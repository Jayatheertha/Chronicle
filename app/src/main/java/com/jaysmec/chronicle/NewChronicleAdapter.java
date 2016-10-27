package com.jaysmec.chronicle;


import java.util.ArrayList;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class NewChronicleAdapter extends RecyclerView.Adapter<NewChronicleAdapter.MyViewHolder> {

    ArrayList<Data> datalist;
    Context cc;
    long size;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView entry, date, time,locanddata;
        public LinearLayout lin;

        public MyViewHolder(View view) {
            super(view);
            entry = (TextView) view.findViewById(R.id.rowentry);
            date = (TextView) view.findViewById(R.id.rowdate);
            time = (TextView) view.findViewById(R.id.rowtime);
            lin= (LinearLayout) view.findViewById(R.id.rowlinlay);
            locanddata=(TextView)view.findViewById(R.id.rowtempandloc);
        }
    }


    public NewChronicleAdapter(ArrayList<Data> list, Context c) {
        this.datalist = list;
        this.cc=c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chronicle_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Data data = datalist.get(position);
        holder.entry.setText(data.entry);
        holder.date.setText(data.date);
        holder.time.setText(data.time);
        if(data.temp.length()>0)
            holder.locanddata.setText(data.address+", "+data.temp+"°C");
        else
            holder.locanddata.setText(data.address+", "+data.temp);
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewerf=new Intent(cc,NewView.class);
                viewerf.putExtra("pos",position);
                cc.startActivity(viewerf);
                //((Activity)cc).finish();
            }
        });
        holder.lin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Data b=Fragment1.datfrag.get(position);
                final long cid=b.cid;
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cc);
                alertDialogBuilder.setMessage("Are you sure you want to delete this?");

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Chronicle.db.delete("Chronicledb", "cid" + "=" +cid , null);
                        Toast.makeText(cc, "Delete successful", Toast.LENGTH_SHORT).show();
                        datalist.remove(position);
                        Fragment1.datfrag.remove(position);
                        notifyItemRemoved(position);

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

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
