package com.cg.repo;

import java.util.TreeMap;

import com.cg.dto.Customer;

public interface WalletRepo {
	TreeMap<String, Customer> getDetails();

	String addCustomer(Customer customer);

}