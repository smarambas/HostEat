package model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSingleton {

	private static String user;
	private static String pswd;
	private static String url;
	private static String driver;
	private static Connection connection = null;
	private static ConnectionSingleton instance = null;
	
	private ConnectionSingleton() {}
	
	public static ConnectionSingleton createConnection( ) throws ClassNotFoundException, SQLException {
		if(instance == null) {	
			instance = new ConnectionSingleton();
			getConfig();
		}
		if(connection == null || connection.isClosed()) {
    		Class.forName(driver);
    		connection = DriverManager.getConnection(url, user, pswd);
    	} 
		
		return instance;
	}
	
	private static void getConfig() {
		try {
			InputStream is = ConnectionSingleton.class.getClassLoader().getResourceAsStream("database.config");
			Properties properties = new Properties();
			properties.load(is);
			user = properties.getProperty("USER");
			pswd = properties.getProperty("PSWD");
			url = properties.getProperty("DB_URL");
			driver = properties.getProperty("DRIVER_CLASS_NAME");					
		} catch (IOException e) {
			e.printStackTrace();	//NEED TO CHANGE THIS
		} 
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static void closeConnection() throws SQLException {
		if(connection != null) {
			connection.close();
		}
	}
}
