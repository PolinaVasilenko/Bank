package com.haulmont.testtask.test;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.haulmont.testtask.BDSource;
import com.haulmont.testtask.Client;
import com.haulmont.testtask.LoanOffer;

import lombok.val;

public class Test {

	public static void main(String[] args) {
		
		BDSource bdSource = new BDSource();
		Client clientTest = getClientUsePassport(bdSource);
		deleteClientUsePassport(bdSource, clientTest);

	}

	private static void deleteClientUsePassport(BDSource bdSource, Client clientTest) {
		bdSource.delete(clientTest);
		
		
	}
	

	private static Client getClientUsePassport(BDSource bdSource) {
		
		String passport = "78601214";
		val clients = bdSource.getClientNumerPassport(passport);
		
		//System.out.println("Клиент "+ clients);		
		return clients.get(0);
	}

	
	private static Client getClientFio(BDSource bdSource) {
		
		String surname = "Иванова"; 
		return null;
		
	}
	
	private static LoanOffer findFinalSummPercent(BDSource bdSource) {
		
		LoanOffer loanOffer = new LoanOffer();
		
		int idClient = 2;
		
		return loanOffer.;
		
	}
}
