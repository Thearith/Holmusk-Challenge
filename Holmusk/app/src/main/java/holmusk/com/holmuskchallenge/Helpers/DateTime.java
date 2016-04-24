package holmusk.com.holmuskchallenge.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTime {
    
    public static final String SINGPAORE_TIMEZONE = "Asia/Singapore";

    private static final String DATE_FORMAT = "EEE, dd MMM yyyy";
    private static final String TIME_FORMAT = "HH:mm:ss";
    
    
    public static String getTodayDate() {
        Date date = getTimezoneTimestamp();
        return getDateFormat(date.getTime(), DATE_FORMAT);
    }

    public static long getTodayDateInteger() {
        Date date = getTimezoneTimestamp();
        return date.getTime();
    }
    
    /*
     * Private helper methods
     * */
    
    private static Date getTimezoneTimestamp() {
        TimeZone timeZone = TimeZone.getTimeZone(SINGPAORE_TIMEZONE);
        return Calendar.getInstance(timeZone).getTime();
    }
    
    private static long getDateFormat(String dateString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        
        try {
            Date date = formatter.parse(dateString);
            return date.getTime();
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    private static String getDateFormat(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        
        return formatter.format(date);
    }
}
