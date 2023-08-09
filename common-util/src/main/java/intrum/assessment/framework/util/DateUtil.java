package intrum.assessment.framework.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	static String FORMAT_DATETIME_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static String addDaysTimeUTC(long days)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMAT_DATETIME_UTC);
		LocalDateTime date = LocalDateTime.now();
		date = date.plusDays(days);
		return dtf.format(date);
	}

	public static String currentTimeUTC()
	{
		return addDaysTimeUTC(0);
	}

}
