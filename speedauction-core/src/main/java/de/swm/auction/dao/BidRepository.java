package de.swm.auction.dao;

import java.util.List;

import de.swm.auction.model.Bid;

public interface BidRepository
{
	void persist(Bid bid, Long auctionId);

	public List<Bid> findByAuction(Long auctionId);

}
