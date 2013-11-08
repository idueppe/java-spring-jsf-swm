package de.swm.auction.dao.inmemory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import de.swm.auction.api.AuctionState;
import de.swm.auction.dao.AuctionRepository;
import de.swm.auction.dao.RepositoryUtils;
import de.swm.auction.exceptions.AuctionNotFoundException;
import de.swm.auction.model.Auction;

@Component()
public class AuctionRepositoryBean implements AuctionRepository
{
	
	private Map<Long, Auction> store = new HashMap<>();

	@Override
	public Long persist(Auction auction)
	{
		auction.setId(RepositoryUtils.nextId());
		store.put(auction.getId(), auction);
		return auction.getId();
	}

	@Override
	public void merge(Auction auction)
	{
		store.put(auction.getId(), auction);
	}

	@Override
	public void delete(Long auctionId)
	{
		store.remove(auctionId);

	}

	@Override
	public Auction find(Long auctionId) throws AuctionNotFoundException
	{
		if (!store.containsKey(auctionId))
			throw new AuctionNotFoundException(auctionId);
		return store.get(auctionId);
	}

	@Override
	public List<Auction> findByState(AuctionState state)
	{
		List<Auction> found = new LinkedList<>();
		
		for (Auction auction : store.values())
		{
			if (auction.getState() == state)
			{
				found.add(auction);
			}
		}
		return found;
	}

	@Override
	public List<Auction> findByOwner(String ownerEmail)
	{
		List<Auction> found = new LinkedList<>();
		
		for (Auction auction : store.values())
		{
			if (auction.getOwner().equals(ownerEmail))
			{
				found.add(auction);
			}
		}
		return found;
	}

}
