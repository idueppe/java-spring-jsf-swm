package de.swm.auction.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import de.swm.auction.dao.ProductRepository;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.model.Product;
import de.swm.auction.model.ProductDetails;

public class ProductRepositorySpringJdbcBean extends NamedParameterJdbcDaoSupport implements ProductRepository
{

	private RowMapper<ProductDetails> productDetailsRowMapper = new RowMapper<ProductDetails>()
	{
		@Override
		public ProductDetails mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			ProductDetails product = new ProductDetails();
			product.setId(rs.getLong("id"));
			product.setTitle(rs.getString("title"));
			product.setDescription(rs.getString("description"));
			return product;
		}
	};

	@Override
	public void persist(ProductDetails product)
	{
		String sql = "INSERT INTO Product (id, title, description) VALUES (SpeedAuction.nextVal, :title, :description)";

		SqlParameterSource paramSource = new MapSqlParameterSource() //
				.addValue("title", product.getTitle()) //
				.addValue("description", product.getDescription());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		getNamedParameterJdbcTemplate().update(sql, paramSource, keyHolder, new String[]
		{ "id" });

		product.setId(keyHolder.getKey().longValue());
	}

	@Override
	public void merge(ProductDetails product) throws ProductNotFoundException
	{
		String sql = "UPDATE Product SET title = :title, description = :description WHERE id = :id";
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(product);
		getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	@Override
	public void delete(Long productId) throws ProductNotFoundException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ProductDetails find(Long productId) throws ProductNotFoundException
	{
		try
		{
			String sql = "SELECT id, title, description FROM product WHERE id = :id";
			SqlParameterSource paramSource = new MapSqlParameterSource("id", productId);
			return getNamedParameterJdbcTemplate().queryForObject(sql, paramSource, productDetailsRowMapper);
		} catch (EmptyResultDataAccessException ex)
		{
			throw new ProductNotFoundException(productId, ex);
		}
	}

	@Override
	public List<? extends Product> findAll()
	{
		String sql = "SELECT id, title, description FROM product";
		return getNamedParameterJdbcTemplate().query(sql, productDetailsRowMapper);
	}

}
