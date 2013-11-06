package de.crowdcode.calculator;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorTest
{

	@Test
	public void testCalculation()
	{
		assertEquals(1.0, new Calculator().calculate("1.2 - 0.2"), 0.0);
		assertEquals(1.0, new Calculator().calculate("0.7 + 0.3"), 0.0);
		assertEquals(0.2, new Calculator().calculate("1.0 * 0.2"), 0.0);
		assertEquals(5.0, new Calculator().calculate("1.0 / 0.2"), 0.0);
		assertEquals(5.0, new Calculator().calculate("5.5 + 5 * 5 / 5 - 5.5"), 0.0);
		assertEquals(125.0, new Calculator().calculate("5.5 + 5 * 5 * 5 - 5.5"), 0.0);
	}
	
	@Test
	public void testCalculationLong()
	{
		assertEquals( -1.0, new Calculator().calculate("5.5 + 5 * 5 / 5 - 5.5 - 2 - 2 - 2"), 0.0);
	}

	@Test
	public void testCalculationPower()
	{
		assertEquals( 3130.0, new Calculator().calculate("5 + 5 ^ 5 "), 0.0);
	}

}
