package db;

public class DBConnectionFactory {
	private static final String DEFAULT_DB = "mysql";
	
	public static DBConnection getConnection(String db) {
		
		switch (db) {
		case "mysql":
			return null;
		case "mongodb":
			return null;
		default:
			return null;
		}
		

	}
	
	public static DBConnection getConnection() {
		return getConnection(DEFAULT_DB);
		
	}


}
