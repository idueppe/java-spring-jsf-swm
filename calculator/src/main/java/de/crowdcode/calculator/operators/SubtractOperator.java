package de.crowdcode.calculator.operators;

import de.crowdcode.calculator.Node;

public class SubtractOperator extends Operator
{

	public SubtractOperator(Node left, Node right)
	{
		super(left, right);
	}

	public double evaluate()
	{
		return left.evaluate() - right.evaluate();
	}

	@Override
	public String toString()
	{
		return left.toString() + " - " + right.toString();
	}
	
}
