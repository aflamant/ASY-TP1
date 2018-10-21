import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compilateur {

  public static void main(String[] args) throws Exception {
    FileReader file = new FileReader(args[0]);
    String className = args[0].replaceAll(".*\\/|.java", "");
    String address = args[1];
    String port = args[2];

    FileWriter stub = new FileWriter(className +".java");
    FileWriter skeleton = new FileWriter("Skeleton.java");
    BufferedReader reader = new BufferedReader(file);
    BufferedWriter stubWriter = new BufferedWriter(stub);
    BufferedWriter skeletonWriter = new BufferedWriter(skeleton);

    Pattern signature = Pattern.compile(
        "(public|private|protected) (static )?([a-zA-Z]+) ([a-zA-Z0-9]+) *(\\( *([a-zA-Z]+ [a-zA-Z0-9]+)?( *, *[a-zA-Z]+ [a-zA-Z0-9])* *\\)) *\\{");

    Matcher matcher;

    String line;
    String[][] params;

    writeSkeletonHeader(skeletonWriter, port);
    writeStubHeader(stubWriter, address, port, className);

    while ((line = reader.readLine()) != null) {

      matcher = signature.matcher(line);
      if (matcher.find()) {
        System.out.println(matcher.group());

        System.out.println("group(1) : " + matcher.group(1)); // (public|private|protected)
        System.out.println("group(2) : " + matcher.group(2)); // (static)?
        System.out.println("group(3) : " + matcher.group(3)); // type de retour
        System.out.println("group(4) : " + matcher.group(4)); // nom de la fonction
        System.out.println("group(5) : " + matcher.group(5)); // paramètres

        String [] preParams = matcher.group(5).replaceAll("( *\\( *| *\\) *)", "").split(" *, *"); // Extraction des paramètres

        if (!preParams[0].equals("")) params = new String[preParams.length][];
        else params = new String[0][];
        for (int i =0; i<params.length; i++) {
          params[i] = preParams[i].split(" ");
        }
        

        System.out.print("Paramètres : -");
        for (String[] param : params) {
          System.out.print(param[0] + "-");
        }
        System.out.println();

        // System.out.println("group(6) : " + matcher.group(6));
        // System.out.println("group(7) : " + matcher.group(7));
        // System.out.println("group(8) : " + matcher.group(8));
        // System.out.println("group(9) : " + matcher.group(9));

        writeSkeletonCase(skeletonWriter, matcher.group(1), matcher.group(3), matcher.group(4), params);
        writeStubFunction(stubWriter, matcher.group(1), matcher.group(3), matcher.group(4), params);
      }
    }
    writeSkeletonFooter(skeletonWriter);
    stubWriter.write("}\n");

    skeletonWriter.close();
    stubWriter.close();
    reader.close();
  }

  private static void writeSkeletonHeader(BufferedWriter writer, String port) {
    String imports = "import java.io.EOFException;\n" + "import java.io.IOException;\n"
        + "import java.io.ObjectInputStream;\n" + "import java.io.ObjectOutputStream;\n"
        + "import java.net.ServerSocket;\n" + "import java.net.Socket;\n";
    try {
      writer.write(imports);
      writer.write("public class Skeleton {\n");
      writer.write("  public static void main(String[] args) {\n");
      writer.write("    ServerSocket sos = null;\n");
      writer.write("    try { sos = new ServerSocket(" + port + "); } catch (Exception e) { e.printStackTrace(); }\n");
      writer.write("    ObjectOutputStream out;\n" + "    ObjectInputStream in;\n" + "    Socket s;\n");
      writer.write("    while(true) {\n");
      writer.write("      try {\n");
      writer.write("        s = sos.accept();\n" + "        out = new ObjectOutputStream(s.getOutputStream());\n"
          + "        in = new ObjectInputStream(s.getInputStream());\n");
      writer.write("        Fournisseur f = new Fournisseur();\n");
      writer.write("        while (true) {\n");
      writer.write("          Requete requete = (Requete) in.readObject();\n");
      writer.write("          Object result;\n");
      writer.write("          switch (requete.name) {\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void writeSkeletonCase(BufferedWriter writer, String privacy, String returnType, String name,
      String[][] params) {
      try {
        writer.write("            case \""+ name +"\":\n");
        writer.write("              result = f." + name + "(");
        int i = 0;
        while (i < params.length) {
          writer.write("(" +params[i][0] + ") requete.arguments["+ i +"]");
          i++;
          if (i!= params.length) writer.write(",");
        }
        writer.write(");\n");
        writer.write("              break;\n");
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  private static void writeSkeletonFooter(BufferedWriter writer) {
    try {
      writer.write("            default:\n");
      writer.write("              result = null;\n");
      writer.write("              System.err.println(\"Fonction inconnue par le serveur.\");\n");
      writer.write("              break;\n");
      writer.write("          }\n");
      writer.write("          out.writeObject(result);\n");
      writer.write("        }\n");
      writer.write("      } catch (EOFException e) {\n");
      writer.write("      } catch (Exception e) {e.printStackTrace();}\n");
      writer.write("    }\n");
      writer.write("  }\n");
      writer.write("}\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void writeStubHeader(BufferedWriter writer, String address, String port, String className){
    String imports = "import java.net.Socket;\n" +
                    "import java.io.ObjectInputStream;\n" +
                    "import java.io.ObjectOutputStream;\n";
    try {
      writer.write(imports);
      writer.write("public class " + className + " {\n");
      writer.write("  private static final String ipAddr = \"" + address + "\";\n");
      writer.write("  private static final int port = " + port + ";\n");
      writer.write("  private Socket s;\n" +
                   "  private ObjectOutputStream out;\n" +
                   "  private ObjectInputStream in;\n\n");
      writer.write("  public "+ className +"() {\n");
      writer.write("    try {\n");
      writer.write("      s = new Socket(ipAddr,port);\n" +
                   "      out = new ObjectOutputStream(s.getOutputStream());\n" +
                   "      in = new ObjectInputStream(s.getInputStream());\n");
      writer.write("    } catch (Exception e) { e.printStackTrace(); }\n");
      writer.write("  }\n");
      writer.write("\n");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void writeStubFunction(BufferedWriter writer, String privacy, String returnType, String name, String[][] params) {
    try {
      writer.write("  " + privacy + " " + returnType + " " + name + " (");
      int i = 0;
      while (i<params.length) {
        writer.write(params[i][0] + " " + params[i][1]);
        i++;
        if (i != params.length) writer.write(",");
      }
      writer.write(") {\n");
      writer.write("    Object[] arguments = new Object[]{");
      i = 0;
      while (i<params.length) {
        writer.write(params[i][1]);
        i++;
        if (i != params.length) writer.write(",");
      }
      writer.write("};\n");
      writer.write("    Requete r = new Requete(\""+ name +"\", arguments);\n" +
                   "    Object result = null;\n");
      writer.write("    try {\n");
      writer.write("      out.writeObject(r);\n" +
                   "      result = in.readObject();\n");
      writer.write("    } catch (Exception e) { e.printStackTrace(); }");
      writer.write("    return (" + returnType + ") result;\n");
      writer.write("  }\n\n");
      
    } catch (Exception e){
      e.printStackTrace();
    }
  }
}