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

import de.swm.auction.api.AuctionState;
import de.swm.auction.dao.AuctionRepository;
import de.swm.auction.dao.BidRepository;
import de.swm.auction.exceptions.AuctionNotFoundException;
import de.swm.auction.exceptions.BidTooLowException;
import de.swm.auction.exceptions.SystemException;
import de.swm.auction.model.Auction;
import de.swm.auction.model.Bid;
import de.swm.auction.model.Product;

public class AuctionRepositorySpringJdbcBean extends NamedParameterJdbcDaoSupport implements AuctionRepository
{
	private BidRepository bidRepository;

	private RowMapper<Auction> auctionRowMapper = new RowMapper<Auction>()
	{

		@Override
		public Auction mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Auction auction = new Auction();
			auction.setId(rs.getLong("id"));
			auction.setStartDate(rs.getDate("startDate"));
			auction.setEndDate(rs.getDate("endDate"));
			auction.setOwner(rs.getString("owner"));
			auction.setMinimumBidding(rs.getDouble("minimum_bid"));
			auction.setProduct(new Product(rs.getLong("product_id")));
			return auction;
		}

	};

	@Override
	public Long persist(Auction auction)
	{

		String sql = "INSERT INTO Auction (id, product_id, startDate, endDate, owner, minimum_bid)"
				+ " VALUES (SPEEDAUCTION.nextVal, :productId, :startDate, :endDate, :owner, :minimumBid)";
		SqlParameterSource params = new MapSqlParameterSource() //
				.addValue("productId", auction.getProduct().getId()) //
				.addValue("startDate", auction.getStartDate()) //
				.addValue("endDate", auction.getEndDate()) //
				.addValue("owner", auction.getOwner()) //
				.addValue("minimumBid", auction.getMinimumBidding()); //
		KeyHolder keyholder = new GeneratedKeyHolder();
		String[] keyColumns = new String[]
		{ "id" };
		getNamedParameterJdbcTemplate().update(sql, params, keyholder, keyColumns);

		auction.setId(keyholder.getKey().longValue());

		persistBids(auction);

		return auction.getId();
	}

	private void persistBids(Auction auction)
	{
		for (Bid bid : auction.getBids())
		{
			bidRepository.persist(bid, auction.getId());
		}
	}

	@Override
	public void merge(Auction auction)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Long auctionId)
	{
		throw new RuntimeException("Don't do this ...");
	}

	@Override
	public Auction find(Long auctionId) throws AuctionNotFoundException
	{
		String sql = "SELECT id, product_id, startdate, enddate, owner, minimum_bid FROM auction WHERE id = :id";
		SqlParameterSource params = new MapSqlParameterSource("id", auctionId);
		Auction auction = getNamedParameterJdbcTemplate().queryForObject(sql, params, auctionRowMapper);

		loadBidsOfAuction(auction);

		return auction;
	}

	private void loadBidsOfAuction(Auction auction)
	{
		List<Bid> bids = bidRepository.findByAuction(auction.getId());
		try
		{
			addBidsToAuction(auction, bids);
		} catch (BidTooLowException e)
		{
			throw new SystemException("Couldn't load auction with id " + auction.getId(), e);
		}
	}

	private void addBidsToAuction(Auction auction, List<Bid> bids) throws BidTooLowException
	{
		for (Bid bid : bids)
		{
			auction.addBid(bid);
		}
	}

	@Override
	public List<Auction> findByState(AuctionState state)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Auction> findByOwner(String ownerEmail)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setBidRepository(BidRepository bidRepository)
	{
		this.bidRepository = bidRepository;
	}

	@Override
	protected void checkDaoConfig()
	{
		super.checkDaoConfig();
		if (bidRepository == null)
		{
			throw new IllegalArgumentException("Missing reference to BidRepository.");
		}
	}
}
