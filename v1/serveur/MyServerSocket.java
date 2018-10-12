import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {
  public static void main(String[] args) {
    try {
      ServerSocket sos = new ServerSocket(2018);

      Socket s = sos.accept();

      
      DataInputStream in = new DataInputStream(s.getInputStream());
      DataOutputStream out = new DataOutputStream(s.getOutputStream());

      int param = in.readInt();

      out.writeInt(Fournisseur.calcul(param));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}