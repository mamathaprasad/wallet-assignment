package wallet.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import wallet.exception.AccountNotFoundException;
import wallet.exception.InSufficientFundsException;
import wallet.model.Account;
import wallet.model.Transaction;



public interface AccountDao {

public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	public int createAccount(Account account) throws AccountNotFoundException;
	public int withdraw(int accountId,int amount) throws AccountNotFoundException;
	public Account getAccountById(int id) throws AccountNotFoundException;
	public int deposit(int accountId,int amount) throws AccountNotFoundException;
	public int fundTransfer(int accountId1,int accountId2,int amount) throws AccountNotFoundException;
	public List<Transaction>transaction(int userId);

	
	
}
