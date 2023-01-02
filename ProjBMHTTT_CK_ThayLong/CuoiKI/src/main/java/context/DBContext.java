package context;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
	private final String serverName = "ADMIN-PC";
	private final String dbName = "projectLTW";
	private final String portNumber = "1433";
	private final String instance = "";
	private final String userID = "abc";
	private final String password = "123";

	public Connection getConnection() throws Exception {
		String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName;
		if (instance == null || instance.trim().isEmpty())
			url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url, userID, password);
	}
}
