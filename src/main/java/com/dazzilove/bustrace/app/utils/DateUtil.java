package com.dazzilove.bustrace.app.utils;

import java.time.LocalDateTime;

public class DateUtil {

    public static String formatTwoLength(String string) {
        String returnValue = string;
        returnValue = (returnValue == null) ? "" : returnValue;
        returnValue = returnValue.trim();
        returnValue = (returnValue.length() == 1) ? "0" + returnValue : returnValue;
        return returnValue;
    }

    public static String getShortFormatedDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return "";
        return String.format("%s-%s-%s"
                , DateUtil.formatTwoLength(String.valueOf(localDateTime.getYear()))
                , DateUtil.formatTwoLength(String.valueOf(localDateTime.getMonthValue()))
                , DateUtil.formatTwoLength(String.valueOf(localDateTime.getDayOfMonth())));
    }

    public static LocalDateTime getStartCreatedAt(String createdAt) {
        String createdAtTemp = createdAt;
        createdAtTemp = createdAtTemp.replaceAll("-", "");

        String year = createdAtTemp.substring(0, 4);
        String month = createdAtTemp.substring(4, 6);
        String day = createdAtTemp.substring(6, 8);

        month = ("0".equals(month.substring(0, 1))) ? month.substring(1, 2) : month;
        day = ("0".equals(day.substring(0, 1))) ? day.substring(1, 2) : day;

        return LocalDateTime.of(
                Integer.parseInt(year)
                , Integer.parseInt(month)
                , Integer.parseInt(day)
                , 0
                , 0
                , 0);
    }

    public static LocalDateTime getEndCreatedAt(String createdAt) {
        LocalDateTime basicDate = getStartCreatedAt(createdAt);
        return LocalDateTime.of(
                basicDate.getYear()
                , basicDate.getMonthValue()
                , basicDate.getDayOfMonth()
                , 23
                , 59
                , 59);
    }

}
