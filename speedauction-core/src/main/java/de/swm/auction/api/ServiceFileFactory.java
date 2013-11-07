package de.swm.auction.api;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.dao.file.ProductRepositoryFileBean;
import de.swm.auction.dao.inmemory.ProductRepositoryBean;

public class ServiceFileFactory extends ServiceFactory
{

	@Override
	public ProductRepository createProductRepository()
	{
		return new ProductRepositoryFileBean(new ProductRepositoryBean());
	}

}
