import java.lang.*;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class create_BATMAN_Account_table{

   public static void write_Accounts(){
      File file = new File("BATMAN-build-Accounts.sql");
      DecimalFormat nf = new DecimalFormat("000000");
      int acount_temp = 0;

      try{
         PrintWriter writer = new PrintWriter(file);
         Scanner scanner = new Scanner(new File("Accounts.csv"));
         scanner.useDelimiter(",");

         writer.println("INSERT INTO Accounts ");
         writer.println("VALUES ");

         acount_temp = scanner.nextInt();
         writer.println("(" + nf.format(acount_temp) + scanner.nextLine() + ") ");

         while(scanner.hasNext()){
			 acount_temp = scanner.nextInt();
	         writer.println(",(" + nf.format(acount_temp) + scanner.nextLine() + ") ");
         }
         writer.println(";");

         writer.close();
         scanner.close();
      }
      catch (FileNotFoundException ex){

      }
   }

    public static void main(String args[]){
		System.out.println("\nWelcome to the BATMAN Data Base");
		write_Accounts();
    }
}
