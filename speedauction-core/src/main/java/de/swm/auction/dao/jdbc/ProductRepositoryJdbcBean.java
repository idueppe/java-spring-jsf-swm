package de.swm.auction.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.dao.RepositoryUtils;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.exceptions.SystemException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

public class ProductRepositoryJdbcBean implements ProductRepository
{

	private final static String INSERT_SQL = "INSERT INTO product (id, title, description) VALUES (?,?,?)";
	private final static String UPDATE_SQL = "UPDATE product set title=?, description=? WHERE id=?";
	private final static String DELETE_SQL = "DELETE FROM product WHERE id = ?";
	private final static String FIND_BY_ID = "SELECT id,title,description FROM product WHERE id=?";
	private final static String FIND_ALL = "SELECT id, title, description FROM product";

	private DataSource dataSource;

	@Override
	public void persist(ProductDetails product)
	{
		try (Connection connection = dataSource.getConnection(); //
				PreparedStatement pStmt = connection.prepareStatement(INSERT_SQL);)
		{
			product.setId(RepositoryUtils.nextId());
			pStmt.setLong(1, product.getId());
			pStmt.setString(2, product.getTitle());
			pStmt.setString(3, product.getDescription());
			pStmt.execute();
		} catch (SQLException e)
		{
			System.out.println("--|" + e.getErrorCode());
			throw new SystemException("Coudn't persist product.", e);
		}
	}

	@Override
	public void merge(ProductDetails product) throws ProductNotFoundException
	{
		try (Connection connection = dataSource.getConnection(); //
				PreparedStatement pStmt = connection.prepareStatement(UPDATE_SQL);)
		{
			pStmt.setString(1, product.getTitle());
			pStmt.setString(2, product.getDescription());
			pStmt.setLong(3, product.getId());
			if (pStmt.executeUpdate() == 0)
			{
				throw new ProductNotFoundException(product.getId());
			}
		} catch (SQLException e)
		{
			throw new SystemException("Coudn't persist product.", e);
		}
	}

	@Override
	public void delete(Long productId) throws ProductNotFoundException
	{
		try (Connection connection = dataSource.getConnection(); //
				PreparedStatement pStmt = connection.prepareStatement(DELETE_SQL);)
		{
			pStmt.setLong(1, productId);
			if (pStmt.executeUpdate() != 1)
			{
				throw new ProductNotFoundException(productId);
			}	
		} catch (SQLException e)
		{
			throw new SystemException("Couldn't delete product with id " + productId, e);
		}
	}

	@Override
	public ProductDetails find(Long productId) throws ProductNotFoundException
	{
		try (Connection connection = dataSource.getConnection(); //
				PreparedStatement pStmt = connection.prepareStatement(FIND_BY_ID);)
		{
			pStmt.setLong(1, productId);
			try (ResultSet resultSet = pStmt.executeQuery())
			{
				if (resultSet.next())
				{
					ProductDetails buildProductDetails = buildProductDetails(resultSet);
					if (!resultSet.next())
					{
						return buildProductDetails;
					}
				}
			}
		} catch (SQLException e)
		{
			throw new SystemException("Couldn't delete product with id " + productId, e);
		}
		throw new ProductNotFoundException(productId);
	}

	private ProductDetails buildProductDetails(ResultSet resultSet) throws SQLException
	{
		ProductDetails product = new ProductDetails();
		product.setId(resultSet.getLong("id"));
		product.setTitle(resultSet.getString("title"));
		product.setDescription(resultSet.getString("description"));
		return product;
	}

	@Override
	public List<Product> findAll()
	{
		List<Product> products = new LinkedList<>();
		try (Connection connection = dataSource.getConnection(); //
				PreparedStatement pStmt = connection.prepareStatement(FIND_ALL);
				ResultSet resultSet = pStmt.executeQuery())
		{
			while (resultSet.next())
			{
				products.add(buildProductDetails(resultSet));
			}

		} catch (SQLException e)
		{
			throw new SystemException("Couldn't read products.", e);
		}
		return products;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

}
