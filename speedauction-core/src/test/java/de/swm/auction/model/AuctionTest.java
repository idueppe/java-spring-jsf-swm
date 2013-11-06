package de.swm.auction.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.swm.auction.exceptions.BidTooLowException;

public class AuctionTest
{
	
	private Date before60Seconds = new Date(System.currentTimeMillis() - 60000);
	private Date before30Seconds = new Date(System.currentTimeMillis() - 30000);
	private Date in30Seconds = new Date(System.currentTimeMillis() + 30000);
	private Date in60Seconds = new Date(System.currentTimeMillis() + 60000);
	
	private Auction auction;
	
	@Before
	public void setUp()
	{
		auction = new Auction();
	}
	
	@Test
	public void testAddHigherBidding() throws BidTooLowException
	{
		auction.addBid(buildBid(10.0));
		auction.addBid(buildBid(12.0));
		assertEquals(12.0, auction.getHighestBid().getAmount(),0.00001);
		
	}
	
	@Test(expected=BidTooLowException.class)
	public void testAddLowerBidding() throws BidTooLowException
	{
		auction.addBid(buildBid(13.0));
		auction.addBid(buildBid(12.0));
		
	}
	
	@Test
	public void testPending()
	{
		auction.setStartDate(in30Seconds);
		auction.setEndDate(in60Seconds);
		
		assertTrue(auction.isPending());
		assertFalse(auction.isActive());
		assertFalse(auction.isExpired());
	}
	
	@Test
	public void testActive()
	{
		auction.setStartDate(before60Seconds);
		auction.setEndDate(in30Seconds);
		
		assertFalse(auction.isPending());
		assertTrue(auction.isActive());
		assertFalse(auction.isExpired());
	}
	
	@Test
	public void testExpired()
	{
		auction.setStartDate(before60Seconds);
		auction.setEndDate(before30Seconds);
		
		assertFalse(auction.isPending());
		assertFalse(auction.isActive());
		assertTrue(auction.isExpired());
	}
	
	private Bid buildBid(double amount)
	{
		Bid bid = new Bid();
		bid.setAmount(amount);
		bid.setCreated(new Date());
		bid.setEmail("test@junit");
		return bid;
	}
}
