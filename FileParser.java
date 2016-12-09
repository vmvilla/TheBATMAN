public static FileToString(String fileName) throws Exception {
   File file = new File(fileName);

   Scanner scan = new Scanner(file);
   StringBuilder text = new StringBuilder();
   String line = "";

   while (scan.hasNextLine())
   {
      line = scan.nextLine();
      line = line.replaceAll(";", "; ");
      text.append(line);
      text.append("\n");
   }

   return text.toString();
}
