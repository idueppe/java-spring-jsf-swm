package de.swm.auction.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;

public class JdbcTest
{

	private static long nextId = System.currentTimeMillis();

	public static long nextId()
	{
		return ++nextId;
	}

	/**
	 * @throws SQLException
	 */
	@Test
	public void testJdbc() throws SQLException
	{
		String sqlInsert = "INSERT INTO product (id, title, description) VALUES (?,?,?)";
		try (Connection connection = openConnection(); PreparedStatement pStmt = connection.prepareStatement(sqlInsert);)
		{

			for (int i = 0; i < 10; i++)
			{
				pStmt.setLong(1, nextId());
				pStmt.setString(2, "Product Titel " + i);
				pStmt.setString(3, "Product Description " + i);
				pStmt.addBatch();
			}
			int[] update = pStmt.executeBatch();
			System.out.println("Zeilen eingefügt " + Arrays.toString(update));
		}
	}

	@Test
	public void testJdbcResult() throws SQLException
	{
		String sql = "SELECT title, description FROM product";
		try (Connection connection = openConnection();
				PreparedStatement pStmt = connection.prepareStatement(sql);
				ResultSet resultSet = pStmt.executeQuery())
		{

			while (resultSet.next())
			{
				System.out.println("Title: " + resultSet.getString("title"));
				System.out.println("Description: " + resultSet.getString("description"));
			}

		}
	}

	@Test
	public void testResultSetMetaData() throws SQLException
	{
		String sql = "SELECT * FROM product";
		try (Connection connection = openConnection(); //
			PreparedStatement pStmt = connection.prepareStatement(sql);)
		{

			pStmt.setFetchSize(10);
			try (ResultSet resultSet = pStmt.executeQuery();)
			{

				ResultSetMetaData metaData = resultSet.getMetaData();

				while (resultSet.next())
				{
					for (int col = 1; col <= metaData.getColumnCount(); col++)
					{
						String columnName = metaData.getColumnName(col);
						System.out.println(columnName + ": " + resultSet.getString(col));
					}
					System.out.println("--------");
				}
			}

		}
	}

	private Connection openConnection() throws SQLException
	{
		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
		String jdbcUsername = "hr";
		String jdbcPassword = "oracle";

		return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
	}

}
