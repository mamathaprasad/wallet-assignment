package wallet.db;

public class Connection {

	public static java.sql.Connection getConnection() throws ClassNotFoundException,java.sql.SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		
		return java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/wallet", "root", "root");
	    
	}
}
