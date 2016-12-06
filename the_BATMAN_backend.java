
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


	public static void Deposit(){
	Inputs:
		(FirstName varchar(30)
	  	LastName varchar(30)
		      Or 
		CustomerID),
		AccountNumber
		Value
	}

	public static void List_Of_Transactions_Per_User(){
	Inputs:
	(FirstName varchar(30)
	  	LastName varchar(30)
		      Or 
		CustomerID),
		AccountNumber,
		DATE [OPTIONAL]
	}

	public static void List_Of_Transactions_Per_Date(){
		Inputs: 
		Date
	}

	public static void Customers_In_State(){
	Input:
		State
	}

	public static void Customers_In_City(){
	Input:
		City
	}

	public static void Customer_Age_Histogram(){
	Input:
		Age range?? Or none Or both?
	}

	public static void Customer_Account_Histogram_Transaction_Amounts(){
	Input:
		(FirstName varchar(30)
	  	 LastName varchar(30)
		      Or 
		 CustomerID),
		DATE [OPTIONAL]
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


