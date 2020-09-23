package com.inoco.kata.shared;

import java.util.Date;

import com.inoco.kata.entity.Transaction;

public class UserUtils {
	public static boolean checkUserId(final Transaction transaction, final Long userId) {
		return transaction.getIdUser().equals(userId);
	}

	public static boolean compareDate(final Date dateToCheck, final Date startDate, final Date endDate) {
		return dateToCheck.after(startDate) && dateToCheck.before(endDate);
	}
}
