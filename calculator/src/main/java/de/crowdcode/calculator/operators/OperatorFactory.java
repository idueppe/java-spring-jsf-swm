package de.crowdcode.calculator.operators;

import de.crowdcode.calculator.Node;

public class OperatorFactory
{

	public static Operator buildOperator(OperatorType type,  Node right, Node left)
	{
		switch (type)
		{
		case ADD:
			return new AddOperator(left, right);
		case DIVIDE:
			return new DivideOperator(left, right);
		case SUBTRACT:
			return new SubtractOperator(left, right);
		case MULTIPLY:
			return new Multiply(left, right);
		case POWER:
			return new PowerOperator(left, right);
		}
		throw new IllegalArgumentException("Unknown OperatorType "+type);
	}

}
