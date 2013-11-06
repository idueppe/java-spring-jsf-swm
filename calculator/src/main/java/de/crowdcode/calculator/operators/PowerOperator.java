package de.crowdcode.calculator.operators;

import de.crowdcode.calculator.Node;

public class PowerOperator extends Operator
{

	public PowerOperator(Node left, Node right)
	{
		super(left, right);
	}

	@Override
	public double evaluate()
	{
		return Math.pow(left.evaluate(), right.evaluate());
	}

}
