
public class the_BATMAN_backend{
	
	public static void Create_New_User(){
	Inputs: 
	   FirstName varchar(30),
	   LastName varchar(30),
	   Age int,
	   Street varchar (30),
	   City varchar (30),
	   State char(2),
	   PhoneNumber int
	   AccountNumber int PRIMARY KEY, (<-- THIS SHOULD AUTO INCREMENT)   
	   CustomerID int,(<-- this should be autogenerated by AUTO INCREMENT)
	   AccountType varchar(8),
	   AccountValue float
	}

	public static void Delete_User(){
	Inputs: 
		FirstName varchar(30),
	  	LastName varchar(30),
	      Or 
		CustomerID
	}

	public static void Transfer(){
	Input:
	(FirstName varchar(30)
	  	LastName varchar(30)
		      Or 
		CustomerID),
		AccountNumberTO
		AccountNumberFROM
		Value
	}

	public static void Withdraw(){
	Inputs:
		(FirstName varchar(30)
	  	LastName varchar(30)
		      Or 
		CustomerID),
		AccountNumber
		Value
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

