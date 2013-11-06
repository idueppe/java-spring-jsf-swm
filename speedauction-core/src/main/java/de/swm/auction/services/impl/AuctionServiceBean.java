package de.swm.auction.services.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.swm.auction.api.AuctionState;
import de.swm.auction.dao.AuctionRepository;
import de.swm.auction.dtos.AuctionDTO;
import de.swm.auction.exceptions.AuctionIsExpiredException;
import de.swm.auction.exceptions.AuctionNotFoundException;
import de.swm.auction.exceptions.BidTooLowException;
import de.swm.auction.exceptions.InvalidAuctionTimeException;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Auction;
import de.swm.auction.model.Bid;
import de.swm.auction.model.Product;
import de.swm.auction.services.AuctionService;
import de.swm.auction.services.ProductService;

public class AuctionServiceBean implements AuctionService
{
	
	private AuctionRepository auctionRepository;
	
	private ProductService productService;

	@Override
	public Long registerAuction(Date startTime, Date endTime, double minimumBidding, String title, String description)
			throws ProductNotFoundException, InvalidAuctionTimeException
	{
		Long productId = productService.registerProduct(title, description);
		Product product = productService.find(productId);
		
		Auction auction = new Auction();
		auction.setStartDate(startTime);
		auction.setEndDate(endTime);
		auction.setMinimumBidding(minimumBidding);
		auction.setProduct(product);
		
		auctionRepository.persist(auction);
		
		return auction.getId();
	}

	@Override
	public AuctionDTO findAuction(Long auctionId) throws AuctionNotFoundException
	{
		return convertToDto(auctionRepository.find(auctionId));
	
	}

	private AuctionDTO convertToDto(Auction auction)
	{
		AuctionDTO dto = new AuctionDTO();
		dto.setAuctionId(auction.getId());
		dto.setHighestBid(auction.getHighestBid().getAmount());
		dto.setMinimumBidding(auction.getMinimumBidding());
		dto.setProductId(auction.getProduct().getId());
		dto.setProductTitle(auction.getProduct().getTitle());
		dto.setState(auction.getState());
		return dto;
	}
	
	private List<AuctionDTO> convertToDto(List<Auction> auctions)
	{
		List<AuctionDTO> dtos = new LinkedList<>();
		for (Auction auction : auctions)
		{
			dtos.add(convertToDto(auction));
		}
		return dtos;
	}

	@Override
	public void bid(Long auctionId, double amount, String email) throws AuctionNotFoundException, AuctionIsExpiredException,
			BidTooLowException
	{	
		Auction auction = auctionRepository.find(auctionId);
		if (auction.isActive())
		{
			auction.addBid(new Bid(amount, email));
			auctionRepository.merge(auction);
		} else {
			throw new AuctionIsExpiredException();
		}
	}

	@Override
	public List<AuctionDTO> allActiveAuctions()
	{
		return convertToDto(auctionRepository.findByState(AuctionState.ACTIVE));
	}

	@Override
	public List<AuctionDTO> allPendingAuctions()
	{
		return convertToDto(auctionRepository.findByState(AuctionState.PENDING));
	}

	@Override
	public List<AuctionDTO> allExpiredAuctions()
	{
		return convertToDto(auctionRepository.findByState(AuctionState.EXPIRED));
	}

	@Override
	public List<AuctionDTO> allWonAuctions(String email)
	{
		return null;
	}

	public void setAuctionRepository(AuctionRepository auctionRepository)
	{
		this.auctionRepository = auctionRepository;
	}
	
	public void setProductService(ProductService productService)
	{
		this.productService = productService;
	}
}

