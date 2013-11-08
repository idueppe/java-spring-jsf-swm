package de.swm.auction.dao;

/**
 * Never use this in production!!! 
 */
public class RepositoryUtils
{
	
	private static long nextId = System.currentTimeMillis();

	public static long nextId()
	{
		return ++RepositoryUtils.nextId;
	}

}
