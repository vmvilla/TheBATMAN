import random

newFile = open("BATMAN-build-transactions.sql", 'w')
newFile.write("## BATMAN-build-transactions.sql\n")
newFile.write("## Team mySQL is better than yourSQL\n\n")

string = ""

for i in range(0,5023):
   ato = str(random.randint(1,1000))
   if random.randint(1,3) == 1:
      afrom = ato
   else:
      afrom = str(random.randint(1,1000))
   if random.randint(1,2) == 1:
      sign = "-"
   else:
      sign = ""
   amount = str(random.randint(0,1000)) + "." + str(random.randint(0,99)).zfill(2)
   date = '"' + str(random.randint(2012,2016)) + "-" + str(random.randint(1,12)).zfill(2) + "-" + str(random.randint(1,28)).zfill(2) + '"'

   string = afrom + "," + ato + "," + sign + amount + "," + date

   newFile.write("INSERT INTO Transactions(AccountFrom, AccountTo, TransactionAmount, TransactionDate) VALUES(" + string + ");\n")
