package de.swm.auction.dao.inmemory;

public class RepositoryUtils
{

	private static long nextId = 0;

	public static long nextId()
	{
		return ++RepositoryUtils.nextId;
	}

}
