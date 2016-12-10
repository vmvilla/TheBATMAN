import java.sql.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class the_BATMAN_backend {
   private Connection conn;
   private int target; 
   private String url;
   private String username;
   private String password;
   private String database;
   
   public the_BATMAN_backend(String username, String password, String database) {
      conn = null;
      target = 1;
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
      this.database = database;
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
         s1.executeUpdate("use " + database + ";");
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

      //show_tables();
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

   public int CustomerId_From_Name(String FirstName, String LastName) {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT CustomerID FROM Customers WHERE FirstName = '" + FirstName + "' AND LastName = '" + LastName "';");
      resultBool = results.next();

      if (resultBool) {
         return results.getInt("CustomerId");
      }
      return -1;
   }

   public String[] Get_Customer_Name(int customerId) {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();
      String names[] = {"", ""};

      results = s1.executeQuery("SELECT FirstName, LastName FROM Customers WHERE CustomerID = " + customerId + ;");

      resultBool = results.next();
      if (resultBool) {
         names[0] = results.getString("FirstName");
         names[1] = results.getString("LastName");
      }
      return names;
   }

   public boolean Customer_Is_Valid(int customerId) {
      String names[] = Get_Customer_Name(customerId);
    
      if (!names[0].equals("") && !names[1].equals("")) {
         return true;
      }
      return false;
   }

   public int[] Create_New_User() {

      Statement s1 = conn.createStatement();
      ResultSet results;
      int returns[] = {-1, -1};
      int customerId;
      boolean resultBool;

      s1.executeUpdate("INSERT INTO Customers (FirstName, LastName, Age, Street, City, State, PhoneNumber) VALUES('" + FirstName + "', '" + LastName + "', " + age ", '" + street + "', '" + city + "', '" + state + "', " + phoneNum + ");");

      returns[0] = CustomerId_From_Name(FirstName, LastName);

      s1.executeUpdate("INSERT INTO Accounts (CustomerID, AccountType, AccountValue) VALUES(" + returns[0] + ", '" + accntType + "', " + accountValue + ");");
      results = s1.executeQuery("SELECT max(AccountNumber) AS AcctNum FROM Accounts;");
      resultBool = results.next();

      if (resultBool) {
         returns[1] = results.getInt("AcctNum");
      }

      return returns;

   }

   public void Delete_User(int customerId) {
      Statement s1 = conn.createStatement();
      s1.executeUpdate("DELETE FROM Accounts WHERE CustomerID = " + customerId + ";");
      s1.executeUpdate("DELETE FROM Customers WHERE CustomerID = " + customerId + ";");
   }

   public void Delete_User(String FirstName, String LastName) {
      Delete_User(CustomerId_From_Name(FirstName, LastName));
   }

   public void Delete_Account(int accountNumber) {
      Statement s1 = conn.createStatement();
      s1.executeUpdate("DELETE FROM Accounts WHERE AccountNumber = " + accountNumber + ";");
   }

   public float[] Get_All_Accounts_Balances(int customerId) {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();
      float returns[10] = {0};
      int i = 0;

      results = s1.executeQuery("SELECT AccountNumber, AccountValue FROM Accounts NATURAL JOIN Customers WHERE CustomerID = " + customerId + ";");
      resultBool = results.next();

      while (resultBool && i < returns.length) {
         returns[i++] = (float)results.getInt("AccountNumber");
         returns[i++] = results.getFloat("AccountValue");
         resultBool = results.next();
      }

      return returns;
   }

   public float Get_Account_Value(int accntNumber) {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT AccountValue FROM Accounts WHERE AccountNumber = " + accntNumber + ";");
      resultBool = results.next();
  
      if (resultBool) {
            return results.getFloat("AccountValue");
      }

      return -1;
   }

   private int Get_Newest_TransID() {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT max(TransactionID) AS TransID FROM Transactions;");
      resultBool = results.next();

      if (resultBool) {
         return results.getInt("TransID");
      }
      return -1;
   }

   private void Update_Accounts(float value, int accntNumber) {
      Statement s1 = conn.createStatement();

      s1.executeUpdate("UPDATE Accounts SET AccountValue = AccountValue + " + value + " WHERE AccountNumber = " + accntNumber + ";");
   }

   private void Create_Transaction(int accntFrom, int accntTo, float value, String accntType, Date date) {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      s1.executeUpdate("INSERT INTO Transactions (AccountFrom, AccountTo, TransactionAmount, TransactionType, TransactionDate) VALUES " +
                       "(" + accntFrom + "," + accntTo + ", " + value + ", '" + accntType + "', '" + date +  "');");

   } 

   public Date Get_Current_Date() {
   }

   public float[] Transfer(int accntTO, int accntFROM, float value) {
      float returns[] = {-1, -1, -1};
      Create_Transaction(accntFROM, accntTO, float value, "TRANSFER", date);
      Update_Accounts((-1 * value), accountFROM);
      Update_Accounts(value, accountTO);
      returns[0] = (float)Get_Newest_TransID();
      returns[1] = Get_Account_Value(accntFROM);
      returns[2] = Get_Account_Value(accntTO);

      return returns;
   }

   public void Withdraw(int accntNum, float value) {
      Create_Transaction(accntNum, accntNum, value, "SELF", date);
      Update_Accounts((-1 * value), accntFROM);
      return Get_Account_Value(accntFROM);
   }

   public void Deposit(int accntNum, float value) {
      Create_Transaction(accntNum, accntNum, value, "SELF", date);
      Update_Accounts(value, accntTO);
      return Get_Account_Value(accntTO);
   }

   public String Get_Transaction_Date(int transactionID) {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();
      
      results = s1.executeQuery("SELECT TransactionDate FROM Transactions WHERE TransactionID = " + transactionID); 
      resultBool = results.next();

      if (resultBool) {
         return results.getDate("TransactionDate").toString();
      }

      return "";
   }

   public String[] List_Of_Transactions_Per_User(int customerId, int accntNum) {
      ResultSet results;
      boolean resultBool;
      String returns[10] = {""};
      int i = 0;
      Statement s1 = conn.createStatement();
      
      results = s1.executeQuery("((SELECT TransactionID, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accntNum + " AND TransactionAmount < 0 AND TransactionType = 'SELF') UNION (SELECT TransactionID, TransactionAmount, TransactionDate FROM Transactions WHERE AccountTo = " + accntNum + " AND TransactionAmount >= 0 AND TransactionType = 'SELF') UNION (SELECT TransactionID, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accntNum + " AND TransactionType = 'TRANSFER')) ORDER BY TransactionID;");
      
      resultBool = results.next();

      while (resultBool && i < 10) {
         returns[i++] = results.getInt("TransactionID") + ": $" + results.getFloat("TransactionAmount") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      }

      return returns; 
   } 

   public int Customers_In_State(String state){
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT count(CustomerID) AS Num_Customers FROM Customers WHERE State = '" + state + "';");
      resultBool = results.next();
      
      if (resultBool) {
         return results.getInt("Num_Customers");
      }
      return -1;
   }

   public void Customers_In_City(String city){
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT count(CustomerID) AS Num_Customers FROM Customers WHERE City = '" + city + "';");
      resultBool = results.next();
      
      if (resultBool) {
         return results.getInt("Num_Customers");
      }
      return -1;
   }  

   public String Customer_Age_Histogram(int beginAge, int endAge){
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();
      String result = "";

      results = s1.executeQuery("SELECT count(*) AS Num_Customers FROM Customers WHERE Age >= " + beginAge + " AND Age < " + endAge + ";" );
      resultBool = results.next();
      
      if (resultBool) {
         return results.getString("Num_Customers");
      }
      return "";
   }

   public String[] Customer_Age_Histogram(){
      String results[10] = {""};   
      int beginAge = 0, i = 0;      

      while (beginAge < 100 && i < 10) {
         results[i++] = beginAge + "-" + (beginAge + 10) + ": " + Customer_Age_Histogram(beginAge, beginAge + 10);
         beginAge += 10;
      }

      return results; 
   }

   public String[] Customer_Account_Histogram_Transaction_Amounts(int customerId) {
      ResultSet results;
      boolean resultBool;
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[10] = "";
      
      results = s1.executeQuery("SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS Total_Transacted FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND CustomerID = " + customerId + " GROUP BY AccountNumber, TransactionDate;");
      resultBool = results.next();
      
      while (resultBool && i < 10) {
         returns[i++] = results.getInt("AccountNumber") + ": $" + results.getFloat("Total_Transacted") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next(); 
      }
      return returns;
   }

   public String[] Customer_Account_Histogram_Withdrawals(int customerId) {
      ResultSet results;
      boolean resultBool;
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[10] = "";

      results = s1.executeQuery("SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS Total_Withdrawn FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND TransactionType = 'SELF' AND CustomerID = " + customerId + " GROUP BY AccountNumber, TransactionDate;");
      resultBool = results.next();

      while (resultBool && i < 10) {
         returns[i++] = results.getInt("AccountNumber") + ": $" + results.getFloat("Total_Withdrawn") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      }
      return returns;
   }
  
   public String[] Customer_Account_Histogram_Deposits(int customerId) {
      ResultSet results;
      boolean resultBool;
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[10] = "";

      results = s1.executeQuery("SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS Total_Deposited FROM Transactions, Accounts WHERE AccountNumber = AccountTo AND TransactionType = 'SELF' AND CustomerID = " + customerId + " GROUP BY AccountNumber, TransactionDate;");
      resultBool = results.next();

      while (resultBool && i < 10) {
         returns[i++] = results.getInt("AccountNumber") + ": $" + results.getFloat("Total_Deposited") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      }
      return returns;
   }

   public String[] Customer_Account_Histogram_Transfers(int customerId) {
      ResultSet results;
      boolean resultBool;
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[10] = "";

      results = s1.executeQuery("SELECT TransactionDate, AccountFrom, AccountTo, sum(TransactionAmount) AS Total_Transferred FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND TransactionType = 'TRANSFER' AND CustomerID = " + customerId + " GROUP BY AccountFrom, TransactionDate;");
      resultBool = results.next();

      while (resultBool && i < 10) {
         returns[i++] = results.getInt("AccountFrom") + " -> " + results.getInt("AccountTo") + ": $" + results.getFloat("Total_Transferred") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      }
      return returns;
   }

   public String[] Customer_Account_Value_Histogram_Per_Customer(int customerId){
      ResultSet results;
      boolean resultBool;      
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[10] = {""};

      results = s1.execute("SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS Account_Balance FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND CustomerID = " + customerId + " GROUP BY TransactionDate, AccountNumber;");
      resultBool = results.next();
      
      while (resultBool && i < 10) {
         returns[i++] = results.getInt("AccountNumber") + ": $" + results.getFloat("Account_Balance") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      } 
      return returns;
   } 
} 

