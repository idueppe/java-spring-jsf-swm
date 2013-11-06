package de.swm.auction.dao;

import java.util.List;

import de.swm.auction.api.AuctionState;
import de.swm.auction.exceptions.AuctionNotFoundException;
import de.swm.auction.model.Auction;

public interface AuctionRepository
{
	
	public Long persist(Auction auction);
	
	public void merge(Auction auction);

	public void delete(Long auctionId);

	public Auction find(Long auctionId) throws AuctionNotFoundException;
	
	public List<Auction> findByState(AuctionState state);
	
	public List<Auction> findByOwner(String ownerEmail);
	
}
