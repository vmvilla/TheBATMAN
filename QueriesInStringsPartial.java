// CREATE NEW USER
//inputs: FirstName, LastName, Age, Street, City, State, Phone, AccountType, AccountValue
"INSERT INTO Customers (FirstName, LastName, Age, Street, City, State, PhoneNumber) VALUES('" + firstname + "', '" + lastname + "', " + age ", '" + street + "', '" + city + "', '" + state + "', " + phone + ");"
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname "';"
"INSERT INTO Accounts (CustomerID, AccountType, AccountValue) VALUES(" + customerId + ", '" + accountType + "', " + accountValue + ");"
"SELECT AccountNumber, AccountValue FROM Customers NATURAL JOIN Accounts WHERE CustomerID = " + customerId + ";
//return to user: CustomerID, AccountNumber, Balance, AccountType

//DELETE USER
//inputs: CustomerID or FirstName, LastName
//if First, Last
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"DELETE FROM Accounts WHERE CustomerID = " + customerId + ";"
"DELETE FROM Customers WHERE CustomerID = " + customerId + ";"
//return to user: 

//DELETE Account
//inputs: AccountNumber
"DELETE FROM Accounts WHERE AccountNumber = " + accountNumber + ";"
//return to user:

//GET ALL BALANCES FOR USER
//inputs: CustomerID or FirstName, LastName
//if First, Last
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"SELECT AccountType, AccountValue FROM Accounts NATURAL JOIN Customers WHERE CustomerID = " + customerId + ";"
//return to user: AccountNumber, AccountType, AccountValue

//GET BALANCE FOR ACCOUNT
//inputs: AccountNumber
"SELECT AccountValue, AccountType FROM Accounts NATURAL JOIN Customers WHERE AccountNumber = " + accountNumber + ";"
//return to user: AccountNumber, AccountType, AccountValue

//TRANSFER
//inputs: AccountNumberTo, AccountNumberFrom, Amount, CurrentDate
"INSERT INTO Transactions (AccountFrom, AccountTo, TransactionAmount, TransactionType, TransactionDate) VALUES (" + accountFrom + "," + accountTo + ", " + amount + ", 'TRANSFER', '" + date +  "');"
"UPDATE Accounts SET AccountValue = AccountValue - " + amount + " WHERE AccountNumber = " + accountFrom + ";"
"UPDATE Accounts SET AccountValue = AccountValue + " + amount + " WHERE AccountNumber = " + accountTo + ";"
"SELECT max(TransactionID) FROM Transactions;"
//return to user: AccountNumbers and balances, TransactionID and Amount

//DEPOSIT / WITHDRAW: accountFrom = accountTo
//inputs: AccountNumberFrom, Amount
"INSERT INTO Transactions (AccountFrom, AccountTo, TransactionAmount, TransactionType, TransactionDate) VALUES (" + accountFrom + "," + accountTo + ", " + amount + ", 'SELF', '" + date +  "');"
"UPDATE Accounts SET AccountValue = AccountValue + " + amount + " WHERE AccountNumber = " + accountTo + ";"
"SELECT max(TransactionID) FROM Transactions;"
//return to user: AccountNumber, TransactionID and Amount

//List Transactions for Account Overall
//inputs: AccountNumber
"SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accountNumber + " ORDER BY TransactionID;"
//return to user: items in select clause

//List Transactions for Account By Date
//inputs: AccountNumber, BeginDateRange, EndDateRange
"SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions WHERE AccountFrom = " + accountNumber + " AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "' ORDER BY TransactionID;"
//return to user: items in select clause


//List Transactions for User Overall
//inputs: CustomerID or FirstName, LastName
//if First, Last
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions, Accounts WHERE AccountFrom = AccountNumber AND CustomerID = " + customerId + " AND TransactionType = 'SELF' ORDER BY TransactionID;"

"SELECT TransactionID, AccountFrom, AccountTo, TransactionType, TransactionAmount, TransactionDate FROM Transactions, Accounts WHERE AccountFrom = AccountNumber AND CustomerID = " + customerId + " AND TransactionType = 'TRANSFER' ORDER BY TransactionID;"
//return to user: CustomerID, items in select clause


//List Transactions for User By Date
//inputs: CustomerID or FirstName, LastName, BeginDateRange, EndDateRange
//if First, Last
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"SELECT TransactionID, AccounNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions, Accounts WHERE AccountFrom = AccountNumber AND CustomerID = " + customerId + " AND TransactionType = 'SELF' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "' ORDER BY TransactionID;"

"SELECT TransactionID, AccountFrom, AccountTo, TransactionType, TransactionAmount, TransactionDate FROM Transactions, Accounts WHERE AccountFrom = AccountNumber AND CustomerID = " + customerId + " AND TransactionType = 'TRANSFER' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "' ORDER BY TransactionID;"
//return to user: CustomerID, items in select clause

//List Transactions By Date
//inputs: BeginDateRange, EndDateRange
"SELECT TransactionID, AccountNumber, TransactionType, TransactionAmount, TransactionDate FROM Transactions, Accounts WHERE TransactionType = 'SELF' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "' ORDER BY TransactionID;"

"SELECT TransactionID, AccountFrom, AccountTo, TransactionType, TransactionAmount, TransactionDate FROM Transactions, Accounts WHERE TransactionType = 'TRANSFER' AND TransactionDate >= '" + transactionFirstDate + "' AND TransactionDate <= '" + transactionSecondDate + "' ORDER BY TransactionID;" 
//return to user: items in select clause

//Customers By State
//inputs: State
"SELECT CustomerID, FirstName, LastName, Age FROM Customers WHERE State = '" + state + "' ORDER BY CustomerID;"
//return to user: items in select clause

//Customers By City
//inputs: City
"SELECT CustomerID, FirstName, LastName, Age FROM Customers WHERE City = '" + city + "' ORDER BY CustomerID;"
//return to user: items in select clause

//Customer Age Histogram: call 10 times with ranges 0, 9; 10, 19; 20, 29
//inputs: BeginAgeRange, EndAgeRange
"SELECT count(*) AS 'Num Customers' FROM Customers WHERE Age >= " + ageBegin + " AND Age <= " + ageEnd + ";" 
//return to user: 

//Customer Account Histogram Transacted 
//if First, Last
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"SELECT TransactionDate, AccountNumber, sum(TransactionAmount) AS 'Total Transacted' FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND CustomerID = " + customerId + " GROUP BY TransactionDate, AccountNumber;"
//return to user: date, amount

//Customer Account Histogram Withdrawals
//if First, Last
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"SELECT TransactionDate, sum(TransactionAmount) AS 'Total Withdrawn'  FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND TransactionType = 'SELF' AND TransactionAmount < 0 AND CustomerID = " + customerId + " GROUP BY TransactionDate;"
//return to user: date, amount

//Customer Account Histogram Deposits
//if First, Last
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"SELECT TransactionDate, sum(TransactionAmount) AS 'Total Deposited' FROM Transactions, Accounts WHERE AccountNumber = AccountTo AND TransactionType = 'SELF' AND TransactionAmount > 0 AND CustomerID = " + customerId + " GROUP BY TransactionDate;"
//return to user: date, amount

//Customer Account Histogram Transfers
//if First, Last
"SELECT CustomerID FROM Customers WHERE FirstName = '" + firstname + "' AND LastName = '" + lastname + "';"
//use customerID
"SELECT TransactionDate, sum(TransactionAmount) AS 'Total Transferred' FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND TransactionType = 'TRANSFER' AND CustomerID = " + customerId + " GROUP BY TransactionDate;"
//return to user: date, amount

//Account Value Histogram
"SELECT TransactionDate, sum(TransactionAmount) AS 'Account Balance' FROM Transactions, Accounts WHERE AccountNumber = AccountFrom AND AccountNumber = " + accountNumber + " GROUP BY TransactionDate;"
//return to user: data, balance


