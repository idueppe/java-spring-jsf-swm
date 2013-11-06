package de.swm.auction.services.impl;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;
import de.swm.auction.services.ProductService;

public class ProductServiceBean implements ProductService
{
	
	private ProductRepository productRepository;
	
	@Override
	public Long registerProduct(String title, String description)
	{
		ProductDetails product = new ProductDetails();
		product.setTitle(title);
		product.setDescription(description);
		productRepository.persist(product);
		return product.getId();
	}

	@Override
	public ProductDetails findDetails(Product product) throws ProductNotFoundException
	{
		return productRepository.find(product.getId());
	}

	@Override
	public Product find(Long productId) throws ProductNotFoundException
	{
		return productRepository.find(productId);
	}

	public void setProductRepository(ProductRepository productRepository)
	{
		this.productRepository = productRepository;
	}
	
}
