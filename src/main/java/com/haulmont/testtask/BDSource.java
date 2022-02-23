package com.haulmont.testtask;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.replication.fluent.ChainedCommonCreateSlotBuilder;

import lombok.val;

public class BDSource {
	
	private static final String TEMPLATE_FOR_INSERT = "INSERT into client (name, surname, secondname, phonenumber, email, passport) VALUES ('%s','%s','%s', '%s','%s','%s')"; 
	private static final String TEMPLATE_FOR_DELETE = "DELETE FROM client WHERE Id = '%d'";
	private static final String TEMPLATE_FOR_FIND = "select * from client where passport = '%s'";
	private static final String TEMPLATE_FOR_FINDSURNAME = "select * from client where surname = %s";
	
	private static final String TEMPLATE_FIND_BODYLOAN_STRING = "select summ_bodyloan from loan_offer where id_client = '%d'";
	private static final String TEMPLATE_FIND_SUMMPERCENT = "select summ_percent from loan_offer where id_client = '%d'";
	
	
	private static final String CONNECTION_STR = "jdbc:postgresql://127.0.0.1:5432/finance";
	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "postgres";
    
	public List<Client> getClientFio(String fio) throws ClassNotFoundException, SQLException{
		
		//val sqlFindSurname = TEMPLATE_FOR_FINDSURNAME; 
		val sqlVal = String.format(TEMPLATE_FOR_FINDSURNAME, fio);
		return getClient(sqlVal);
	}
	
	public List<Client> getClientNumerPassport(String passport) {
		
		//System.out.println("sqlVal "+passport);
		
		val sqlVal = String.format(TEMPLATE_FOR_FIND, passport);
		
		return getClient(sqlVal);
		
		
	}

    
    public List<Client> getClient(String sql)  {
    	
    	System.out.println(sql);

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Client> listResult = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(CONNECTION_STR, CONNECTION_USERNAME, CONNECTION_PASSWORD);
				Statement statement = conn.createStatement()) 
		{

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {				
				Client client = new Client();
				client.setId(resultSet.getInt(1));
				client.setName(resultSet.getString(2));
				client.setSurname(resultSet.getString(3));
				client.setSecondName(resultSet.getString(4));
				client.setPhoneNumber(resultSet.getString(5));
				client.setEmail(resultSet.getString(6));
				listResult.add(client);
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return listResult;

	}
    
    public void create(Client client) throws ClassNotFoundException, SQLException{
 		   
 		   try (Connection conn = DriverManager.getConnection(CONNECTION_STR, CONNECTION_USERNAME, CONNECTION_PASSWORD)){
 			   
 			val sqlInsert = TEMPLATE_FOR_INSERT; 
 			
 			val sqlVal = String.format(sqlInsert, client.getName(), client.getSurname(), client.getSecondName(), client.getPhoneNumber(), client.getEmail(), client.getPassport());
 		
			Statement statement = conn.createStatement();
			String name = null;
			int rows = statement.executeUpdate(  sqlVal );
					
 	           //System.out.printf("Added %d rows", rows);
 	        }
 	    }
    
    
    public void delete(Client client) {
    	
    	System.out.println("client!!!!   "+ client);
    	
    	try (Connection conn = DriverManager.getConnection(CONNECTION_STR, CONNECTION_USERNAME, CONNECTION_PASSWORD)){
    		
    		Statement statement = conn.createStatement();
    		val sqlDelete =  TEMPLATE_FOR_DELETE;
    		
    		//System.out.println("sqlDeleteeeeeee   " +sqlDelete);
    		
    		val sqlPassport = String.format( sqlDelete, client.getId());
	           
            int rows = statement.executeUpdate(sqlPassport);
           
    		   
	           	
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    	
    } 
    
    public void findFinalSummPercent(LoanOffer loanOffer) {
    	
    try (Connection conn = DriverManager.getConnection(CONNECTION_STR, CONNECTION_USERNAME, CONNECTION_PASSWORD)){
    		
    		Statement statement = conn.createStatement();
    		
    		val sqlbodyloan = TEMPLATE_FIND_BODYLOAN_STRING;
    		val sqlSummPercent = TEMPLATE_FIND_SUMMPERCENT;
    		
    		val bodyLoan =  String.format(sqlbodyloan, loanOffer.getSummBodyloan());
    		val sumPercent = String.format(sqlSummPercent, loanOffer.getSummPercent());
    		
    		val finalSummPercentLoan =  bodyLoan + sumPercent;
    		
    		System.out.println("finalSummPercentLoan!!!!" + finalSummPercentLoan);
    		
    		
    		
         } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 }

