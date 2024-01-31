import java.net.*;
import java.io.*;

public class myftpserver {

    public static void main(String[] args) {
        String port = args[0];
        try {
            ServerSocket serverSock = new ServerSocket(Integer.parseInt(port));
            System.out.println("Waiting for client...");
            Socket clientSock = serverSock.accept();
            System.out.println("Client accepted");
            PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
            DataInputStream in = new DataInputStream(new BufferedInputStream(clientSock.getInputStream()));
            String inputLine, outputLine;
            while (!((inputLine = in.readUTF()).equals("quit"))) {
                switch (inputLine.substring(0, inputLine.contains(" ") ? inputLine.indexOf(" ") : inputLine.length())) {
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
                System.out.println(inputLine);
            }
            serverSock.close();
            clientSock.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

}