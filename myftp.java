import java.util.Arrays;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.net.*;

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
                input = input.trim().toLowerCase();
                command = input.substring(0,input.contains(" ") ? input.indexOf(" ") : input.length());
                boolean contains = Arrays.stream(commands).anyMatch(command::equals);
                if (contains) {out.writeUTF(input);}
                else {System.out.println("Command not recognized, try again");}
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
