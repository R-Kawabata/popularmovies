package com.example.raphaelkawabata.popularmovies.Utility;

import com.example.raphaelkawabata.popularmovies.MovieDetailsActivity;

/**
 * Created by raphael.kawabata on 06/11/2017.
 */

public class DateParser extends MovieDetailsActivity {
    public static String parseReleaseDate(String string) {
        int month, day;
        String dateArray[] = string.split("-");
        month = Integer.parseInt(dateArray[1]);
        day = Integer.parseInt(dateArray[2]);
        switch (month) {
            case 1:
                dateArray[1] = "January";
                break;
            case 2:
                dateArray[1] = "February";
                break;
            case 3:
                dateArray[1] = "March";
                break;
            case 4:
                dateArray[1] = "April";
                break;
            case 5:
                dateArray[1] = "May";
                break;
            case 6:
                dateArray[1] = "June";
                break;
            case 7:
                dateArray[1] = "July";
                break;
            case 8:
                dateArray[1] = "August";
                break;
            case 9:
                dateArray[1] = "September";
                break;
            case 10:
                dateArray[1] = "October";
                break;
            case 11:
                dateArray[1] = "November";
                break;
            case 12:
                dateArray[1] = "December";
                break;
        }
        if (day == 1 || day % 10 == 2) {
            dateArray[2] = day + "st";
        } else if (day == 2 || day % 10 == 2) {
            dateArray[2] = day + "nd";
        } else if (day == 3 || day % 10 == 3) {
            dateArray[2] = day + "rd";
        } else {
            dateArray[2] = day + "th";
        }
        string = dateArray[0] + "\n" + dateArray[1] + ", " + dateArray[2];
        return string;
    }

}
