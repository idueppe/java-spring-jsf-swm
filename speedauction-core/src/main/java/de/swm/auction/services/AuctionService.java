package de.swm.auction.services;

import java.util.Date;
import java.util.List;

import de.swm.auction.dtos.AuctionDTO;
import de.swm.auction.exceptions.AuctionIsExpiredException;
import de.swm.auction.exceptions.AuctionNotFoundException;
import de.swm.auction.exceptions.BidTooLowException;
import de.swm.auction.exceptions.InvalidAuctionTimeException;
import de.swm.auction.exceptions.ProductNotFoundException;

public interface AuctionService
{

	public Long registerAuction(Date startTime, Date endTime, double minimumBidding, String title, String description) throws ProductNotFoundException, InvalidAuctionTimeException;
	
	public AuctionDTO findAuction(Long auctionId) throws AuctionNotFoundException;
	
	public void bid(Long auctionId, double amount, String email) throws AuctionNotFoundException, AuctionIsExpiredException, BidTooLowException;
	
	public List<AuctionDTO> allActiveAuctions();
	
	public List<AuctionDTO> allPendingAuctions();
	
	public List<AuctionDTO> allExpiredAuctions();
	
	public List<AuctionDTO> allWonAuctions(String email);
	
}
