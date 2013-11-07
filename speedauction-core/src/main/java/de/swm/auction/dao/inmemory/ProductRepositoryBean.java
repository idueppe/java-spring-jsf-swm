package de.swm.auction.dao.inmemory;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

@Component()
public class ProductRepositoryBean implements ProductRepository
{
	private Map<Long, Product> store = new HashMap<>();
	
	public ProductRepositoryBean()
	{
		System.out.println("---- Construct ProductRepositoryBean");
	}

	@Override
	public void persist(Product product)
	{
		product.setId(RepositoryUtils.nextId());
		store.put(product.getId(), product);
	}

	@Override
	public void merge(Product product)
	{
		Product previous = store.put(product.getId(), product);
		if (previous != null)
		{
			System.out.println("Removed "+previous);
		}
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
		// FIXME wir wissen nicht ob es wirklich ein ProductDetails Objekt ist.
		return (ProductDetails) store.get(productId);
	}
	@Override
	public List<Product> findAll()
	{
		return new LinkedList<>(store.values());
	}
	
	public Map<Long, Product> getStore()
	{
		return Collections.unmodifiableMap(store);
	}

	public void setStore(Map<Long, Product> store)
	{
		this.store.clear();
		this.store.putAll(store);
	}


}
