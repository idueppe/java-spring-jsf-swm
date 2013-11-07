package de.swm.auction.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.swm.auction.dao.file.ProductRepositoryFileBean;
import de.swm.auction.model.ProductDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-file.xml")
public class ProductRepositorySpringTest
{
	@Autowired
	private ProductRepositoryFileBean repository;

	@Test
	public void testPersist()
	{
		ProductDetails product = new ProductDetails();
		repository.persist(product);
		assertNotNull(product.getId());
	}

}
