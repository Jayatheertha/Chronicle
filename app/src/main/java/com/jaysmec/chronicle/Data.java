package com.jaysmec.chronicle;

/**
 * Created by jayat on 18-Sep-16.
 */
public class Data  {
    String entry,address,date,time,tag,temp;
            long cid,tim;
    Data(String en,String ad, String dat,String time,String tag,String temp,long cid,long tim){
        this.address=ad;
        this.date=dat;
        this.entry=en;
        this.tag=tag;
        this.temp=temp;
        this.time=time;
        this.cid=cid;
        this.tim=tim;
    }

}
