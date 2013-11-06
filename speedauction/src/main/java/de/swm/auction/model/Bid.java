package de.swm.auction.model;

import java.util.Date;

public class Bid
{
	private double amount;

	private Date created;

	private String email;
	
	public Bid() {}

	public Bid(double amount, String email)
	{
		this.amount = amount;
		this.email = email;
		this.created = new Date();
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public boolean isHigherThan(Bid bid)
	{
		return bid == null || this.amount > bid.amount;
	}

	public boolean isHigherThan(double amount)
	{
		return this.amount > amount;
	}
}
