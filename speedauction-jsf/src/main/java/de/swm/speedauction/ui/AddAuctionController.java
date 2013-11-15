package de.swm.speedauction.ui;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import de.swm.auction.exceptions.InvalidAuctionTimeException;
import de.swm.auction.exceptions.ProductNotFoundException;
import de.swm.auction.services.AuctionService;


@Controller("addAuctionController")
@Scope("session")
public class AddAuctionController implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AuctionService auctionService;

	private Date startTime;
	private Date endTime;
	private double minimumBidding;
	private String title;
	private String description;

	public String addAuction() throws ProductNotFoundException, InvalidAuctionTimeException
	{
		auctionService.registerAuction(startTime, endTime, minimumBidding, title, description);
		return "/views/auctions";
	}
	
	public String cancelAdding()
	{
		startTime = null;
		// und so weiter..
		return "/views/auctions";
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public double getMinimumBidding()
	{
		return minimumBidding;
	}

	public void setMinimumBidding(double minimumBidding)
	{
		this.minimumBidding = minimumBidding;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	

}
