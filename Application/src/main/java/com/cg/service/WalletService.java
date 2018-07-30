package com.cg.service;

import java.math.BigDecimal;

import com.cg.dto.Customer;
import com.cg.exception.InsufficientBalanceException;
import com.cg.exception.InvalidInputException;

public interface WalletService {
	void checkName(String name) throws InvalidInputException;
	void checkMobileNumber(String mobileNumber) throws InvalidInputException;
	void checkPassword(String password) throws InvalidInputException;
	void checkEmail(String emailId) throws InvalidInputException;
	String addCustomer(Customer customer) throws InvalidInputException;
	Customer showBalance(String mobileNum, String password) throws InvalidInputException;
	Customer check(String mobileNum, String password) throws InvalidInputException;
	void deposit(Customer customer, BigDecimal amount);
	boolean withDraw(Customer customer, BigDecimal amount) throws InsufficientBalanceException;
	boolean isFound(String receiverMobile) throws InvalidInputException;
	boolean transfer(String senderMobile, String receiverMobile, BigDecimal amount) throws InterruptedException, InsufficientBalanceException;
}