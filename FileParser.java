import java.util.*;
import java.lang.*;
import java.io.*;

public class FileParser {

public static ArrayList<String> FileToString(String fileName) throws Exception {
   ArrayList<String> list = new ArrayList<String>();
   File file = new File(fileName);

   Scanner scan = new Scanner(file);
   StringBuilder query;
   String line;

   while (scan.hasNextLine())
   {
      line = "";
      query = new StringBuilder();
      while (scan.hasNextLine()) 
      {
         line = scan.nextLine(); 
         if (line.contains(";")) 
         {
            query.append(line.trim());
            break;
         }
         query.append(line.trim());
      }
      if (scan.hasNextLine() && !query.toString().contains(";"))
      {
         
         query.append("; "); 
      }
      if (!(query.toString().equals(""))) {
         list.add(query.toString());
      }
   }
   scan.close();

   return list;
}

public static void main(String args[]) throws Exception {
   ArrayList<String> list = FileToString(args[0]);
   int index = 0;
   for (String item : list) {
      System.out.println(++index + "	" + item);
   }
}
}
