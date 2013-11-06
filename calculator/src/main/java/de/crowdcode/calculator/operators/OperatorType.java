package de.crowdcode.calculator.operators;

import java.util.EnumSet;

public enum OperatorType
{

	ADD("+", 10), SUBTRACT("-", 10), MULTIPLY("*", 20), DIVIDE("/", 20), POWER("^", 30);

	private int precedence;
	private String symbol;

	private OperatorType(String symbol, int precedence)
	{
		this.symbol = symbol;
		this.precedence = precedence;
	}

	public int precedence()
	{
		return precedence;
	}
	
	public String symbol()
	{
		return symbol;
	}
	
	public static OperatorType fromSymbol(String symbol)
	{
		for (OperatorType op : EnumSet.allOf(OperatorType.class))
		{
			if (op.symbol().equals(symbol))
			{
				return op;
			}
		}
		throw new IllegalArgumentException("Unknown symbol "+symbol);
	}

}
