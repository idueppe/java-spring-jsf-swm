package de.swm.auction.spring.framework.jdbc;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * SqlParameterSource that combines MapParameterSource and BeanPropertySqlParameterSource 
 * @author idueppe
 *
 */
public class MapBeanPropertySqlParameterSource implements SqlParameterSource
{

	private MapSqlParameterSource mappedSource;
	private BeanPropertySqlParameterSource beanSource;
	
	public MapBeanPropertySqlParameterSource(Object bean)
	{
		mappedSource = new MapSqlParameterSource();
		beanSource = new BeanPropertySqlParameterSource(bean);
	}
	
	@Override
	public boolean hasValue(String paramName)
	{
		return mappedSource.hasValue(paramName) || beanSource.hasValue(paramName);
	}

	@Override
	public Object getValue(String paramName) throws IllegalArgumentException
	{
		if (mappedSource.hasValue(paramName))
			return mappedSource.getValue(paramName);
		else
			return beanSource.getValue(paramName);
	}

	@Override
	public int getSqlType(String paramName)
	{
		if (mappedSource.hasValue(paramName))
			return mappedSource.getSqlType(paramName);
		else
			return beanSource.getSqlType(paramName);
	}

	@Override
	public String getTypeName(String paramName)
	{
		if (mappedSource.hasValue(paramName))
			return mappedSource.getTypeName(paramName);
		else
			return beanSource.getTypeName(paramName);
	}

	public MapBeanPropertySqlParameterSource addValue(String paramName, Object value)
	{
		mappedSource.addValue(paramName, value);
		return this;
	}

	public MapBeanPropertySqlParameterSource addValue(String paramName, Object value, int sqlType)
	{
		mappedSource.addValue(paramName, value, sqlType);
		return this;
	}

	public MapBeanPropertySqlParameterSource addValue(String paramName, Object value, int sqlType, String typeName)
	{
		mappedSource.addValue(paramName, value, sqlType, typeName);
		return this;
	}

	public MapBeanPropertySqlParameterSource addValues(Map<String, ?> values)
	{
		mappedSource.addValues(values);
		return this;
	}

}
