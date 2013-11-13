package de.swm.auction.dao.jdbc;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.swm.auction.dao.BidRepository;
import de.swm.auction.model.Bid;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext-dao.xml")
public class BidRepositorySpringJdbcBeanTest
{
	@Autowired
	private BidRepository repository;

	@Test
	public void testBidPersist()
	{
		Bid bid = new Bid(10.0, "test@junit.org");
		Long auctionId = -1l;
		repository.persist(bid, auctionId);
		assertNotNull(bid.getId());
	}
	
	@Test
	public void testBidFindByAuction() throws Exception
	{
		repository.persist(new Bid(1.0,"test@findbyauction"), -2L);
		
		List<Bid> bids = repository.findByAuction(-2l);
		assertNotNull(bids);
		assertFalse(bids.isEmpty());
		assertEquals("test@findbyauction",bids.get(0).getEmail());
		
	}
	

}















