/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.neurologic.oauth.util.ApplicationUtil;

/**
 * @author Buhake Sindi
 * @since 07 September 2011
 *
 */
public abstract class JDBCDataStore<T extends StoreData> extends AbstractDataStore<T> {

	private String driverName;
	private String connectionUrl;
	private String connectionUserName;
	private String connectionPassword;
	private String dataSourceName;
	private DataSource dataSource;
	private Driver driver;
	private Connection connection;
	
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
	 * @return the dataSourceName
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}
	
	/**
	 * @param dataSourceName the dataSourceName to set
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	
	private boolean isDataSourceSet() {
		if (dataSource != null) {
			return true;
		}
		
		if (dataSourceName != null && !dataSourceName.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	protected Connection getConnection() {
		try {
			if (this.connection == null || this.connection.isClosed()) {
				if (isDataSourceSet()) {
					connection = openViaDataSource();
				} else {
					connection = openViaDriver();
				}
				
				//Check if it opened...
				if (connection == null || connection.isClosed()) {
					logger.error("Strange! Connection failed to start.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQLException: " + e.getLocalizedMessage(), e);
		}
		
		return connection;
	}
	
	protected void close(ResultSet rs, Statement statement) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("Resulset fails to close: " + e.getLocalizedMessage());
			} finally {
				rs = null;
			}
		}
		
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("Statement fails to close: " + e.getLocalizedMessage());
			} finally {
				statement = null;
			}
		}
	}
	
	protected void close(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Failed to close connection: " + e.getLocalizedMessage());
		} finally {
			connection = null;
		}
	}
	
	private Connection openViaDataSource() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			return connection;
		}
		
		if (dataSource == null) {
			try {
				Context context = new InitialContext();
				dataSource = (DataSource) context.lookup(dataSourceName);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getLocalizedMessage(), e);
			}
		}
		
		if (dataSource != null) {
			connection = dataSource.getConnection();
		}
		
		return connection;
	}
	
	private Connection openViaDriver() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			return connection;
		}
		
		if (driver == null) {
			try {
				driver = (Driver) ApplicationUtil.applicationInstance(getDriverName());
				if (driver == null) {
					//Weird, but we understand....
					driver = (Driver) Class.forName(getDriverName()).newInstance();
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				logger.error("InstantiationException: " + e.getLocalizedMessage(), e);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				logger.error("IllegalAccessException: " + e.getLocalizedMessage(), e);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error("ClassNotFoundException: " + e.getLocalizedMessage(), e);
			}
		}
		
		if (driver != null) {
			Properties properties = new Properties();
			properties.setProperty("user", getConnectionUserName());
			properties.setProperty("password", getConnectionPassword());
			connection = driver.connect(getConnectionUrl(), properties);
			if (connection != null) {
				connection.setAutoCommit(true);
			}
		}
		
		return connection;
	}
}
