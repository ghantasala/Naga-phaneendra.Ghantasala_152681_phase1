package com.cg.repo;

import java.util.TreeMap;

import com.cg.dto.Customer;

public class WalletRepoImpl implements WalletRepo{
	public static TreeMap<String,Customer> customerDetails=null; 
	static{
		customerDetails=new TreeMap<String, Customer>();
		
	}
	public TreeMap<String, Customer> getDetails() {
		
		return customerDetails;
	}
	public String addCustomer(Customer customer) {
		customerDetails.put(customer.getMobileNo(), customer);
		return customer.getMobileNo();
	}
	}