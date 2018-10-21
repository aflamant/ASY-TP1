public class Utilisateur {
  public static void main(String[] args) {
    Fournisseur f = new Fournisseur();
    int result = f.calcul(2);
    System.out.println(result);
    result = f.calcul2(2,3,4);
    System.out.println(result);
    
  }
}