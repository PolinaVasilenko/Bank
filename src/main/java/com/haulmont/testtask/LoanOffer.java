package com.haulmont.testtask;

import java.sql.Date;

import lombok.Data;

@Data
public class LoanOffer {
	
	private int idLoan;
	private int idClient;
	private Date dateLoan;
	private double summPayment;
	private double summBodyloan;
	private double summPercent;
	private double continueOffer;
	
	

}
