package de.swm.auction.services;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogInterceptor implements MethodInterceptor
{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable
	{
		String methodName = invocation.getMethod().getDeclaringClass().getCanonicalName()+ "."+ invocation.getMethod().getName();
		long start = System.currentTimeMillis();
		Object result = invocation.proceed();
		long stop = System.currentTimeMillis();
		System.out.println("--- Methode: "+methodName+ " finished after "+(stop-start)+ " ms");
		
		return result;
	}

}
