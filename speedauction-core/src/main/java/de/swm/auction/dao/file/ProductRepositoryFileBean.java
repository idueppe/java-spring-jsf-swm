package de.swm.auction.dao.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.dao.inmemory.ProductRepositoryBean;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

public class ProductRepositoryFileBean implements ProductRepository
{

	public static final String PRODUCT_DATA_FILENAME = "/tmp/product.data";

	private ProductRepositoryBean delegate;
	private File productFile;
	
	@PostConstruct
	public void init() 
	{
		System.out.println("Bin mit File "+productFile.getName()+ " verbunden.");
		if (productFile.exists())
			readFromFile();
	}
	
	@PreDestroy
	public void close()
	{
		System.out.println("Close file now.");
	}

	public ProductRepositoryFileBean()
	{}
	
	public ProductRepositoryFileBean(ProductRepositoryBean delegate)
	{
		System.out.println("---- Construct ProductRepositoryFileBean ");
		this.delegate = delegate;
	}

	public ProductRepositoryFileBean(ProductRepositoryBean delegate, File productFile)
	{
		System.out.println("---- Construct ProductRepositoryFileBean with " + productFile.getName());
		this.delegate = delegate;
		this.productFile = productFile;
	}

	private void safeToFile()
	{
		try (FileOutputStream fos = new FileOutputStream(productFile);
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
		if (productFile.exists())
		{
			try (FileInputStream fis = new FileInputStream(productFile);
					GZIPInputStream zis = new GZIPInputStream(fis);
					ObjectInputStream ois = new ObjectInputStream(zis);)
			{
				delegate.setStore((Map<Long, Product>) ois.readObject());
			} catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void persist(ProductDetails product)
	{
		readFromFile();
		delegate.persist(product);
		safeToFile();
	}

	public void merge(ProductDetails product)
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

	public ProductRepositoryBean getDelegate()
	{
		return delegate;
	}

	public void setDelegate(ProductRepositoryBean delegate)
	{
		this.delegate = delegate;
	}

	public void setProductFile(File productFile)
	{
		this.productFile = productFile;
	}

}