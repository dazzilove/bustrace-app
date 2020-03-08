package com.dazzilove.bustrace.app.utils;

public class DateUtil {

    public static String formatTwoLength(String string) {
        String returnValue = string;
        returnValue = (returnValue == null) ? "" : returnValue;
        returnValue = returnValue.trim();
        returnValue = (returnValue.length() == 1) ? "0" + returnValue : returnValue;
        return returnValue;
    }

}
