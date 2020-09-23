package com.inoco.kata.shared;

import java.util.Date;

public class DateUtils {
	public static boolean compareDate(final Date dateToCheck, final Date startDate, final Date endDate) {
		return dateToCheck.after(startDate) && dateToCheck.before(endDate);
	}
}
