import java.util.*;
import java.lang.*;
import java.io.*;

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
            break;
         }
         query.append(line);
      }
      if (scan.hasNextLine())
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
