package de.swm.auction.dao;

import java.util.List;

import de.swm.auction.model.Bid;

public interface BidRepository
{
	public void persist(Bid bid, Long auctionId);

	public void persist(List<Bid> bids, Long auctionId);
	
	public List<Bid> findByAuction(Long auctionId);
	

}
