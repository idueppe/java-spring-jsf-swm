package de.swm.auction.exceptions;


public class ProductNotFoundException extends ApplicationException
{
	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(Long productId)
	{
		super("Product with Id "+productId+" not found.");
	}

}
