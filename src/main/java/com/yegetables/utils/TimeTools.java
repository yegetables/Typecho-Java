package com.yegetables.utils;

import java.sql.Timestamp;

public class TimeTools {
    public static Timestamp string10ToTimestamp(String s) {
        if (s == null) return null;
        int en = String.valueOf(System.currentTimeMillis()).length();
        if (s.length() < en)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("0".repeat(en - s.length()));
            s += sb;
        }
        return new Timestamp(Long.parseLong(s));
    }

    public static String timestampToString10(Timestamp s) {
        if (s == null) return "0";
        Long date = s.getTime();
        return String.valueOf(date).substring(0, 10);
    }
    public static Timestamp NowTime(){
        return new Timestamp(System.currentTimeMillis());
    }
}
