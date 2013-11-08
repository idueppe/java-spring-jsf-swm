package de.swm.auction.dao;

import java.util.List;

import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

public interface ProductRepository
{

	public void persist(ProductDetails product);
	
	public void merge(ProductDetails product);

	public void delete(Long productId);

	public ProductDetails find(Long productId) throws ProductNotFoundException;

	public List<Product> findAll();

}
