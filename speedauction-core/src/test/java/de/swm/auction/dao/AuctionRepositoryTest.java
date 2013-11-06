package de.swm.auction.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import de.swm.auction.dao.inmemory.AuctionRepositoryBean;
import de.swm.auction.model.Auction;

public class AuctionRepositoryTest
{
	
	private AuctionRepository repository = new AuctionRepositoryBean();

	@Test
	public void testPersist()
	{
		Auction auction = new Auction();
		Long auctionId = repository.persist(auction);
		assertNotNull(auctionId);
		assertEquals(auctionId,auction.getId());
	}
	
	

}
