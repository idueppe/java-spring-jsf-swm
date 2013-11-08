package de.swm.auction.dao.jdbc;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.swm.auction.dao.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-db.xml")
public class ProductRepositoryJdbcTest
{
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void testSelectProducts() throws SQLException
	{
	}
	
}
