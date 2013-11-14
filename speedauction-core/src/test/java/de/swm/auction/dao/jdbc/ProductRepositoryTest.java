package de.swm.auction.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.ProductDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-db.xml")
//@ContextConfiguration({"/applicationContext-db.xml","/applicationContext-db-test.xml"})
public class ProductRepositoryTest
{

	@Autowired
	private ProductRepository productRepository; 

	@Test
	public void testThatProductIdIsSetAfterPersist()
	{
		ProductDetails product = new ProductDetails();
		product.setTitle("title");
		assertNull("Id should be null before a domain object is persisted.",product.getId());
		productRepository.persist(product);
		assertNotNull("Id must not be null after a domain object is persisted.",product.getId());
	}
	
	@Test
	public void testThatProductCanBeFoundAfterPersist() throws ProductNotFoundException
	{
		ProductDetails product = new ProductDetails();
		product.setTitle("title");
		productRepository.persist(product);
		ProductDetails found = productRepository.find(product.getId());
		assertEquals(product, found);
	}
	
	@Test(expected=ProductNotFoundException.class)
	public void testThatIsDeletedAndCannotBeFoundAgain() throws ProductNotFoundException
	{
		ProductDetails product = new ProductDetails();
		product.setTitle("title");
		productRepository.persist(product);
		productRepository.delete(product.getId());
		productRepository.find(product.getId());
	}
	
	@Test
	public void testFindAllResultSize() throws Exception
	{
		int numberOfProducts = productRepository.findAll().size();
		assertNotNull(productRepository.findAll());
		productRepository.persist(new ProductDetails("junit@test.title"));
		assertFalse(productRepository.findAll().isEmpty());
		productRepository.persist(new ProductDetails("junit@test.title"));
		assertEquals(numberOfProducts+2, productRepository.findAll().size());
	}
	
	

}
