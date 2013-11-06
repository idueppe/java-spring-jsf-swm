package de.swm.auction.dtos;

import de.swm.auction.api.AuctionState;

public class AuctionDTO
{

	private Long auctionId;

	private Long productId;
	
	private String productTitle;
	
	private AuctionState state;
	
	private double highestBid;
	
	private double minimumBidding; 
	
	
	public double getMinimumBidding()
	{
		return minimumBidding;
	}

	public void setMinimumBidding(double minimumBidding)
	{
		this.minimumBidding = minimumBidding;
	}

	public Long getAuctionId()
	{
		return auctionId;
	}

	public void setAuctionId(Long auctionId)
	{
		this.auctionId = auctionId;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public String getProductTitle()
	{
		return productTitle;
	}

	public void setProductTitle(String productTitle)
	{
		this.productTitle = productTitle;
	}

	public AuctionState getState()
	{
		return state;
	}

	public void setState(AuctionState state)
	{
		this.state = state;
	}

	public double getHighestBid()
	{
		return highestBid;
	}

	public void setHighestBid(double highestBid)
	{
		this.highestBid = highestBid;
	}
}
