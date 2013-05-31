package com.omni.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class CurrentDate extends Date{
    public String toString(){
        SimpleDateFormat formatter= 
        new SimpleDateFormat("yyyy/MM/dd");
        String dateNow = formatter.format(this.getTime());
        String ans="";
        ans+=dateNow;
        return ans;
    }  
}
