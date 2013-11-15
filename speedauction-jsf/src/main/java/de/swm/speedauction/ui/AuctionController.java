package de.swm.speedauction.ui;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import de.swm.auction.dtos.AuctionDTO;
import de.swm.auction.exceptions.AuctionNotFoundException;
import de.swm.auction.services.AuctionService;


@Controller("auctionController")
@Scope("session")
public class AuctionController implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AuctionService auctionService;
	
	public List<AuctionDTO> getAuctions() throws AuctionNotFoundException
	{
		return auctionService.allAuctions();
	}
	
}
