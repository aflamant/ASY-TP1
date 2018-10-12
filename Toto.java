import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Toto {
  public static void main(String[] args) {
    String test = "    public static int calcul (int x) {";

    
    Pattern p = Pattern.compile("(public|private) (static)+ ([a-zA-Z]+) ([a-zA-Z0-9]+) \\( *([a-zA-Z]+) ([a-zA-Z0-9]+) *\\) *\\{");
    Matcher m = p.matcher(test);
    while(m.find()){
      System.out.println(m.group());

      System.out.println(m.group(3));
      System.out.println(m.group(4));
      System.out.println(m.group(5));
      System.out.println(m.group(6));
    }
  }
}