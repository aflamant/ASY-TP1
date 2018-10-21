import java.net.Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.IOException;

public class Fournisseur {

  private static final String ipAddr = "127.0.0.1";
  private static final int port = 2018;

  private Socket s;
  private ObjectOutputStream out;
  private ObjectInputStream in;

  public Fournisseur() {
    try {
      s = new Socket(ipAddr,port);
      out = new ObjectOutputStream(s.getOutputStream());
      in = new ObjectInputStream(s.getInputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
  } 

  public int calcul(int x) {
    Object[] arguments = new Object[]{x};
    Requete r = new Requete("calcul", arguments);

    int result = -1;
    try {
      out.writeObject(r);
  
      result = (int) in.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public int calcul2(int x, int y, int z) {
    Object[] arguments = new Object[]{x,y,z};
    Requete r = new Requete("calcul2", arguments);   

    Object result = null;
    try {
      out.writeObject(r);
  
      result = (int) in.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (int) result;
  }
}