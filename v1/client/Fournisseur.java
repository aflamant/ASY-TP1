import java.net.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
public class Fournisseur {

  public static int calcul(int x) {

    int result = -1;
    try {

      Socket s = new Socket("127.0.0.1",2018);

      DataInputStream in = new DataInputStream(s.getInputStream());
      DataOutputStream out = new DataOutputStream(s.getOutputStream());

      out.writeInt(x);

      result = in.readInt();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}