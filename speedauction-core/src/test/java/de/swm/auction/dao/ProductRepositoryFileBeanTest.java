package de.swm.auction.dao;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

public class ProductRepositoryFileBeanTest
{

	private ProductRepository repository;

	@Before
	public void setUp()
	{
		System.out.println("---- SETUP -----");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-file.xml");
		repository = context.getBean("productRepositoryFile", ProductRepository.class);
		System.out.println(repository.getClass().getName());
	}
	
	@After
	public void tearDown()
	{
		new File("./product.data").delete();
	}
	
	@Test
	public void testPersist()
	{
		Product product = new ProductDetails();
		repository.persist(product);
		assertNotNull(product.getId());
	}
	
	@Test
	public void testFind() throws ProductNotFoundException
	{
		Product product = new ProductDetails();
		repository.persist(product);
		assertNotNull(repository.find(product.getId()));
	}

	@Test(expected=ProductNotFoundException.class)
	public void testNotFind() throws ProductNotFoundException
	{
		repository.find(-1l);
	}


	
}
