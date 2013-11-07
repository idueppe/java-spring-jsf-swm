package de.swm.auction.dao.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.dao.inmemory.ProductRepositoryBean;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

public class ProductRepositoryFileBean implements ProductRepository
{

	public static final String PRODUCT_DATA_FILENAME = "/tmp/product.data";
	
	private ProductRepositoryBean delegate;
	
	public ProductRepositoryFileBean()
	{}
	
	public ProductRepositoryFileBean(ProductRepositoryBean delegate)
	{
		this.delegate = delegate;
	}

	private void safeToFile()
	{
		try (FileOutputStream fos = new FileOutputStream(PRODUCT_DATA_FILENAME);
				GZIPOutputStream zos = new GZIPOutputStream(fos);
				ObjectOutputStream oos = new ObjectOutputStream(zos);)
		{
			oos.writeObject(delegate.getStore());
			oos.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void readFromFile()
	{
		try (FileInputStream fis = new FileInputStream(PRODUCT_DATA_FILENAME); 
				GZIPInputStream zis = new GZIPInputStream(fis);
				ObjectInputStream ois = new ObjectInputStream(zis); 
		)
		{
			delegate.setStore((Map<Long, Product>) ois.readObject());
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void persist(Product product)
	{
		readFromFile();
		delegate.persist(product);
		safeToFile();
	}

	public void merge(Product product)
	{
		readFromFile();
		delegate.merge(product);
		safeToFile();
	}

	public void delete(Long productId)
	{
		readFromFile();
		delegate.delete(productId);
		safeToFile();
	}

	public ProductDetails find(Long productId) throws ProductNotFoundException
	{
		readFromFile();
		return delegate.find(productId);
	}

	public List<Product> findAll()
	{
		readFromFile();
		return delegate.findAll();
	}

}