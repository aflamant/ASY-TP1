import java.io.Serializable;

public class Requete implements Serializable {

  private static final long serialVersionUID = 2675457358324132785L;
  
  public String name;
  public Object[] arguments;

  public Requete(String name, Object[] arguments) {
    this.name = name;
    this.arguments = arguments;
  }
}