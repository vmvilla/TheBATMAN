import java.lang.*;
import java.io.*;
import java.util.*;

public class create_BATMAN_Account_table{

   public static void write_Accounts(){
      File file = new File("BATMAN-build-Accounts.sql");
      try{
         PrintWriter writer = new PrintWriter(file);
         Scanner scanner = new Scanner(new File("Accounts.csv"));
         scanner.nextLine();

         writer.println("INSERT INTO Accounts ");
         writer.println("VALUES ");
         writer.println("(" + scanner.nextLine() + ") ");
         while(scanner.hasNext()){
         writer.println(",(" + scanner.nextLine() + ") ");
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
