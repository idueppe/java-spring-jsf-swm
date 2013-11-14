package de.swm.auction.dao.jdbc;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.swm.auction.dao.AuctionRepository;
import de.swm.auction.exceptions.BidTooLowException;
import de.swm.auction.model.Auction;
import de.swm.auction.model.Bid;
import de.swm.auction.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext-dao.xml")
public class AuctionRepositorySpringJdbcBeanTest
{
	
	@Autowired
	private AuctionRepository repository;

	@Test
	public void testPersist() throws BidTooLowException
	{
		Auction auction = buildDefaultAuction();
		auction.setProduct(buildDefaultProduct());
		
		// set properties here
		repository.persist(auction);
		assertNotNull(auction.getId());
		assertNotNull(auction.getHighestBid().getId());
	}
	
	@Test
	public void testFind() throws Exception
	{
		// given
		Auction auction = buildDefaultAuction();
		auction.setProduct(buildDefaultProduct());
		repository.persist(auction);
		// when
		Auction found = repository.find(auction.getId());
		// then
		assertEquals(6.0, found.getHighestBid().getAmount(), 0.001);
		
	}

	private Product buildDefaultProduct()
	{
		Product product = new Product();
		product.setId(-10L);
		product.setTitle("unit product");
		return product;
	}

	private Auction buildDefaultAuction() throws BidTooLowException
	{
		Auction auction = new Auction();
		auction.setMinimumBidding(1.0);
		auction.setOwner("unit@test");
		auction.setStartDate(new Date(System.currentTimeMillis()-10000));
		auction.setEndDate(new Date(System.currentTimeMillis()+10000));
		auction.addBid(new Bid(5.0,"bid_one@junit"));
		auction.addBid(new Bid(6.0,"bid_two@junit"));
		return auction;
	}
	

}
