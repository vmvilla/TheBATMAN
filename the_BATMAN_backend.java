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
      System.out.println("Connected");
      try {
         Statement s1 = conn.createStatement();
         s1.executeUpdate("use " + database + ";");
      }
      catch (Exception e) {

      };
   }

   public void connClose() {
      try{
         Statement s1 = conn.createStatement();
         s1.executeUpdate("commit;");
         conn.close();
         conn = null;
         System.out.println("Closed");
      }
      catch(Exception e){
         System.out.println("Couldn't close connection");
      };
   }

   public void Create_Database() {
      ArrayList<String> list;
      int i = 0;

      try {
         Statement s1 = conn.createStatement();
         list = FileToString("BATMAN-setup.sql");
         while(i < list.size()) {
            s1.executeUpdate(list.get(i++));
         }
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
         System.out.println("Database has been created");
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
         System.out.println("Database has been cleaned");
      }
      
      catch (Exception e) {
         System.out.println("Could not clean up database");
         System.out.println(e);
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

   public int CustomerId_From_Name(String FirstName, String LastName) throws Exception {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT CustomerID FROM Customers WHERE FirstName = '" + FirstName + "' AND LastName = '" + LastName +"';");
      resultBool = results.next();

      if (resultBool) {
         return results.getInt("CustomerId");
      }
      return -1;
   }

   public String[] Get_Customer_Name(int customerId) throws Exception {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();
      String names[] = {"", ""};

      results = s1.executeQuery("SELECT FirstName, LastName FROM Customers WHERE CustomerID = " + customerId + ";");

      resultBool = results.next();
      if (resultBool) {
         names[0] = results.getString("FirstName");
         names[1] = results.getString("LastName");
      }
      return names;
   }

   public boolean Customer_Is_Valid(int customerId) throws Exception {
      String names[] = Get_Customer_Name(customerId);
    
      if (!(names[0].equals("")) && !(names[1].equals(""))) {
         return true;
      }
      return false;
   }

   public int[] Create_New_User(String FirstName, String LastName, int age, String street, String city, String state, int phoneNum, float accountValue) throws Exception {
      Statement s1 = conn.createStatement();
      ResultSet results;
      int returns[] = {-1, -1};
      int customerId;
      boolean resultBool;

      s1.executeUpdate("INSERT INTO Customers (FirstName, LastName, Age, Street, City, State, PhoneNumber) VALUES('" + FirstName + "', '" + LastName + "', " + age + ", '" + street + "', '" + city + "', '" + state + "', " + phoneNum + ");");

      returns[0] = CustomerId_From_Name(FirstName, LastName);

      s1.executeUpdate("INSERT INTO Accounts (CustomerID, AccountType, AccountValue) VALUES(" + returns[0] + ", 'CHECKING', " + accountValue + ");");
      results = s1.executeQuery("SELECT max(AccountNumber) AS AcctNum FROM Accounts;");
      resultBool = results.next();

      if (resultBool) {
         returns[1] = results.getInt("AcctNum");
      }

      return returns;

   }

   public void Delete_User(int customerId) throws Exception {
      Statement s1 = conn.createStatement();
      ResultSet results;
      ArrayList<Integer> list = new ArrayList<Integer>();
      boolean resultBool;
      int index = 0;
      
      results = s1.executeQuery("SELECT AccountNumber FROM Accounts WHERE CustomerID = " + customerId);
      resultBool = results.next();
      while (resultBool) {
         list.add(results.getInt("AccountNumber"));
         resultBool = results.next();
      }
      for (index = 0; index < list.size(); index++) {
         Delete_Account(list.get(index));
      }

      s1.executeUpdate("DELETE FROM Customers WHERE CustomerID = " + customerId + ";");
   }

   public void Delete_User(String FirstName, String LastName) throws Exception {
      Delete_User(CustomerId_From_Name(FirstName, LastName));
   }

   public void Delete_Account(int accountNumber) throws Exception {
      Statement s1 = conn.createStatement();

      s1.executeUpdate("DELETE FROM Transactions WHERE AccountFrom = " + accountNumber + ";");
      s1.executeUpdate("DELETE FROM Transactions WHERE AccountTo = " + accountNumber + ";");
      s1.executeUpdate("DELETE FROM Accounts WHERE AccountNumber = " + accountNumber + ";");
   }

   public String[] Get_All_Accounts_Balances(int customerId) throws Exception {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();
      String returns[] = new String[10];
      int i = 0;
       
      results = s1.executeQuery("SELECT AccountNumber, AccountValue FROM Accounts NATURAL JOIN Customers WHERE CustomerID = " + customerId + ";");
      resultBool = results.next();

      while (resultBool && i < returns.length) {
         returns[i++] = results.getInt("AccountNumber") + ": $" + String.format("%.2f", results.getFloat("AccountValue"));
         resultBool = results.next();
      }

      return returns;
   }

   public String Get_Account_Value(int accntNumber) throws Exception {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT AccountValue FROM Accounts WHERE AccountNumber = " + accntNumber + ";");
      resultBool = results.next();
  
      if (resultBool) {
            return (accntNumber + ": $" + String.format("%.2f", results.getFloat("AccountValue")));
      }

      return "";
   }

   private int Get_Newest_TransID() throws Exception {
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

   private void Update_Accounts(float value, int accntNumber) throws Exception {
      Statement s1 = conn.createStatement();

      s1.executeUpdate("UPDATE Accounts SET AccountValue = AccountValue + " + value + " WHERE AccountNumber = " + accntNumber + ";");
   }

   private void Create_Transaction(int accntFrom, int accntTo, float value, String accntType, java.sql.Date date) throws Exception {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      s1.executeUpdate("INSERT INTO Transactions (AccountFrom, AccountTo, TransactionAmount, TransactionType, TransactionDate) VALUES " +
                       "(" + accntFrom + "," + accntTo + ", " + value + ", '" + accntType + "', '" + date +  "');");

   } 

   public java.sql.Date Get_Current_Date() {
      java.util.Date date = new java.util.Date();
      java.sql.Date sqlDate = new java.sql.Date( date.getTime() );

      return sqlDate;
   }

   public String Transfer(int accntTO, int accntFROM, float value) throws Exception {
      String returns;
      Create_Transaction(accntFROM, accntTO, value, "TRANSFER", Get_Current_Date());
      Update_Accounts((-1 * value), accntFROM);
      Update_Accounts(value, accntTO);

      returns = ("Transaction: " + Get_Newest_TransID() + " AccountFrom: " + Get_Account_Value(accntFROM) + " --> AccountTo: " + Get_Account_Value(accntTO));

      return returns;
   }

   public String Withdraw(int accntNum, float value) throws Exception {
      Create_Transaction(accntNum, accntNum, value, "SELF", Get_Current_Date());
      Update_Accounts((-1 * value), accntNum);
      return ("Transaction: " + Get_Newest_TransID() + " AccountNumber: " + Get_Account_Value(accntNum));
   }

   public String Deposit(int accntNum, float value) throws Exception {
      Create_Transaction(accntNum, accntNum, value, "SELF", Get_Current_Date());
      Update_Accounts(value, accntNum);
      return ("Transaction: " + Get_Newest_TransID() + " AccountNumber: " + Get_Account_Value(accntNum));
   }

   public String[] List_Of_Transactions_Per_User(int customerId, int accntNum) throws Exception {
      ResultSet results;
      boolean resultBool;
      String returns[] = new String[10];
      int i = 0;
      Statement s1 = conn.createStatement();
      
      results = s1.executeQuery("(SELECT TransactionID, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accntNum + " AND TransactionType = 'SELF') UNION (SELECT TransactionID, TransactionAmount, TransactionDate FROM Transactions WHERE AccountTo = " + accntNum + " AND TransactionType = 'TRANSFER') UNION (SELECT TransactionID, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accntNum + " AND TransactionType = 'TRANSFER') ORDER BY TransactionID;");
      
      resultBool = results.next();

      while (resultBool && i < 10) {
         returns[i++] = "Transaction: " + results.getInt("TransactionID") + ": $" + results.getFloat("TransactionAmount") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      }

      return returns; 
   } 

   public String Customers_In_State(String state) throws Exception {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT count(CustomerID) AS Num_Customers FROM Customers WHERE State = '" + state + "';");
      resultBool = results.next();
      
      if (resultBool) {
         return ("Customers In " + state + ": " + results.getInt("Num_Customers"));
      }
      return "";
   }

   public String Customers_In_City(String city) throws Exception {
      ResultSet results;
      boolean resultBool;
      Statement s1 = conn.createStatement();

      results = s1.executeQuery("SELECT count(CustomerID) AS Num_Customers FROM Customers WHERE City = '" + city + "';");
      resultBool = results.next();
      
      if (resultBool) {
         return ("Customers In " + city + ": " + results.getInt("Num_Customers"));
      }
      return "";
   }  

   public String Customer_Age_Histogram(int beginAge, int endAge) throws Exception {
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

   public String[] Customer_Age_Histogram() throws Exception {
      String results[] = new String[10];   
      int beginAge = 0, i = 0;      

      while (beginAge < 100 && i < 10) {
         results[i++] = beginAge + "-" + (beginAge + 10) + ": " + Customer_Age_Histogram(beginAge, beginAge + 10);
         beginAge += 10;
      }

      return results; 
   }

   public String[] Customer_Account_Histogram_Transaction_Amounts(int customerId) throws Exception {
      ResultSet results;
      boolean resultBool;
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[] = new String[10];
      
      results = s1.executeQuery("SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS Total_Transacted FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND CustomerID = " + customerId + " GROUP BY AccountNumber, TransactionDate ORDER BY TransactionDate, AccountNumber;");
      resultBool = results.next();
      
      while (resultBool && i < 10) {
         returns[i++] = "Customer: " + customerId + " AccountNumber: " + results.getInt("AccountNumber") + ": $" + results.getFloat("Total_Transacted") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next(); 
      }
      return returns;
   }

   public String[] Customer_Account_Histogram_Withdrawals(int customerId) throws Exception {
      ResultSet results;
      boolean resultBool;
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[] = new String[10];

      results = s1.executeQuery("SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS Total_Withdrawn FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND TransactionAmount < 0 AND TransactionType = 'SELF' AND CustomerID = " + customerId + " GROUP BY AccountNumber, TransactionDate ORDER BY TransactionDate, AccountNumber;");
      resultBool = results.next();

      while (resultBool && i < 10) {
         returns[i++] = "Customer: " + customerId + " AccountNumber: " + results.getInt("AccountNumber") + ": $" + results.getFloat("Total_Withdrawn") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      }
      return returns;
   }
  
   public String[] Customer_Account_Histogram_Deposits(int customerId) throws Exception {
      ResultSet results;
      boolean resultBool;
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[] = new String[10];

      results = s1.executeQuery("SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS Total_Deposited FROM Transactions, Accounts WHERE AccountNumber = AccountTo AND TransactionAmount >= 0 AND TransactionType = 'SELF' AND CustomerID = " + customerId + " GROUP BY AccountNumber, TransactionDate ORDER BY TransactionDate, AccountNumber;");
      resultBool = results.next();

      while (resultBool && i < 10) {
         returns[i++] = "Customer: " + customerId + " AccountNumber: " + results.getInt("AccountNumber") + ": $" + results.getFloat("Total_Deposited") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      }
      return returns;
   }

   public String[] Customer_Account_Histogram_Transfers(int customerId) throws Exception {
      ResultSet results;
      boolean resultBool;
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[] = new String[10];

      results = s1.executeQuery("SELECT TransactionDate, AccountFrom, AccountTo, sum(TransactionAmount) AS Total_Transferred FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND TransactionType = 'TRANSFER' AND CustomerID = " + customerId + " GROUP BY AccountNumber, TransactionDate ORDER BY AccountNumber, TransactionDate;");
      resultBool = results.next();

      while (resultBool && i < 10) {
         returns[i++] = "AccountFrom: " + results.getInt("AccountFrom") + " -> AccountTo: " + results.getInt("AccountTo") + ": $" + results.getFloat("Total_Transferred") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      }
      return returns;
   }

   public String[] Customer_Account_Value_Histogram_Per_Customer(int customerId) throws Exception {
      ResultSet results;
      boolean resultBool;      
      int i = 0;
      Statement s1 = conn.createStatement();
      String returns[] = new String[10];

      results = s1.executeQuery("SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS Account_Balance FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND CustomerID = " + customerId + " GROUP BY AccountNumber, TransactionDate ORDER BY TransactionDate, AccountNumber;");
      resultBool = results.next();
      
      while (resultBool && i < 10) {
         returns[i++] = "Customer: " + customerId + " AccountNumber: " + results.getInt("AccountNumber") + ": $" + results.getFloat("Account_Balance") + ": " + results.getDate("TransactionDate").toString();
         resultBool = results.next();
      } 
      return returns;
   } 
} 

