package de.swm.auction;

import java.util.Date;

public class TestUtils
{
	public static Date before60Seconds = new Date(System.currentTimeMillis() - 60000);
	public static Date before30Seconds = new Date(System.currentTimeMillis() - 30000);
	public static Date in30Seconds = new Date(System.currentTimeMillis() + 30000);
	public static Date in60Seconds = new Date(System.currentTimeMillis() + 60000);

}
