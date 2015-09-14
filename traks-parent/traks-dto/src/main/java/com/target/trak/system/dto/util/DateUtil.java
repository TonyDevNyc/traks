package com.target.trak.system.dto.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

private final static String EMPTY_STR = "";
	
	private DateUtil(){}
	
	public static String convertDateToIso8601(final Calendar calendar) {
		if (calendar == null) {
			return EMPTY_STR;
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		return df.format(new Date(calendar.getTimeInMillis()));
	}
	
	public static Calendar convertTimestampToCalendar(final Timestamp ts) {
		if (ts == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ts.getTime());
		return c;
	}
	
	public static Timestamp convertCalendarToTimestamp(final Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		return new Timestamp(calendar.getTimeInMillis());
	}
}
