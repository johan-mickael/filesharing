package utils;

import java.util.Date;
import java.util.Random;

public class Utilities {
	public static String getRandom() {
		Date date = new Date();
		Random random = new Random();
		return date.getYear()+1900+""+date.getMonth()+1+""+date.getDate()+""+date.getHours()+""+date.getMinutes()+""+date.getSeconds()+random.nextInt();
	}
}
