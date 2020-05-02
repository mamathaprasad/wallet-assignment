

import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wallet.dao.AccountDao;
import wallet.exception.AccountNotFoundException;
import wallet.model.Account;
import wallet.model.Transaction;
public class Test {

private static ApplicationContext context;
	
	private static wallet.dao.AccountDao accountDao;
	
	
	private static Scanner in=new Scanner(System.in);
	public static void main(String[] args) {
		context=new ClassPathXmlApplicationContext("applicationContext.xml");
		accountDao=(AccountDao)context.getBean("accountDao");
		while(true) {
			System.out.println("***Welcome to the wallet Management***");
			System.out.println("1.Create Account");
			System.out.println("2.Get Account By Id");
			System.out.println("3.Withdraw");
			System.out.println("4.Deposit");
			System.out.println("5.Fund Transfer");
			System.out.println("6.Transaction History");
			System.out.println("0.Exit");
			System.out.println("Enter your Choice to Proceed");
			int choice=in.nextInt();
			switch(choice) {
			
				case 1:createAccount();break;
				case 2:getAccountById();break;
				case 3:withdrawAmount();break;
				case 4:depositAmount();break;
				case 5:fundtransfer();break;
				case 6:transactions();break;
				case 0:System.exit(0);
				default:System.out.println("Invalid choice");
			}
		}
	}
	
	
	private static void depositAmount() {
		System.out.println("Enter the Account Id:");
		int accountId=in.nextInt();
		System.out.println("Enter the Amount:");
		int amount=in.nextInt();
		
		double accountBalance;
		try {
			accountBalance = accountDao.deposit(accountId, amount);
			System.out.println("Amount Deposited,current balance="+accountBalance);
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private static void getAccountById() {
		System.out.println("Enter the Id of the Account:");
		in.nextLine();
		int accountId=in.nextInt();
	
		Account account;
		try {
			account = accountDao.getAccountById(accountId);
			System.out.println(account);
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private static void createAccount() {
		System.out.println("Enter the Name:");
		in.nextLine();
		String name=in.nextLine();
		System.out.println("Opening Balance for the account creation");
		int openingBalance=in.nextInt();
		Account account=new Account();
		account.setAccountName(name);
		account.setAccountBalance(openingBalance);
		int status;
		try {
			status = accountDao.createAccount(account);
			System.out.println("Account is created with id  "+ status);
			System.out.println("Creation Successful");
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void withdrawAmount() {
		System.out.println("Enter your account id");
		int accountId=in.nextInt();
		System.out.println("Enter the amount to be withdrawn");
		int amount=in.nextInt();
		
		double accountBalance;
		try {
			accountBalance = accountDao.withdraw(accountId, amount);
			System.out.println("Amount withdrawn,current balance="+accountBalance);
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public static void fundtransfer()
	{
		System.out.println("enter your id:");
		int id=in.nextInt();
		System.out.println("enter the id to which you want to transfer");
		int id2=in.nextInt();
		System.out.println("enter the amount");
		int amount=in.nextInt();
		try
		{
			accountDao.withdraw(id, amount);
			System.out.println("Transfer of" +amount+"is successfull");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	private static void transactions() {
		System.out.println("Enter the accountId");
		in.nextLine();
		int userId=in.nextInt();
		
		List<Transaction> transactions;
		
			transactions = accountDao.transaction(userId);
			if(transactions.isEmpty()){
				System.out.println("No Transactions made");
			}
			else{
			System.out.println("Transaction History of UserId:"+userId);
			for(Transaction transaction:transactions){
				System.out.println(transaction);
			}
		}
		
	}
	
}

