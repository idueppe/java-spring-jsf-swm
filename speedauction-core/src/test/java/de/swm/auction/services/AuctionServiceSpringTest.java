package de.swm.auction.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.swm.auction.api.AuctionState;
import de.swm.auction.dtos.AuctionDTO;
import de.swm.auction.exceptions.ApplicationException;
import de.swm.auction.exceptions.AuctionIsExpiredException;
import de.swm.auction.exceptions.AuctionNotFoundException;
import de.swm.auction.exceptions.BidTooLowException;

public class AuctionServiceSpringTest
{

	private AuctionService auctionService;
	
	@Before
	public void setUp()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		auctionService = context.getBean("auctionService", AuctionService.class);
	}

	@Test
	public void testRegisterService() throws ApplicationException
	{
		Date in10Seconds = new Date(System.currentTimeMillis() + 10000);
		Date in30Seconds = new Date(System.currentTimeMillis() + 30000);

		Long auctionId = auctionService.registerAuction(in10Seconds, in30Seconds, 10.0, "TITLE", "DESCRIPTION");
		AuctionDTO auction = auctionService.findAuction(auctionId);

		assertNotNull(auction);
		assertNotNull(auction.getAuctionId());
		assertNotNull(auction.getProductId());
		assertEquals("TITLE", auction.getProductTitle());
		assertEquals(AuctionState.PENDING, auction.getState());

		assertEquals(10.0, auction.getMinimumBidding(), 0.001);
		assertEquals(0.0, auction.getHighestBid(), 0.001);
	}

	@Test
	public void testBiddingProcess() throws ApplicationException
	{
		Date in1Seconds = new Date(System.currentTimeMillis() + 100);
		Date in4Seconds = new Date(System.currentTimeMillis() + 500);

		Long auctionId = auctionService.registerAuction(in1Seconds, in4Seconds, 10.0, "TITLE", "DESCRIPTION");
		AuctionDTO auctionDto = auctionService.findAuction(auctionId);

		assertEquals(1, auctionService.allPendingAuctions().size());
		// poll until auction is active
		while (auctionDto.getState() == AuctionState.PENDING)
		{
			auctionDto = auctionService.findAuction(auctionId);
		}

		assertEquals(1, auctionService.allActiveAuctions().size());
		// make bids until auction expires
		while (auctionDto.getState() == AuctionState.ACTIVE)
		{
			double amount = Math.max(auctionDto.getHighestBid() + 0.5, auctionDto.getMinimumBidding());
			safeBid(auctionId, amount);
			auctionDto = auctionService.findAuction(auctionId);
		}

		// check results
		assertEquals(1, auctionService.allExpiredAuctions().size());
		
		assertEquals(AuctionState.EXPIRED, auctionDto.getState());
		assertTrue(auctionDto.getMinimumBidding() < auctionDto.getHighestBid());
	}

	private void safeBid(Long auctionId, double amount) throws AuctionNotFoundException, BidTooLowException
	{
		try
		{
			auctionService.bid(auctionId, amount, "@" + System.currentTimeMillis());
		} catch (AuctionIsExpiredException ex)
		{
			// this can happen due to test structure
		}
	}

}
