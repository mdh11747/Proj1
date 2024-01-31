import java.util.Scanner;
import java.io.DataOutputStream;
import java.net.*;

public class myftp {

    private static String input;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String sysName = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            Socket sock = new Socket(sysName,port);
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());

            while (true) {
                System.out.print("mytftp>");
                input = scan.nextLine();
                input = input.trim().toLowerCase();
                out.writeUTF(input);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
