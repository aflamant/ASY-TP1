import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Skeleton {
  public static void main(String[] args) {
    ServerSocket sos = null;
    try {
      sos = new ServerSocket(2018);
    } catch (Exception e) {
      e.printStackTrace();
    }

    ObjectOutputStream out;
    ObjectInputStream in;
    Socket s;

    while(true) {
      try {

        s = sos.accept();

        out = new ObjectOutputStream(s.getOutputStream());
        in = new ObjectInputStream(s.getInputStream());

        Fournisseur f = new Fournisseur();
        
        while (true) {
          Requete requete = (Requete) in.readObject();
          Object result;
          switch (requete.name) {
            case "calcul":
              result = f.calcul((int) requete.arguments[0]);
              break;
            case "calcul2":
              result = f.calcul2((int) requete.arguments[0], (int) requete.arguments[1], (int) requete.arguments[2]);
              break;
            default:
              result = null;
              System.err.println("Fonction inconnue par le serveur.");
              break;
          }
          out.writeObject(result);
        }
      } catch (EOFException e) {
        //ignore exception
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}