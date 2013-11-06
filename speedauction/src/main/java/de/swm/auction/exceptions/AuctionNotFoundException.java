package de.swm.auction.exceptions;


public class AuctionNotFoundException extends ApplicationException
{
	private static final long serialVersionUID = 1L;

	public AuctionNotFoundException(Long auctionId)
	{
		super ("Auction with id "+auctionId + " not found");
	}


}
