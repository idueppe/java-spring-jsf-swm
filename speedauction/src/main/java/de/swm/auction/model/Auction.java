package de.swm.auction.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.swm.auction.api.AuctionState;
import de.swm.auction.exceptions.BidTooLowException;

public class Auction
{

	private Long id;
	
	private Date startDate;
	
	private Date endDate;
	
	private String owner;
	
	private Bid highestBid = new Bid(0.0, "");
	
	private List<Bid> bids = new LinkedList<>();
	
	private double minimumBidding;
	
	private Product product;
	
	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public double getMinimumBidding()
	{
		return minimumBidding;
	}

	public void setMinimumBidding(double minimumBidding)
	{
		this.minimumBidding = minimumBidding;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void addBid(Bid bid) throws BidTooLowException
	{
		if (bid.isHigherThan(highestBid) && bid.getAmount() >= minimumBidding)
		{
			bids.add(0, bid);
			highestBid = bid;
		} else {
			throw new BidTooLowException(bid, highestBid.getAmount());
		}
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public boolean isActive()
	{
		return !isExpired() && !isPending();
	}
	
	public boolean isExpired()
	{
		return endDate.before(new Date());
	}
	
	public boolean isPending()
	{
		return startDate.after(new Date());
	}

	public Bid getHighestBid()
	{
		return highestBid;
	}

	public AuctionState getState()
	{
		if (isPending())
		{
			return AuctionState.PENDING;
		} else if (isActive())
		{
			return AuctionState.ACTIVE;
		} else
		{
			return AuctionState.EXPIRED;
		}
	}



}
