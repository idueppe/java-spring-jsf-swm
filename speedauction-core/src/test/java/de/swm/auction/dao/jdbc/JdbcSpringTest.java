package de.swm.auction.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-db.xml")
public class JdbcSpringTest
{

	@Autowired
	private DataSource dataSource;

	@Test
	public void testSelectProducts() throws SQLException
	{
		String sql = "SELECT * FROM PRODUCT";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(sql);
				ResultSet resultSet = pStmt.executeQuery())
		{
			while (resultSet.next())
			{
				System.out.println(resultSet.getString("id"));
				System.out.println(resultSet.getString("title"));
			}
		}
	}

}
