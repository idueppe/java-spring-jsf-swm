package de.swm.auction.api;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.dao.inmemory.ProductRepositoryBean;

public class ServiceInmemoryFactory extends ServiceFactory
{

	@Override
	public ProductRepository createProductRepository()
	{
		return new ProductRepositoryBean();
	}

}
