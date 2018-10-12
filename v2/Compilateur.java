import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.stream.FileImageOutputStream;

public class Compilateur  {

  public static void main(String[] args) throws Exception{
    FileReader file = new FileReader(args[0]);
    FileWriter stub = new FileWriter("client/Fournisseur.java");
    FileWriter skeleton = new FileWriter("serveur/Skeleton.java");    
    BufferedReader reader = new BufferedReader(file);
    BufferedWriter stubWriter = new BufferedWriter(stub);
    BufferedWriter skeletonWriter = new BufferedWriter(skeleton);

    Pattern signature = Pattern.compile("(public|private) (static)+ ([a-zA-Z]+) ([a-zA-Z0-9]+) \\( *([a-zA-Z]+) ([a-zA-Z0-9]+) *\\) *\\{");

    Matcher matcher;
    String name;
    String line;

    while ( (line = reader.readLine()) != null ) {

      matcher = signature.matcher(line);
      if (matcher.find()){
        System.out.println("It's a match!");
      System.out.println(matcher.group());

      System.out.println(matcher.group(3));
      System.out.println(matcher.group(4));
      System.out.println(matcher.group(5));
      System.out.println(matcher.group(6));

      }
    }
  }
  
}