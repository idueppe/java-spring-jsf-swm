package de.swm.auction.model;

public class ProductDetails extends Product
{

	private static final long serialVersionUID = 1L;
	
	private String description;

	public ProductDetails()
	{
	}
	
	public ProductDetails(String title)
	{
		setTitle(title);
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
