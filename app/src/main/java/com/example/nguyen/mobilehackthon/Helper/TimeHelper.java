package com.example.nguyen.mobilehackthon.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {

    public int dateDiff(long timeEvent){
        try{
            /* Get Current time*/
            String s1;
            Date dnow=new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat ("MM/dd/yyyy");
            s1=ft.format(dnow);
            Date d3= new Date(timeEvent);

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            String s2= sdf.format(d3);
            //Dates to compare
            String CurrentDate=  s1;
            String FinalDate=  s2;

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");

            //Setting dates
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);
            SimpleDateFormat f1 = new SimpleDateFormat ("E");
            SimpleDateFormat f2 = new SimpleDateFormat ("E");

            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            float diffDays = (float) (difference *1.0 / (60 * 60 * 1000 * 24));
            return Math.round(diffDays);
        }catch(Exception e) {}
        return 0;
    }

    public String getDay(long timeEvent) {
        try {
            String s1;
            Date dnow = new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat("MM/dd/yyyy");
            s1 = ft.format(dnow);
            Date d3 = new Date(timeEvent);

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            String s2 = sdf.format(d3);
            //Dates to compare
            String CurrentDate = s1;
            String FinalDate = s2;

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");

            //Setting dates
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);
            SimpleDateFormat f1 = new SimpleDateFormat("E");
            SimpleDateFormat f2 = new SimpleDateFormat("E");

            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            if (Check(differenceDates, f1.format(date1), f2.format(date2)))
                return f2.format(date2);
            else
                return "null";

        } catch (Exception exception) {}
        return "null";
    }

    private boolean Check(long l, String format, String format1) {

        if (l<7)
            if (change2Day(format1)>=change2Day(format)) return true;
            return false;
        }

    private int change2Day(String s) {
        switch (s) {
            case ("Mon"):
                return 1;
            case ("Tue"):
                return 2;
            case ("Wed"):
                return 3;
            case ("Thu"):
                return 4;
            case ("Fri"):
                return 5;
            case ("Sat"):
                return 6;
            case ("Sun"):
                return 7;
        }
        return 1;

    }

}
