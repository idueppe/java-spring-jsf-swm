package de.swm.auction.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;


public class ProductNotFoundException extends ApplicationException
{
	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(Long productId)
	{
		super("Product with Id "+productId+" not found.");
	}

	public ProductNotFoundException(Long productId, EmptyResultDataAccessException ex)
	{
		super("Product with Id "+productId+" not found.", ex);
	}

}
