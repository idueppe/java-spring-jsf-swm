package de.swm.auction.dao.inmemory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

public class ProductRepositoryBean implements ProductRepository
{
	
	private Map<Long, Product> store = new HashMap<>();

	@Override
	public void persist(Product product)
	{
		product.setId(RepositoryUtils.nextId());
		store.put(product.getId(), product);
	}

	@Override
	public void merge(Product product)
	{
		store.put(product.getId(), product);
	}

	@Override
	public void delete(Long productId)
	{
		store.remove(productId);
	}

	@Override
	public ProductDetails find(Long productId) throws ProductNotFoundException
	{
		if (!store.containsKey(productId))
		{
			throw new ProductNotFoundException(productId);
		}
		return (ProductDetails) store.get(productId);
	}
	@Override
	public List<Product> findAll()
	{
		return new LinkedList<>(store.values());
	}

}