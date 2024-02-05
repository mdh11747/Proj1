import java.util.Arrays;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.net.*;
import java.io.*;
import java.nio.file.Files;

public class myftp {

    private static String input;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String sysName = args[0];
        int port = Integer.parseInt(args[1]);
        String[] commands = {"get","put","delete","ls","cd","mkdir","pwd","quit"};

        try {
            Socket sock = new Socket(sysName,port);
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            String command = "";

            while (!command.equals("quit")) {
                System.out.print("mytftp>");
                input = scan.nextLine();
                input = input.trim();
                command = input.substring(0,input.contains(" ") ? input.indexOf(" ") : input.length());
                String inputArg = input.substring(input.contains(" ") ? input.indexOf(" ") + 1 : input.length());
                boolean contains = Arrays.stream(commands).anyMatch(command::equals);
                File clientFile;
                byte[] clientFileBytes;
                if (contains) {
                    out.writeUTF(input);
                    if (command.equals("put")) {
                        try {
                            clientFile = new File("./" + inputArg);
                            clientFileBytes = new byte[(int) clientFile.length()];
                            FileInputStream fis = new FileInputStream(clientFile);
                            fis.read(clientFileBytes);
                            out.write(clientFileBytes, 0, clientFileBytes.length);
                            System.out.println("File transferred to server successfully");
                        } catch (Exception e) {
                            System.out.println("There was an error transferring the file");
                        }
                    } 
                }
                else {System.out.println("Command not recognized, try again");}
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getFileFromArg(String arg) {
        return arg.substring(arg.indexOf("/") + 1);
    }

}
