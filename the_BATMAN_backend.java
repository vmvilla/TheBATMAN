
public class the_BATMAN_backend{
	
	public static void Create_New_User(String FirstName, String LastName, int age, String street, String city, String state, String phoneNum, String accntType, float accntValue){
		
	}

	public static void Delete_User(String FirstName, String LastName){
		
	}
	
	public static void Delete_User(int customerId){
		
	}

	public static void Transfer(int customerId, int accntTO int accntFROM, float value){
		
	}
	
	public static void Transfer(String FirstName, String LastName, int accntTO int accntFROM, float value){
	
	}

	public static void Withdraw(int customerId, int accntNum, float value){
		
	}
	
	public static void Withdraw(String FirstName, String LastName, int accntNum, float value){

	}


	public static void Deposit(int customerId, int accntNum, float value){
	}
	
	public static void Deposit(String FirstName, String LastName, int accntNum, float value){
	
	}
	
	public static void List_Of_Transactions_Per_User(int customerId, int accntNum){
	}

	public static void List_Of_Transactions_Per_User(int customerId, int accntNum, Date date){
		
	}
	
	public static void List_Of_Transactions_Per_User(String FirstName, String LastName, int accntNum){
		
	}

	public static void List_Of_Transactions_Per_User(String FirstName, String LastName, int accntNum, Date date){
		
	}
	
	public static void List_Of_Transactions_Per_Date(Date date){
	}

	public static void Customers_In_State(String state){
	
	}

	public static void Customers_In_City(String city){
	
	}

	public static void Customer_Age_Histogram(int beginAge, int endAge){
	
	}

	public static void Customer_Age_Histogram(){
	// group by decades
	}
	
	public static void Customer_Account_Histogram_Transaction_Amounts(int customerId){
		
	}
	public static void Customer_Account_Histogram_Transaction_Amounts(int customerId, Date date){
		
	}
	
	public static void Customer_Account_Histogram_Transaction_Amounts(String FirstName, String LastName){
	
	}

	public static void Customer_Account_Histogram_Transaction_Amounts(String FirstName, String LastName, Date date){
	
	}
	public static void Customer_Account_Histogram_Withdrawals(){
	Input:
		(FirstName varchar(30)
	  	 LastName varchar(30)
		      Or 
		 CustomerID),
		DATE [OPTIONAL]
	}

	public static void Customer_Account_Histogram_Deposits(){
	Input:
		(FirstName varchar(30)
	  	 LastName varchar(30)
		      Or 
		 CustomerID),
		DATE [OPTIONAL]
	}

	public static void Customer_Account_Histogram_Transfer(){
	Input:
		(FirstName varchar(30)
	  	 LastName varchar(30)
		      Or 
		 CustomerID),
		DATE [OPTIONAL]
    }

	public static void Customer_Account_Value_Histogram_Per_Customer(){
	Input:
	(FirstName varchar(30)
	  	LastName varchar(30)
		      Or 
		CustomerID),
		DATE [OPTIONAL]
	}

	public static void Customer_Account_Value_Analytics(){
	Input:
	DATE [OPTIONAL]
		avg, max, min,
	} 
}


