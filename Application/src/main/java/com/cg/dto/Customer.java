package com.cg.dto;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class Customer {

	private String name;
	private String mobileNo;
	private Wallet wallet;
	private String password;
	private String EmailId;
	private  TreeMap<LocalDateTime, String> transactions ;
	public Customer() {
		wallet = new Wallet();
		transactions = new TreeMap<LocalDateTime, String>();
	}
	public Customer(String mobileNo2, String name2, String password, String emailId, Wallet wallet2,
			TreeMap<LocalDateTime, String> transactions) {
		this.name=name2;
		mobileNo=mobileNo2;
		wallet=wallet2;
		this.transactions = transactions;
		this.password = password;
}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmailId() {
		return EmailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	public TreeMap<LocalDateTime, String> getTransactions() {
		return transactions;
	}
	public String getPassword() {
		return password;
	}
	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", mobileNo=" + mobileNo + ", wallet=" + wallet + ", EmailId=" + EmailId
				+ "]";
	}
	
	
}

