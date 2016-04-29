package holmusk.com.holmuskchallenge.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTime {
    
    public static final String SINGPAORE_TIMEZONE = "Asia/Singapore";

    private static final String DATE_FORMAT = "EEE, dd MMM yyyy";
    private static final String DAY_FORMAT  = "EEE";
    private static final String TIME_FORMAT = "HH:mm:ss";

    public static final int WEEK_DAYS       = 7;
    
    
    public static String getTodayDate() {
        Date date = getTimezoneTimestamp();
        return getDateFormat(date.getTime(), DATE_FORMAT);
    }

    public static long getTodayDateInteger() {
        Date date = getTimezoneTimestamp();
        return date.getTime();
    }

    public static long[] getWeeklyDates() {
        long[] dates = new long[WEEK_DAYS];
        for(int i=0; i<WEEK_DAYS; i++) {
            int negIndex = -i;
            dates[i] = getTimezoneTimestamp(negIndex).getTime();
        }

        return dates;
    }

    public static String[] getWeeklyDatesLabels() {
        String[] dates = new String[WEEK_DAYS];
        for(int i=0; i<WEEK_DAYS; i++) {
            int negIndex = -i;
            Date date = getTimezoneTimestamp(negIndex);
            dates[i] = getDateFormat(date, DAY_FORMAT);
        }

        return dates;
    }
    
    /*
     * Private helper methods
     * */
    
    private static Date getTimezoneTimestamp() {
        int todayIndex = 0;
        return getTimezoneTimestamp(todayIndex);
    }

    private static Date getTimezoneTimestamp(int index) {
        TimeZone timeZone = TimeZone.getTimeZone(SINGPAORE_TIMEZONE);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.add(Calendar.DATE, index);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
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
        return getDateFormat(date, format);
    }

    private static String getDateFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
