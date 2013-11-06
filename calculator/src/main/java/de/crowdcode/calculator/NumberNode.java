package de.crowdcode.calculator;

public class NumberNode implements Node
{
	private double value;

	public NumberNode(double value)
	{
		this.value = value;
	}
	
	public double evaluate()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return Double.toString(value);
	}
	
}
