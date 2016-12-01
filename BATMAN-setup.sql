CREATE TABLE Customers (
   CustomerID int PRIMARY KEY,
   FirstName varchar(30),
   LastName varchar(30),
   Age int,
   Street varchar (30),
   City varchar (30),
   State char(2)
   PhoneNumber int
);


CREATE TABLE Transactions (
   TransactionID int PRIMARY KEY,
   AccountFrom int,
   AccountTo int,
   TransactionAmount float,
   TransactionType varchar(10),
   TransactionDate datetime,
   FOREIGN KEY AccountFrom REFERENCES Accounts(AccountNumber),
   FOREIGN KEY AccountTo REFERENCES Accounts(AccountNumber)
);


CREATE TABLE Accounts (
   AccoutNumber int PRIMARY KEY,   
   CustomerID int,
   AccountType varchar(8),
   FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);
