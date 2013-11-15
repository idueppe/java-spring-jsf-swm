package de.swm.auction.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import de.swm.auction.api.AuctionState;
import de.swm.auction.dao.AuctionRepository;
import de.swm.auction.dtos.AuctionDTO;
import de.swm.auction.exceptions.AuctionNotFoundException;
import de.swm.auction.model.Auction;
import de.swm.auction.model.Product;

public class AuctionServiceBeanMockTest
{

	@Test
	public void test() throws AuctionNotFoundException
	{
		AuctionServiceBean service = new AuctionServiceBean();
		
		service.setAuctionRepository(new AuctionRepositoryMockup());
		long auctionId = 111l;
		
		AuctionDTO dto = service.findAuction(auctionId);
		
		
		assertEquals(Long.valueOf(auctionId), dto.getAuctionId());
		assertEquals("JUNIT TEST", dto.getProductTitle());
	}
	
	public static class AuctionRepositoryMockup implements AuctionRepository {

		@Override
		public Long persist(Auction auction)
		{
			return null;
		}

		@Override
		public void merge(Auction auction)
		{
			
		}

		@Override
		public void delete(Long auctionId)
		{
			
		}

		@Override
		public Auction find(Long auctionId) throws AuctionNotFoundException
		{
			Auction auction = new Auction();
			auction.setId(auctionId);
			auction.setProduct(new Product());
			auction.getProduct().setTitle("JUNIT TEST");
			auction.setEndDate(new Date());
			auction.setStartDate(new Date());
			return auction;
		}

		@Override
		public List<Auction> findByState(AuctionState state)
		{
			return null;
		}

		@Override
		public List<Auction> findByOwner(String ownerEmail)
		{
			return null;
		}

		@Override
		public List<Auction> findAll() throws AuctionNotFoundException
		{
			return null;
		}
		
	}

}
