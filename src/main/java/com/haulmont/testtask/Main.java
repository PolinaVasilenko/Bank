package com.haulmont.testtask;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	
    	BDSource bdSource = new BDSource();
    	//bdSource.getClient("select * from client");
    	
    	Client testClient = TestClient.getTestClient();
    	//bdSource.create(testClient);
    	bdSource.delete(testClient);
    	//testClient.getClientPassport();
    	
    	System.out.println("Ok"); 
    	
    	 
    }
    

}
