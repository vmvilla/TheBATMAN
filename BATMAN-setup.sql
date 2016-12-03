CREATE TABLE Customers (
   CustomerID int AUTO_INCREMENT,
   FirstName varchar(30),
   LastName varchar(30),
   Age int,
   Street varchar (50),
   City varchar (30),
   State char(2),
   PhoneNumber int,
   PRIMARY KEY(CustomerID),
   UNIQUE(FirstName, LastName)
);

CREATE TABLE Accounts (
   AccountNumber int AUTO_INCREMENT,   
   CustomerID int,
   AccountType varchar(8),
   AccountValue float,
   PRIMARY KEY(AccountNumber), 
   FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Transactions (
   TransactionID int AUTO_INCREMENT,
   AccountFrom int,
   AccountTo int,
   TransactionAmount float,
   TransactionType varchar(10),
   TransactionDate datetime,
   PRIMARY KEY(TransactionID),
   FOREIGN KEY (AccountFrom) REFERENCES Accounts(AccountNumber),
   FOREIGN KEY (AccountTo) REFERENCES Accounts(AccountNumber)
);

