package wallet.model;

import java.sql.Time;

import java.util.Date;

public class Transaction {
	private int id;
	private double amount;
	private String transactionType;
	private Date timeStamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", transactionType=" + transactionType + ", timeStamp="
				+ timeStamp + "]";
	}
	
	
}
