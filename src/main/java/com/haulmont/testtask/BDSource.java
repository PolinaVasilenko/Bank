package com.haulmont.testtask;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.postgresql.replication.fluent.ChainedCommonCreateSlotBuilder;

import lombok.val;

public class BDSource {
	
	private static final String TEMPLATE_FOR_INSERT = "INSERT into client (name, surname, secondname, phonenumber, email, passport) VALUES ('%s','%s','%s', '%s','%s','%s')"; 
	private static final String TEMPLATE_FOR_DELETE = "DELETE FROM client WHERE Id = '%d'";
	private static final String TEMPLATE_FOR_FIND = "select * from client where passport = '%s'";
	private static final String TEMPLATE_FOR_FINDSURNAME = "select * from client where surname = %s";
	
	private static final String MASKFORLOAN= " '%s'"; 
	
	//private static final String TEMPLATE_FIND_BODYLOAN_STRING = "select summ_bodyloan from loan_offer where id_client = '%d'";
	//private static final String TEMPLATE_FIND_SUMMPERCENT = "select summ_percent from loan_offer where id_client = '%d'";
	
	private static final String TEMPLATE_RECEIVEINDOLOAN = "select * from loan_offer where id_client in (%s)";
	
	private static final String CONNECTION_STR = "jdbc:postgresql://127.0.0.1:5432/finance";
	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "postgres";
    
	public List<Client> getClientFio(String fio) throws ClassNotFoundException, SQLException{
		
		//val sqlFindSurname = TEMPLATE_FOR_FINDSURNAME; 
		val sqlVal = String.format(TEMPLATE_FOR_FINDSURNAME, fio);
		return getClients(sqlVal);
	}
	
	public List<Client> getClientNumerPassport(String passport) {
		
		//System.out.println("sqlVal "+passport);
		
		val sqlVal = String.format(TEMPLATE_FOR_FIND, passport);
		
		return getClients(sqlVal);
		
		
	}

    
    public List<Client> getClients(String sql)  {
    	
    	//System.out.println(sql);

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
    
    public List<LoanOffer> receiveInfoLoan(List<Client> clients) {
    	
    	List<LoanOffer> listLoanOffers = new ArrayList<>();
    	
    try (Connection conn = DriverManager.getConnection(CONNECTION_STR, CONNECTION_USERNAME, CONNECTION_PASSWORD)){
    		
    		Statement statement = conn.createStatement();
    		
    		val stringJoiner = new StringJoiner(",");     		
    		
    		 clients.stream().forEach(client -> stringJoiner.add(String.format(MASKFORLOAN, client.getId() )));
    		 val idClients = stringJoiner.toString();
    		
    		String sql = String.format(TEMPLATE_RECEIVEINDOLOAN, idClients);
    		
    		ResultSet resultSet = statement.executeQuery(sql);   		
    		
    		
			while (resultSet.next()) {	
				LoanOffer loanOffer = new LoanOffer();
				loanOffer.setIdLoan(resultSet.getInt(1));
				loanOffer.setIdClient(resultSet.getInt(2));
				loanOffer.setDateLoan(resultSet.getDate(3));
				loanOffer.setSummPayment(resultSet.getDouble(4));
				loanOffer.setSummBodyloan(resultSet.getDouble(5));
				loanOffer.setSummPercent(resultSet.getDouble(6));
				loanOffer.setContinueOffer(resultSet.getDouble(7));
				listLoanOffers.add(loanOffer);
			}
			
			
			
    		System.out.println(listLoanOffers);
    		
         } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return listLoanOffers;
    }
 }

