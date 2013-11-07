package de.swm.auction.api;

import de.swm.auction.dao.ProductRepository;

public abstract class ServiceFactory
{
	public enum FactoryType {INMEMORY, FILE};
	
	public static FactoryType type = FactoryType.INMEMORY;
	
	public static ServiceFactory instance()
	{
		if (type == FactoryType.INMEMORY)
		{
			return new ServiceInmemoryFactory();
		} else {
			return new ServiceFileFactory();
		}
	}
	
	public abstract ProductRepository createProductRepository();

}
