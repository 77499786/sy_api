package com.forest.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class ForestDateUtils {
    private static String short_pattern = "yyyy-MM-dd";

    public static Date formatShortDate(String daystring){
        try{
            return DateUtils.parseDate(daystring, short_pattern);
        } catch(Exception e) {
            return null;
        }
    }
}
