package com.javasm.storage.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者:yy
 * 日期:2022/7/1 11:24
 * 描述:
 */
public class FormCodeUtil {
    public enum FormCodePrefix {
        XSRK("XSRK"), XSCK("XSCK"), SHRK("SHRK"), SHCK("SHCK"), YK("YK"), SH("SH"),PD("PD");


        FormCodePrefix(String prefix) {
            this.prefix = prefix;
        }

        private String prefix;

        public String getPrefix() {
            return this.prefix;
        }
    }

    static int xsrk = 0;
    static int xsck = 0;
    static int shrk = 0;
    static int shck = 0;
    static int yk = 0;
    static int sh = 0;
    static int pd = 0;

    public static String get(FormCodePrefix prefix){
        StringBuilder sb = new StringBuilder(prefix.getPrefix());
        sb.append(getCurrentDate());
        switch (prefix){
            case SH:
                sb.append(sh++);
                break;
            case YK:
                sb.append(yk);
                break;
            case SHCK:
                sb.append(shck++);
                break;
            case SHRK:
                sb.append(shrk++);
                break;
            case XSCK:
                sb.append(xsck++);
                break;
            case XSRK:
                sb.append(xsrk++);
                break;
            case PD:
                sb.append(pd++);
                break;
            default:
                sb.append(0);
        }
        return sb.toString();
    }

    private static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        String format1 = format.format(calendar.getTime());
        return format1;
    }
}
