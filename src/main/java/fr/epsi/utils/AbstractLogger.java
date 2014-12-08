package fr.epsi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class AbstractLogger {

    public static void log(String stringToDisplay){
        System.out.println(logPrefix() + stringToDisplay);
    }

    private static String logPrefix(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date).toString() + "> ";
    }

    public static String getConsoleInput(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
