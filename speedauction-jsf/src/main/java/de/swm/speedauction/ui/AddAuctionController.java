package de.swm.speedauction.ui;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

	public String startAddingAuction()
	{
		System.out.println("... starting adding");
		startTime = new Date();
		endTime = new Date(System.currentTimeMillis()+100000);
		title = "";
		description = "";
		minimumBidding = 1.0;
		
		return "startAddingAuction";
	}
	
	public String addAuction() throws ProductNotFoundException, InvalidAuctionTimeException
	{
		if (startTime.before(new Date()) || startTime.after(endTime))
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zeiten sind umgültig", "...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
		
		Long auctionId = auctionService.registerAuction(startTime, endTime, minimumBidding, title, description);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Add Auction with Id"+auctionId, "blah blub");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
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
