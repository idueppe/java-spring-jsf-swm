package de.swm.auction.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.ProductDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-dao.xml")
public class ProductRepositorySpringDaoJdbcTest
{
	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testProductPersist() throws ProductNotFoundException
	{
		ProductDetails product = buildDefaultProduct();
		productRepository.persist(product);

		assertNotNull(product.getId());

		product.setDescription("CHANGED DESCRIPTION");
		productRepository.merge(product);
	}

	@Test
	public void testPersistAndFind() throws Exception
	{
		ProductDetails product = buildDefaultProduct();
		productRepository.persist(product);
		assertNotNull(product.getId());

		ProductDetails found = productRepository.find(product.getId());
		assertEquals(found, product);
	}

	@Test(expected=ProductNotFoundException.class)
	public void testProductNotFoundExceptionOnFind() throws ProductNotFoundException
	{
		productRepository.find(-1000L);
	}

	@Test
	public void testFindAll() throws Exception
	{
		assertNotNull(productRepository.findAll());
		assertFalse(productRepository.findAll().isEmpty());
	}
	
	private ProductDetails buildDefaultProduct()
	{
		ProductDetails product = new ProductDetails();
		product.setTitle("JUNIT DAO TEST");
		product.setDescription("JUNIT TEST for DAO persist");
		return product;
	}

}
