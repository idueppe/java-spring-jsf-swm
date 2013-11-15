package de.swm.auction.services;

import java.util.List;

import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

public interface ProductService
{

	public Long registerProduct(String title, String description);

	public ProductDetails findDetails(Product product) throws ProductNotFoundException;

	public Product find(Long product) throws ProductNotFoundException;

	public List<? extends Product> findAll();

}
