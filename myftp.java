import java.util.Arrays;
import java.util.Scanner;
import java.net.*;
import java.io.*;
import java.nio.file.Files;

public class myftp {

    private static String input;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String sysName = args[0];
        int port = Integer.parseInt(args[1]);
        String[] commands = { "get", "put", "delete", "ls", "cd", "mkdir", "pwd", "quit" };

        try {
            Socket sock = new Socket(sysName, port);
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());
            String command = "";

            while (!command.equals("quit")) {
                System.out.print("mytftp>");
                input = scan.nextLine();
                input = input.trim().toLowerCase();
                command = input.substring(0, input.contains(" ") ? input.indexOf(" ") : input.length());
                String inputArg = input.substring(input.contains(" ") ? input.indexOf(" ") + 1 : input.length());
                boolean contains = Arrays.stream(commands).anyMatch(command::equals);
                File clientFile;
                byte[] clientFileBytes;
                if (contains) {
                    out.writeUTF(input);
                    if (command.equals("put")) {
                        try {
                            clientFile = new File("./clientFiles/" + getFileFromArg(inputArg));
                            clientFileBytes = new byte[(int) clientFile.length()];
                            FileInputStream fis = new FileInputStream(clientFile);
                            fis.read(clientFileBytes);
                            out.write(clientFileBytes, 0, clientFileBytes.length);
                            System.out.println("File transferred to server successfully");
                        } catch (Exception e) {
                            System.out.println("There was an error transferring the file");
                        }
                    }
                    if (command.equals("delete")) {
                        try {
                            out.writeUTF(inputArg);
                            System.out.println("The delete command transferred to server successfully");
                            System.out.println(in.readUTF());
                        } catch (Exception e) {
                            System.out.println("There was an error deleting the file");
                        }
                    }
                    if (command.equals("ls")) {
                        try {
                            out.writeUTF(input);
                            String fileList = in.readUTF();
                            System.out.println(fileList);
                        } catch (Exception e) {
                            System.out.println("There was an error listing the files");
                        }
                    }

                } else {
                    System.out.println("Command not recognized, try again");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getFileFromArg(String arg) {
        return arg.substring(arg.indexOf("/") + 1);
    }
}
