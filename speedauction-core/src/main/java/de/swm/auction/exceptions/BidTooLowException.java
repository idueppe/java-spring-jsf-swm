package de.swm.auction.exceptions;

import de.swm.auction.model.Bid;



public class BidTooLowException extends ApplicationException
{

	public BidTooLowException(Bid bid, double expectedAmount)
	{
		super("The bid "+bid.getAmount()+" is too low. The minimum bid amount is "+expectedAmount);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
