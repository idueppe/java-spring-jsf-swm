package de.swm.auction.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import de.swm.auction.dao.BidRepository;
import de.swm.auction.model.Bid;
import de.swm.auction.spring.framework.jdbc.MapBeanPropertySqlParameterSource;

public class BidRepositorySpringJdbcBean extends NamedParameterJdbcDaoSupport implements BidRepository
{

	private RowMapper<Bid> rowMapper = new RowMapper<Bid>()
	{

		@Override
		public Bid mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Bid bid = new Bid();
			bid.setId(rs.getLong("id"));
			bid.setAmount(rs.getDouble("amount"));
			bid.setCreated(rs.getDate("created"));
			bid.setEmail(rs.getString("email"));
			return bid;
		}
	};
	
	@Override
	public void persist(List<Bid> bids, Long auctionId)
	{
		for (Bid bid : bids)
		{
			persist(bid, auctionId);
		}
	}

	@Override
	public void persist(Bid bid, Long auctionId)
	{
		String sql = "INSERT INTO bid (id, email, amount, created, auction_id) VALUES (SPEEDAUCTION.nextVal, :email, :amount, :created, :auctionId)";
		// SqlParameterSource paramSource = new MapSqlParameterSource() //
		// .addValue("email", bid.getEmail()) //
		// .addValue("created", bid.getCreated()) //
		// .addValue("amount", bid.getAmount()) //
		// .addValue("auctionId", auctionId); //

		SqlParameterSource paramSource = new MapBeanPropertySqlParameterSource(bid).addValue("auctionId", auctionId);

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = new String[]
		{ "id" };
		getNamedParameterJdbcTemplate().update(sql, paramSource, generatedKeyHolder, keyColumnNames);
		bid.setId(generatedKeyHolder.getKey().longValue());
	}

	@Override
	public List<Bid> findByAuction(Long auctionId)
	{
		String sql = "SELECT id, email, amount, created " //
				+ "FROM bid " //
				+ "WHERE auction_id = :auctionId " //
				+ "ORDER BY amount ASC";
		SqlParameterSource paramSource = new MapSqlParameterSource("auctionId", auctionId);
		return getNamedParameterJdbcTemplate().query(sql, paramSource, rowMapper);
	}

}
