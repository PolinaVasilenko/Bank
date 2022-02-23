package com.haulmont.testtask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.Data;

@Data
public class Client {
	

   
   private int id;
   private String name;
   private String surname;
   private String secondName;
   private String phoneNumber;
   private String email;
   private int passport;
   
   
   
   
   }

