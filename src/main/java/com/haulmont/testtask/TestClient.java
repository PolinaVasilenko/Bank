package com.haulmont.testtask;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;

import lombok.val;

public class TestClient {
	
    BDSource bdSource = new BDSource();	
    private static final String TEMPLATE_FOR_FIND = "select * from client where passport = %s";
    
   
		  
	public static Client getTestClient() {
		Client testPerson = new Client();
		testPerson.setName("Pavel");
		testPerson.setSurname("Кузнецов");
		testPerson.setSecondName("Алексеевич");
		testPerson.setPhoneNumber("89764553291");
		testPerson.setEmail("pav@bk.ru");
		testPerson.setPassport(43672312);
		return testPerson;
	}	  
   
	
	
	public boolean getClientPassport(String passport) throws ClassNotFoundException, SQLException {
		
		List<Client> resultClients = new ArrayList<>();
		
	    resultClients = bdSource.getClientNumerPassport(passport);
	    
	    if (resultClients.size()==1) {
	    	return true;
	    }
	    else {
	    	System.out.println("В БД нашли несколько пользователей с таким номером паспорта, уточните данные или измените запрос");
	    }
	    
		return false;
		
		
	}
   
}
