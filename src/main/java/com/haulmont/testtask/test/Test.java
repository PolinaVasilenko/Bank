package com.haulmont.testtask.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.haulmont.testtask.BDSource;
import com.haulmont.testtask.Client;
import com.haulmont.testtask.LoanOffer;
import com.haulmont.testtask.calculation.CalculationData;
import com.haulmont.testtask.calculation.CalculationPercentLoan;

import lombok.Data;
import lombok.val;

@Data
public class Test {

	public static void main(String[] args) {
		
		BDSource bdSource = new BDSource();
		CalculationData calculationData = new CalculationData();
		//Client clientTest = getClientUsePassport(bdSource);
		//String sql = "select * from loan_offer where id_client = '6'";
		
		//deleteClientUsePassport(bdSource, clientTest);
		
		//List<Client> listOfClients = new ArrayList<Client>();
		
		String sqlgetClients = "select * from client";
		
		val resaltList = bdSource.getClients(sqlgetClients);
		
		System.out.println("resaltList" + resaltList);
		val listVal = bdSource.receiveInfoLoan(resaltList);
		
		calculationData.getPercentLoan(listVal);

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
	
//	private static void receiveInfoLoan(BDSource bdSource, Client clientTest) {
//		LoanOffer loanOffer = new LoanOffer();
//		
//		System.out.println(loanOffer);	
//		
//	}
}
