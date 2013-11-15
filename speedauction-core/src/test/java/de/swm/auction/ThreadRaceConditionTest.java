package de.swm.auction;

import org.junit.Test;

public class ThreadRaceConditionTest
{

	private int count;

	private Runnable increase = new Runnable()
	{

		@Override
		public void run()
		{
			for (int i = 0; i < 1_000_000_000; i++)
			{
					count++;
			}
			
			System.out.println(Thread.currentThread().getName()+": "+count);
		}
	};

	@Test
	public void testRaceCondition() throws Exception
	{
		System.out.println(Integer.MIN_VALUE +" - "+Integer.MAX_VALUE);
		
		Thread a = new Thread(increase,"A");
		Thread b = new Thread(increase,"B") {
			
			public void start() 
			{
				System.out.println(getName()+" wurde gestartet");
				super.start();
			}
			
		};
		a.start();
		b.start();

		a.join();
		b.join();
		
		System.out.println("Finised "+count);
		
	}

}
