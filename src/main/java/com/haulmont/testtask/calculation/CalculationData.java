package com.haulmont.testtask.calculation;

import java.util.ArrayList;
import java.util.List;

import com.haulmont.testtask.BDSource;
import com.haulmont.testtask.LoanOffer;

public class CalculationData {
	
	
	BDSource bdSource = new BDSource();
	
	public List<CalculationPercentLoan> getPercentLoan(List<LoanOffer> listLoanOffers){
	
	List<CalculationPercentLoan> percentLoanList = new ArrayList<>();
	
	for (LoanOffer elem : listLoanOffers) {
		CalculationPercentLoan calculationPercentLoan = new CalculationPercentLoan();
		calculationPercentLoan.setIdClient(elem.getIdClient());
		calculationPercentLoan.setSummBodyloan(elem.getSummBodyloan());
		calculationPercentLoan.setSummPercent(elem.getSummPercent());
		calculationPercentLoan.setPercentLoan(elem.getSummBodyloan()+elem.getSummPercent());
		percentLoanList.add(calculationPercentLoan);
		
		System.out.println("calculationPercentLoan!!!!" + calculationPercentLoan);
	}
	
	
	return percentLoanList;
			
	}
}
