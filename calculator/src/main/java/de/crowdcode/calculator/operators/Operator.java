package de.crowdcode.calculator.operators;

import de.crowdcode.calculator.Node;

public abstract class Operator implements Node
{
	
	protected Node left;
	protected Node right;
	
	public Operator(Node left, Node right)
	{
		this.left = left;
		this.right = right;
	}

}
