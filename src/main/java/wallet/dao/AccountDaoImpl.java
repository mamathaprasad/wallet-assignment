package wallet.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import wallet.exception.AccountNotFoundException;
import wallet.model.Account;
import wallet.model.Transaction;

@Component("accountDao")
public class AccountDaoImpl implements AccountDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	 public JdbcTemplate getJdbcTemplate() {
			return jdbcTemplate;
		}

	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
		
	}

	public int createAccount(Account account) throws AccountNotFoundException {
		String sql="INSERT into accounts(name,balance) VALUES('"+account.getAccountName()+"',"+account.getAccountBalance()+")";
		return jdbcTemplate.update(sql);
	}

	public int withdraw(int accountId, int amount) throws AccountNotFoundException  {
		
		/*
		 * int accountBalance=0; try {
		 * 
		 * accountBalance=(int) (getAccountById(accountId).getAccountBalance()-amount);
		 * if(accountBalance<0) { System.out.println("The Amount is Insufficient\n"); }
		 * 
		 * java.sql.Connection connection=wallet.db.Connection.getConnection(); String
		 * sql="UPDATE accounts SET balance=? WHERE id=?"; java.sql.PreparedStatement
		 * statement=connection.prepareStatement(sql); statement.setInt(1,
		 * accountBalance); statement.setInt(2, accountId); statement.executeUpdate();
		 * connection.close(); }catch(ClassNotFoundException cnfe) {
		 * System.err.println(cnfe.getMessage()); } catch (java.sql.SQLException e) {
		 * System.err.println(e.getMessage()); }
		 */
		String sql="update accounts set  balance=? where id=?";
		 int accountBalance =  (int) (getAccountById(accountId).getAccountBalance()-amount);
		 jdbcTemplate.update(sql,accountBalance,accountId);
		String transtype="withdraw";
		java.sql.Timestamp sqlTimeStamp=new java.sql.Timestamp(new java.util.Date().getTime());
		jdbcTemplate.update("insert into transaction values(?,?,?,?)",accountId,amount,transtype,sqlTimeStamp);
		
		return accountBalance;
	}

	
		  
		 public int deposit(int accountId, int amount) throws AccountNotFoundException
		 { /* int accountBalance=0; try { AccountDao accountdao=new AccountDaoImpl();
		 * Account account=accountdao.getAccountById(accountId);
		 * 
		 * java.sql.Connection connection= wallet.db.Connection.getConnection();
		 * 
		 * String sql="UPDATE accounts SET balance=? WHERE id=?";
		 * 
		 * accountBalance=(int) (account.getAccountBalance()+amount);
		 * java.sql.PreparedStatement statement=connection.prepareStatement(sql);
		 * statement.setDouble(1,accountBalance); statement.setInt(2, accountId);
		 * statement.executeUpdate(); connection.close(); }catch(ClassNotFoundException
		 * cnfe) { System.err.println(cnfe.getMessage()); }catch(java.sql.SQLException
		 * sqle) { System.err.println(sqle.getMessage()); }
		 */
		String sql="update accounts set balance=? where id= ?";
		
	        int accountBalance= (int) (getAccountById(accountId).getAccountBalance()+amount);
	         jdbcTemplate.update(sql,accountBalance,accountId );
		 
		String transtype="deposit";
		java.sql.Timestamp sqlTimeStamp=new java.sql.Timestamp(new java.util.Date().getTime());
		jdbcTemplate.update("insert into transaction values(?,?,?,?)",accountId,amount,transtype,sqlTimeStamp);
		
		return accountBalance;
	}

	public int fundTransfer(int accountId1, int accountId2, int amount) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		Account account=new Account();
		if(account.getAccountBalance()<10000)
		{
			System.out.println("you dont have sufficient amount");
		}
		withdraw(accountId1,amount);
		
		return deposit(accountId2,amount);
	}

	public List<Transaction> transaction(int userId) {
		// TODO Auto-generated method stub
		String sql="Select * from transaction where id= ' "+userId+"'";
		List<Transaction> transactions=jdbcTemplate.query(sql,new RowMapper<Transaction>() {
				public Transaction mapRow(ResultSet rs,int rn) throws SQLException {
					Transaction trans=new Transaction();
					trans.setId(rs.getInt(1));
					trans.setAmount(rs.getInt(2));
					trans.setTransactionType(rs.getString(3));
					trans.setTimeStamp(rs.getTimestamp(4));
					return trans;
				}
	});
		return transactions;
	}
	public Account getAccountById(int accountId) throws AccountNotFoundException{
		
		  Account account=null; try { java.sql.Connection connection=
		  wallet.db.Connection.getConnection();
		  
		  String sql="SELECT * FROM accounts WHERE id='"+accountId+"'";
		  java.sql.Statement statement = connection.createStatement();
		  java.sql.ResultSet result=statement.executeQuery(sql); if(result.next()) {
		  String name=result.getString(2); int balance=result.getInt(3); account=new
		  Account(accountId,name,balance); }else {
		  System.out.println("Account with id:"+accountId+"not Found!"); }
		  connection.close(); }catch(ClassNotFoundException cnfe) {
		  System.err.println(cnfe.getMessage()); }catch(java.sql.SQLException sqle) {
		  sqle.printStackTrace(); }
		  
		  return account;
		}
	/*
	 * String sql = "select * from accounts where id= ?";
	 * 
	 * 
	 * //System.out.println("ID : " + customer.getId()); return (Account)
	 * jdbcTemplate.queryForObject(sql, new Object[] {}, new AccountMapper() ); }
	 * 
	 * class AccountMapper implements RowMapper<Account>{
	 * 
	 * public Account mapRow(ResultSet rs, int rowNum) throws SQLException { Account
	 * account = new Account(); account.setAccountId(rs.getInt("accountId"));
	 * account.setAccountName(rs.getString("accountName"));
	 * account.setAccountBalance(rs.getInt("accountBalance")); return account; } };
	 */


}

	
	
