package de.crowdcode.calculator;

import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.crowdcode.calculator.operators.Operator;
import de.crowdcode.calculator.operators.OperatorFactory;
import de.crowdcode.calculator.operators.OperatorType;

public class Calculator
{

	private static final String REGEX = "([0-9]+\\.?[0-9]*)|([\\*\\+\\-\\/\\^]{1})";
	private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX);

	private Stack<Node> nodes = new Stack<>();
	private Stack<OperatorType> operators = new Stack<>();

	public double calculate(String term)
	{
		Matcher matcher = REGEX_PATTERN.matcher(term);
		while (matcher.find())
		{
			String token = matcher.group();
			try
			{
				processNumberToken(token);
			} catch (NumberFormatException ex)
			{
				processOperatorToken(token);
			}
			log(nodes, operators);
		}

		processLeftOperations();

		log(nodes, operators);

		return nodes.pop().evaluate();
	}

	private void processLeftOperations()
	{
		while (!operators.isEmpty())
		{
			combineOperator(nodes, operators);
		}
	}

	private void processOperatorToken(String token)
	{
		OperatorType currentOp = OperatorType.fromSymbol(token);
		while (lessPrecedenceThenLastOperator(currentOp))
		{
			combineTopTwoNodesWithOperator();
		}
		operators.push(currentOp);
	}

	private void combineTopTwoNodesWithOperator()
	{
		OperatorType lastOp = operators.pop();
		Node right = nodes.pop();
		Node left = nodes.pop();
		Operator operator = OperatorFactory.buildOperator(lastOp, right, left);
		nodes.push(operator);
	}

	private boolean lessPrecedenceThenLastOperator(OperatorType currentOp)
	{
		return !operators.isEmpty()
				&& currentOp.precedence() <= operators.peek().precedence();
	}

	private void processNumberToken(String token)
	{
		NumberNode number = new NumberNode(Double.parseDouble(token));
		nodes.add(number);
	}

	private void log(Stack<Node> nodes, Stack<OperatorType> operators)
	{
		System.out.println("Nodes: " + Arrays.toString(nodes.toArray()));
		System.out.println("Ops  : " + Arrays.toString(operators.toArray()));
		System.out.println("----");
	}

	private void combineOperator(Stack<Node> nodes,
			Stack<OperatorType> operators)
	{
		OperatorType lastOperator = operators.pop();
		Node right = nodes.pop();
		Node left = nodes.pop();
		nodes.push(OperatorFactory.buildOperator(lastOperator, right, left));
	}

}
