package de.swm.auction.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.swm.auction.dao.inmemory.ProductRepositoryBean;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;
import de.swm.auction.services.impl.ProductServiceBean;

public class ProductServiceTest
{
	
	private ProductServiceBean productService = new ProductServiceBean();
	
	@Before
	public void setUp()
	{
		productService.setProductRepository(new ProductRepositoryBean());
	}

	@Test
	public void testRegisterProduct()
	{
		Long productId = productService.registerProduct("Title", "Description");
		assertNotNull(productId);
	}
	
	@Test
	public void testFindProduct() throws ProductNotFoundException
	{
		Long productId = productService.registerProduct("Title", "Description");
		Product product = productService.find(productId);
		assertNotNull(product);
		assertEquals("Title", product.getTitle());
		// TODO Assertion assertEquals("Title", product.getTitle()); ergänzen
	}
	
	@Test
	public void testFindDetails() throws ProductNotFoundException
	{
		Long productId = productService.registerProduct("Title", "Description");
		Product product = productService.find(productId);
		ProductDetails details = productService.findDetails(product);
		assertNotNull(details);
		assertEquals("Description", details.getDescription());
		// TODO Assertion assertEquals("Description", product.getDescription()); ergänzen
		
	}

}
