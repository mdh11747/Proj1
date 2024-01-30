import java.util.Scanner;
import java.net.Socket;

public class myftp {

    private static String input;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String sysName = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            Socket sock = new Socket(sysName,port);

            while (true) {
                System.out.print("mytftp>");
                input = scan.nextLine();
                input = input.trim().toLowerCase();
                switch (input.substring(0, input.contains(" ") ? input.indexOf(" ") : input.length())) {
                    case ("get"):
                        System.out.println("get command recognized");
                        break;
                    case ("put"):
                        System.out.println("put command recognized");
                        break;
                    case ("delete"):
                        System.out.println("delete command recognized");
                        break;
                    case ("ls"):
                        System.out.println("ls command recognized");
                        break;
                    case ("cd"):
                        System.out.println("cd command recognized");
                        break;
                    case ("mkdir"):
                        System.out.println("mkdir command recognized");
                        break;
                    case ("pwd"):
                        System.out.println("pwd command recognized");
                        break;
                    case ("quit"):
                        System.out.println("quit command recognized");
                        break;
                    default:
                        System.out.println("Command not recognized.");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
