package com.teoryul.newsly.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Util class for converting a ISO8601 date string, ie. "2019-03-21T12:21:00Z", into a date object with local formatting.
 * Also, supports conversion from date object to long and vice versa.
 * <p>
 * INFO: The T is just a literal to separate the date from the time, and the Z means "zero hour offset" also known as "Zulu time" (UTC).
 */
public final class DateUtil {

    private static final SimpleDateFormat iso8601StringParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    /**
     * Parses an iso8601 string to a date object and then applies a local formatting to it.
     *
     * @param iso8601DateString
     * @return Locally formatted String representation of the provided iso8601DateString
     * @throws ParseException When it fails to parse the string
     */
    public static String stringToDateString(final String iso8601DateString) throws ParseException {
        return SimpleDateFormat.getDateInstance().format(iso8601StringParser.parse(iso8601DateString.replace("Z", "+0000")));
    }

    /**
     * Parses an iso8601 string to a date object and returns its time in milliseconds.
     *
     * @param iso8601DateString
     * @return Long time representation of the provided iso8601 string
     * @throws ParseException When it fails to parse the string
     */
    public static long stringToMillis(final String iso8601DateString) throws ParseException {
        return iso8601StringParser.parse(iso8601DateString.replace("Z", "+0000")).getTime();
    }

    /**
     * @param longTime
     * @return String representation of the provided time
     */
    public static String millisToDateString(final long longTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(longTime);
        return SimpleDateFormat.getDateInstance().format(cal.getTime());
    }

    public static long getCurrentTimeAsMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
