// CREATE NEW USER
//inputs: FirstName, LastName, Age, Street, City, State, Phone, AccountType, AccountValue
"INSERT INTO Customers (FirstName, LastName, Age, Street, City, State, PhoneNumber) VALUES('" + firstname + "', '" + lastname + "', " + age ", '" + street + "', '" + city + "', '" + state + "', " + phone + ");"
"SELECT CustomerId FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname "';"
"INSERT INTO Accounts (CustomerId, AccountType, AccountValue) VALUES(" + customer + ", '" + accountType + "', " + accountValue + ");"

"SELECT AccountNumber, AccountValue FROM Customers NATURAL JOIN Accounts WHERE CustomerID = " + customerId + ";
//return to user: CustomerId, AccountNumber, Balance, AccountType

//DELETE USER
//inputs: CustomerId or FirstName, LastName
//if First, Last
"SELECT CustomerId FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"DELETE FROM Accounts WHERE CustomerID = " + customerId + ";"
"DELETE FROM Customers WHERE CustomerID = " + customerId + ";"
//return to user: CustomerId, FirstName, LastName was deleted

//GET ALL BALANCES FOR USER
//inputs: CustomerId or FirstName, LastName
//if First, Last
"SELECT CustomerId FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"SELECT AccountValue, AccountType FROM Accounts NATURAL JOIN Customers WHERE CustomerID = " + customerId + ";"
//return to user: AccountNumber, AccountType, AccountValue

//GET BALANCE FOR ACCOUNT
//inputs: AccountNumber
"SELECT AccountValue, AccountType FROM Accounts NATURAL JOIN Customers WHERE CustomerID = " + customerId + " AND AccountNumber = " + accountNumber + ";"
//return to user: AccountNumber, AccountType, AccountValue

//TRANSFER
//inputs: AccountNumberTo, AccountNumberFrom, Amount, CurrentDate
"INSERT INTO Transactions (AccountFrom, AccountTo, TransactionAmount, TransactionType, TransactionDate) VALUES (" + accountFrom + "," + accountTo + ", " + amount + ", 'transfer', '" + date +  "');"
"UPDATE Accounts SET AccountValue = AccountValue - " + amount + " WHERE AccountNumber = " + accountFrom + ";"
"UPDATE Accounts SET AccountValue = AccountValue + " + amount + " WHERE AccountNumber = " + accountTo + ";"
"SELECT max(TransactionID) FROM Transactions;"
"SELECT TransactionAmount FROM Transactions WHERE TransactionID = " + transactionId + ";"
//return to user: AccountNumbers and balances, TransactionID and Amount

//WITHDRAW: accountFrom = accountTo
//inputs: AccountNumberFrom, Amount
"INSERT INTO Transactions (AccountFrom, AccountTo, TransactionAmount, TransactionType, TransactionDate) VALUES (" + accountFrom + "," + accountTo + ", " + amount + ", 'withdraw', '" + date +  "');"
"UPDATE Accounts SET AccountValue = AccountValue - " + amount + " WHERE AccountNumber = " + accountFrom + ";"
"SELECT max(TransactionID) FROM Transactions;"
"SELECT CustomerId From Accounts WHERE AccountNumber = " + accountFrom + ";"
//return to user: CustomerId of account, AccountNumber, TransactionID and Amount

//DEPOSIT: accountFrom = accountTo
//inputs: AccountNumberTo, Amount
"INSERT INTO Transactions (AccountFrom, AccountTo, TransactionAmount, TransactionType, TransactionDate) VALUES (" + accountFrom + "," + accountTo + ", " + amount + ", 'deposit', '" + date +  "');"
"UPDATE Accounts SET AccountValue = AccountValue + " + amount + " WHERE AccountNumber = " + accountTo + ";"
"SELECT max(TransactionID) FROM Transactions;"
"SELECT CustomerId From Accounts WHERE AccountNumber = " + accountTo + ";"
//return to user: CustomerId of account, AccountNumber, TransactionID and Amount

//List Transactions for Account Overall
//inputs: AccountNumber
"((SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accountNumber + " AND TransactionType = 'deposit') UNION " +
"(SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accountNumber + " AND TransactionType = 'withdraw') UNION " +
"(SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accountNumber + " AND TransactionType = 'transfer')) ORDER BY TransactionID;"
//return to user: items in select clause

//List Transactions for Account By Date
//inputs: AccountNumber, BeginDateRange, EndDateRange
"((SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accountNumber + " AND TransactionType = 'deposit' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "') UNION " +
"(SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accountNumber + " AND TransactionType = 'withdraw' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "') UNION " +
"(SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accountNumber + " AND TransactionType = 'transfer' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "')) ORDER BY TransactionID;"
//return to user: items in select clause


//List Transactions for User Overall
//inputs: CustomerId or FirstName, LastName
//if First, Last
"SELECT CustomerId FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"((SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions, Accounts WHERE AccountFrom = AccountNumber AND CustomerID = " + customerId + " AND TransactionType = 'deposit') UNION " +
"(SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountTo = AccountNumber AND CustomerID = " + customerId + " AND TransactionType = 'withdraw') UNION " +
"(SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = AccountNumber AND CustomerID = " + customerId + "AND TransactionType = 'transfer')) ORDER BY TransactionID;"
//return to user: CustomerId, items in select clause


//List Transactions for User By Date
//inputs: CustomerId or FirstName, LastName, BeginDateRange, EndDateRange
//if First, Last
"SELECT CustomerId FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"((SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions, Accounts WHERE AccountFrom = AccountNumber AND CustomerID = " + customerId + " AND TransactionType = 'deposit' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "') UNION " +
"(SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountTo = AccountNumber AND CustomerID = " + customerId + " AND TransactionType = 'withdraw' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "') UNION " +
"(SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = AccountNumber AND CustomerID = " + customerId + "AND TransactionType = 'transfer' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "')) ORDER BY TransactionID;"
//return to user: CustomerId, items in select clause

//List Transactions By Date
//inputs: BeginDateRange, EndDateRange
"SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "' ORDER BY TransactionID;" 
//return to user: items in select clause

//Customers By State
//inputs: State
"SELECT CustomerID, FirstName, LastName, Age FROM Customers WHERE State = '" + state + "' ORDER BY CustomerID;"
//return to user: items in select clause

//Customers By City
//inputs: City
"SELECT CustomerID, FirstName, LastName, Age FROM Customers WHERE City = '" + city + "' ORDER BY CustomerID;"
//return to user: items in select clause



