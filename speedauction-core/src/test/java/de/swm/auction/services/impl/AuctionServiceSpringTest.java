package de.swm.auction.services.impl;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.swm.auction.exceptions.InvalidAuctionTimeException;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.services.AuctionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext-tx.xml")
@Transactional
public class AuctionServiceSpringTest
{
	
	@Autowired
	private AuctionService auctionService;

	@Test
	public void testRegisterAuction() throws Exception
	{
		registerAuction();
	}
	
	@Test
	public void testRegisterAuctionWithoutTX() throws Exception
	{
		Date startTime = new Date(System.currentTimeMillis()+1000);
		Date endTime = new Date(System.currentTimeMillis()+8000);
		double minimumBidding = 10.0;
		String title = "Apple MacBook Pro";
		String description = "Beschreibung";
		auctionService.registerAuctionWithoutTX(startTime, endTime, minimumBidding, title, description);
	}


	@Test
	@Rollback(value=false)
	public void testRegisterAuction2() throws Exception
	{
		registerAuction();
	}
	
	private void registerAuction() throws ProductNotFoundException, InvalidAuctionTimeException
	{
		Date startTime = new Date(System.currentTimeMillis()+1000);
		Date endTime = new Date(System.currentTimeMillis()+8000);
		double minimumBidding = 10.0;
		String title = "Apple MacBook Pro";
		String description = "Beschreibung";
		auctionService.registerAuction(startTime, endTime, minimumBidding, title, description);
	}

}




