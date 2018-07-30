package com.cg.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.dto.Customer;
import com.cg.exception.IBankException;
import com.cg.exception.InsufficientBalanceException;
import com.cg.exception.InvalidInputException;
import com.cg.repo.WalletRepo;
import com.cg.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{

	static WalletRepo iWalletDao = new WalletRepoImpl();
	public static TreeMap<String, Customer> customerDetails = null;
	static {
		customerDetails = iWalletDao.getDetails();

	}

	public void checkName(String name) throws InvalidInputException {
		Pattern pattern = Pattern.compile("[a-zA-Z]{1,}");
		Matcher matcher = pattern.matcher(name);
		if (!(matcher.matches())) {
			throw new InvalidInputException(IBankException.nameException);
		}
	}

	public void checkMobileNumber(String mobileNumber) throws InvalidInputException {

		Pattern pattern = Pattern.compile("[7-9]{1}[0-9]{9}");
		Matcher matcher = pattern.matcher(mobileNumber);
		if (!(matcher.matches())) {
			throw new InvalidInputException(IBankException.mobileNumberException);
		}
	}

	public void checkPassword(String password) throws InvalidInputException {
		Pattern pattern = Pattern.compile(".*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*" + "");
		Matcher matcher = pattern.matcher(password);
		if (!(matcher.matches())) {
			throw new InvalidInputException(IBankException.passwordException);
		}

	}

	public void checkEmail(String emailId) throws InvalidInputException {
		Pattern pattern = Pattern.compile("[a-z]{1}[a-z0-9._]{1,}@[a-zA-Z0-9]{1,}.com");
		Matcher matcher = pattern.matcher(emailId);
		if (!(matcher.matches())) {
			throw new InvalidInputException(IBankException.emailIdException);
		}

	}

	public String addCustomer(Customer customer) throws InvalidInputException {
		String result = null;
		if (!customerDetails.containsKey(customer.getMobileNo())) {
			result= iWalletDao.addCustomer(customer);
		}
		else
			throw new InvalidInputException(IBankException.ACCOUNTEXISTS);
     return result;
	}

	public Customer showBalance(String mobileNum, String password) throws InvalidInputException {
		Customer result = null;

		if (customerDetails.containsKey(mobileNum)) {

			if (customerDetails.get(mobileNum).getPassword().equals(password)) {
				result = customerDetails.get(mobileNum);
			}

		}
		else
			throw new InvalidInputException(IBankException.invalidDetails);
		
		return result;
	}

	public Customer check(String mobileNum, String password) throws InvalidInputException {
		Customer result = null;
		if (customerDetails.containsKey(mobileNum)) {
			if (customerDetails.get(mobileNum).getPassword().equals(password)) {
				result = customerDetails.get(mobileNum);
			}
		}
		else
			throw new InvalidInputException(IBankException.invalidDetails);
		return result;
	}

	public void deposit(Customer customer, BigDecimal amount) {

		customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount));
		customer.getTransactions().put(LocalDateTime.now(), "Credited " + amount);

	}

	public boolean withDraw(Customer customer, BigDecimal amount) throws InsufficientBalanceException {
		boolean result = false;
		if (customer.getWallet().getBalance().subtract(amount).compareTo(BigDecimal.valueOf(0.0)) >= 0) {
			customer.getWallet().setBalance(customer.getWallet().getBalance().subtract(amount));
			result = true;
			customer.getTransactions().put(LocalDateTime.now(), "Debited " + amount);
		}
		else
			throw new InsufficientBalanceException(IBankException.insufficientFunds);
		return result;
	}

	public boolean isFound(String receiverMobile) throws InvalidInputException {
		boolean result = false;
		if (customerDetails.containsKey(receiverMobile)) {
			result = true;

		}
		else
			throw new InvalidInputException(IBankException.mobileNumberException);
		return result;
	}

	public boolean transfer(String senderMobile, String receiverMobile, BigDecimal amount) throws  InsufficientBalanceException, InterruptedException {
		boolean result = false;
		if (customerDetails.get(senderMobile).getWallet().getBalance().subtract(amount)
				.compareTo(BigDecimal.valueOf(0.0)) >= 0) {
			Customer sender = customerDetails.get(senderMobile);
			sender.getWallet().setBalance(sender.getWallet().getBalance().subtract(amount));
			sender.getTransactions().put(LocalDateTime.now(), "Debited " + amount + " to " + receiverMobile);
			Customer receiver = customerDetails.get(receiverMobile);
			Thread.sleep(100);
			receiver.getWallet().setBalance(receiver.getWallet().getBalance().add(amount));
			receiver.getTransactions().put(LocalDateTime.now(), "Credited " + amount + " from " + senderMobile);
			result = true;
		}
		else
			throw new InsufficientBalanceException(IBankException.insufficientFunds);
		return result;
	}

}