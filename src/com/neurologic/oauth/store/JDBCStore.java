/**
 * 
 */
package com.neurologic.oauth.store;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Buhake Sindi
 * @since 02 August 2011
 *
 */
public abstract class JDBCStore<T> extends AbstractStore<T> {

	private String driverName;
	private String connectionUrl;
	private String connectionUserName;
	private String connectionPassword;
	private Connection databaseConnection;
	
	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	/**
	 * @return the connectionUrl
	 */
	public String getConnectionUrl() {
		return connectionUrl;
	}
	
	/**
	 * @param connectionUrl the connectionUrl to set
	 */
	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}
	
	/**
	 * @return the connectionUserName
	 */
	public String getConnectionUserName() {
		return connectionUserName;
	}
	
	/**
	 * @param connectionUserName the connectionUserName to set
	 */
	public void setConnectionUserName(String connectionUserName) {
		this.connectionUserName = connectionUserName;
	}
	
	/**
	 * @return the connectionPassword
	 */
	public String getConnectionPassword() {
		return connectionPassword;
	}
	
	/**
	 * @param connectionPassword the connectionPassword to set
	 */
	public void setConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
	}

	/**
	 * @return the databaseConnection
	 */
	public Connection getDatabaseConnection() throws SQLException {
		return databaseConnection;
	}
	
	/**
	 * @param databaseConnection the databaseConnection to set
	 */
	public void setDatabaseConnection(Connection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	/**
	 * Creates a SQL Connection (if an existing connection isn't available)
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected void openConnection() throws SQLException {
		openConnection(false);
	}
	
	protected void openConnection(boolean forceNewConnection) throws SQLException {
		if (getDatabaseConnection() != null && !forceNewConnection) {
			//Why we want to always create a new connection?
			return ;
		}
		
		try {
			Class<?> clazz = Class.forName(getDriverName());
			Driver driver = (Driver) clazz.newInstance();
			
			Properties properties = new Properties();
			properties.put("user", getConnectionUserName());
			properties.put("password", getConnectionPassword());
			databaseConnection = driver.connect(getConnectionUrl(), properties);
			databaseConnection.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * Closes a provided SQL Connection.
	 * 
	 * @param connection a SQL Connection
	 * @throws SQLException
	 */
	protected void closeConnection(Connection connection) throws SQLException {
		if (connection != null && !connection.isClosed()) {
			if (!connection.getAutoCommit()) {
				connection.commit();
				connection.setAutoCommit(true);
			}
			
			try {
				connection.close();
			} finally {
				connection = null;
			}
		}
	}
	
	/**
	 * Closes the following connection resources and throws relevant {@link SQLException} exception, when necessary.
	 * @param resultSet the {@link ResultSet} to close.
	 * @param preparedStatement the {@link PreparedStatement} to close. 
	 * @throws SQLException
	 */
	protected void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
		//ALWAYS close a ResultSet first.
		if (resultSet != null && !resultSet.isClosed()) {
			try {
				resultSet.close();
			} finally {
				resultSet = null;
			}
		}
		
		if (preparedStatement != null && !preparedStatement.isClosed() && !preparedStatement.isPoolable()) {
			try {
				preparedStatement.close();
			} finally {
				preparedStatement = null;
			}
		}
	}
	
	/**
	 * This method calls the <code>closeResources()</code> method but doesn't throw any exceptions.
	 * 
	 * @param resultSet the {@link ResultSet} to close.
	 * @param preparedStatement the {@link PreparedStatement} to close.
	 */
	protected void closeResourcesSilently(ResultSet resultSet, PreparedStatement preparedStatement) {
		try {
			closeResources(resultSet, preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Silent closing of resources.", e);
		}
	}
}
