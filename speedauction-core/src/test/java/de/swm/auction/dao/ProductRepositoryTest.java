package de.swm.auction.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.swm.auction.dao.inmemory.ProductRepositoryBean;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.ProductDetails;

public class ProductRepositoryTest
{
	
	private ProductRepository productRepository = new ProductRepositoryBean(); 
	

	@Test
	public void testThatProductIdIsSetAfterPersist()
	{
		ProductDetails product = new ProductDetails();
		assertNull("Id should be null before a domain object is persisted.",product.getId());
		productRepository.persist(product);
		assertNotNull("Id must not be null after a domain object is persisted.",product.getId());
	}
	
	@Test
	public void testThatProductCanBeFoundAfterPersist() throws ProductNotFoundException
	{
		ProductDetails product = new ProductDetails();
		productRepository.persist(product);
		ProductDetails found = productRepository.find(product.getId());
		assertEquals(product, found);
	}
	
	@Test(expected=ProductNotFoundException.class)
	public void testThatIsDeletedAndCannotBeFoundAgain() throws ProductNotFoundException
	{
		ProductDetails product = new ProductDetails();
		productRepository.persist(product);
		productRepository.delete(product.getId());
		productRepository.find(product.getId());
	}
	
	@Test
	public void testFindAllResultSize() throws Exception
	{
		assertNotNull(productRepository.findAll());
		assertTrue(productRepository.findAll().isEmpty());
		productRepository.persist(new ProductDetails());
		assertFalse(productRepository.findAll().isEmpty());
		productRepository.persist(new ProductDetails());
		assertEquals(2, productRepository.findAll().size());
	}
	
	

}
