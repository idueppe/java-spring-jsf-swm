package de.swm.auction.api;

import static org.junit.Assert.*;

import org.junit.Test;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.dao.file.ProductRepositoryFileBean;

public class ServiceFactoryTest
{

	@Test
	public void test()
	{
		ServiceFactory.type = ServiceFactory.FactoryType.FILE;
		ProductRepository repository = ServiceFactory.instance().createProductRepository();
		assertTrue(repository instanceof ProductRepositoryFileBean);
	}

}
