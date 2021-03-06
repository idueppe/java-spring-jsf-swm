package de.swm.auction.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;
import de.swm.auction.services.ProductService;

@Component("productService")
public class ProductServiceBean implements ProductService
{
	@Autowired
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

	@Override
	public List<? extends Product> findAll()
	{
		// FIXME idueppe - return Dto objects
		return this.productRepository.findAll();
	}
	
}
