import java.sql.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class the_BATMAN_backend {
   private Connection conn = null;
   private int target = 1; 
   private String url;
   private String username;
   private String password;
   
   public the_BATMAN_backend(String username, String password) {
      try {
         Class.forName("com.mysql.jdbc.Driver").newInstance();
      }
      catch (Exception e) {
         System.out.println("Driver not found");
         System.out.println(e);
         System.exit(-2);
      };
      
      url = "jdbc:mysql://cslvm74.csc.calpoly.edu/" + username + "?";
      this.username = username;
      this.password = password;
connConnect();
   }
   
   private void connConnect() {
      try {
         conn = DriverManager.getConnection(url + "user=" + username + "&password=" + password + "&");
      }
      catch (Exception e) {
         System.out.println("Could not open connection");
         System.out.println(e);
         System.exit(-3);
      }
      System.out.println(">Connected");
      try {
         Statement s1 = conn.createStatement();
         s1.executeUpdate("use zberson");
      }
      catch (Exception e) {

      };
   }

   private void connClose() {
      try{
         Statement s1 = conn.createStatement();
         s1.executeUpdate("commit;");
         conn.close();
         conn = null;
         System.out.println(">Closed");
      }
      catch(Exception e){
         System.out.println("Couldn't close connection");
      };
   }

   public void Create_Database() {
      ArrayList<String> list;
      int i = 0;

//   show_tables();
      try {
         Statement s1 = conn.createStatement();
         list = FileToString("BATMAN-setup.sql");
         while(i < list.size()) {
            s1.executeUpdate(list.get(i++));
         }
//s1.executeUpdate("describe Customers;");
         i = 0;
         list = FileToString("BATMAN-build-Customers.sql");
         while(i < list.size()) {
            s1.executeUpdate(list.get(i++));
         }
         i = 0;
         list = FileToString("BATMAN-build-Accounts.sql");
         while(i < list.size()) {
            s1.executeUpdate(list.get(i++));
         }
         i = 0;
         list = FileToString("BATMAN-build-Transactions.sql");
         while(i < list.size()) {
            s1.executeUpdate(list.get(i++));
         }
      }

      catch (Exception e) {
         System.out.println("Could not create database");
         System.out.println(e);
      };
   }
   
   public void Clear_Database() {
      ArrayList<String> list;
      int i = 0;

      try {
         Statement s1 = conn.createStatement();
         list = FileToString("BATMAN-cleanup.sql");
         while(i < list.size()) {
            s1.executeUpdate(list.get(i++));
         }
      }
      
      catch (Exception e) {
         System.out.println("Could not clean up database");
         System.out.println(e);
      };
   }
	
	public void Create_New_User(String FirstName, String LastName, int age, String street, String city, String state, String phoneNum, String accntType, float accntValue){
	}

	public void Delete_User(String FirstName, String LastName){
		
	}
	
	public void Delete_User(int customerId){
		
	}

	public void Transfer(int customerId, int accntTO, int accntFROM, float value) {
		
	}
	
	public void Transfer(String FirstName, String LastName, int accntTO, int accntFROM, float value){
	
	}

	public void Withdraw(int customerId, int accntNum, float value){
		
	}
	
	public void Withdraw(String FirstName, String LastName, int accntNum, float value){

	}


	public void Deposit(int customerId, int accntNum, float value){
	}
	
	public void Deposit(String FirstName, String LastName, int accntNum, float value){
	
	}
	
	public void List_Of_Transactions_Per_User(int customerId, int accntNum){
	}

	public void List_Of_Transactions_Per_User(int customerId, int accntNum, java.sql.Date date){
		
	}
	
	public void List_Of_Transactions_Per_User(String FirstName, String LastName, int accntNum){
		
	}

	public void List_Of_Transactions_Per_User(String FirstName, String LastName, int accntNum, java.sql.Date date){
		
	}
	
	public void List_Of_Transactions_Per_Date(java.sql.Date date){
	}

	public void Customers_In_State(String state){
	
	}

	public void Customers_In_City(String city){
	
	}

	public void Customer_Age_Histogram(int beginAge, int endAge){
	
	}

	public void Customer_Age_Histogram(){
	// group by decades
	}
	
	public void Customer_Account_Histogram_Transaction_Amounts(int customerId){
		
	}
	public void Customer_Account_Histogram_Transaction_Amounts(int customerId, java.sql.Date date){
		
	}
	
	public void Customer_Account_Histogram_Transaction_Amounts(String FirstName, String LastName){
	
	}

	public void Customer_Account_Histogram_Transaction_Amounts(String FirstName, String LastName, java.sql.Date date){
	
	}
	
	public void Customer_Account_Histogram_Withdrawals(int customerId){
	}
	public void Customer_Account_Histogram_Withdrawals(int customerId, java.sql.Date date){
	}
	public void Customer_Account_Histogram_Withdrawals(String FirstName, String LastName){
	}
	public void Customer_Account_Histogram_Withdrawals(String FirstName, String LastName, java.sql.Date date){
	}
	
	public void Customer_Account_Histogram_Deposits(int customerId){
	}
	
	public void Customer_Account_Histogram_Deposits(int customerId, java.sql.Date date){
	}
	
	public void Customer_Account_Histogram_Deposits(String FirstName, String LastName){
	}
	
	public void Customer_Account_Histogram_Deposits(String FirstName, String LastName, java.sql.Date date){
	}

	public void Customer_Account_Histogram_Transfer(int customerId){
	}
	public void Customer_Account_Histogram_Transfer(int customerId, java.sql.Date date){
	}
	
	public void Customer_Account_Histogram_Transfer(String FirstName, String LastName){
	}
	
	public void Customer_Account_Histogram_Transfer(String FirstName, String LastName, java.sql.Date date){
	}

	public void Customer_Account_Value_Histogram_Per_Customer(int customerId){
	}
	public void Customer_Account_Value_Histogram_Per_Customer(int customerId, java.sql.Date date){
	}
	
	public void Customer_Account_Value_Histogram_Per_Customer(String FirstName, String LastName){
	}
	
	public void Customer_Account_Value_Histogram_Per_Customer(String FirstName, String LastName, java.sql.Date date){
	}
/*
	public void Customer_Account_Value_Analytics(){
	//
	Input:
	java.sql.java.sql.java.sql.Date [OPTIONAL]
		avg, max, min,
	} 
*/

public void close() {
   connClose();
   System.exit(0);
}

private void show_tables() {
   try {
      connConnect();
      Statement s1 = conn.createStatement();
      s1.executeUpdate("show tables;");
      connClose();
   }
   catch (Exception e) {
      
   };
}

private static ArrayList<String> FileToString(String fileName) throws Exception {
   ArrayList<String> list = new ArrayList<String>();
   File file = new File(fileName);

   Scanner scan = new Scanner(file);
   StringBuilder query;
   String line;

   while (scan.hasNextLine())
   {
      line = "";
      query = new StringBuilder();
      while (scan.hasNextLine()) 
      {
         line = scan.nextLine(); 
         if (line.contains(";")) 
         {
            query.append(line.trim());
            break;
         }
         query.append(line.trim());
      }
      if (scan.hasNextLine() && !query.toString().contains(";"))
      {
         
         query.append("; "); 
      }
      if (!(query.toString().equals(""))) {
         list.add(query.toString());
      }
   }
   scan.close();

   return list;
}
}

