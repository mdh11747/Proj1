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
            String inputLine, inputArg, outputLine, command;
            command = "";
            while (!(command.equals("quit"))) {
                inputLine = in.readUTF();
                System.out.println(inputLine);
                command = inputLine.substring(0, inputLine.contains(" ") ? inputLine.indexOf(" ") : inputLine.length());
                inputArg = getFileFromArg(inputLine.substring(inputLine.contains(" ") ? inputLine.indexOf(" ") + 1 : inputLine.length()));
                String fileName = inputLine.substring(inputLine.contains(" ") ? inputLine.indexOf(" ") + 1 : inputLine.length());
                System.out.println(fileName);
                switch (command) {
                    case ("get"):
                        System.out.println("get command recognized");
                        getFile(fileName, clientSock);
                        break;
                    case ("put"):
                        System.out.println("put command recognized");
                        putFile(inputArg, in);
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
            serverSock.close();
            clientSock.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    public static void getFile(String fileName, Socket sock) {
        System.out.println("hi" + fileName);
        try {
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            File serverFile = new File("./" + fileName);
            byte[] serverFileBytes = new byte[(int) serverFile.length()];
            FileInputStream fis = new FileInputStream(serverFile);
            fis.read(serverFileBytes);
            out.writeUTF(fileName);
            out.write(serverFileBytes, 0, serverFileBytes.length);
            System.out.println("Succesfully sent file to client");
        } catch (Exception e) {
            System.out.println("Error transferring file to client");
        }
    }

    public static void putFile(String fileName, DataInputStream in) {
        byte[] bytes = new byte[10000];
        try {
            int fileLength = in.read(bytes);
            byte[] temp = new byte[fileLength];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = bytes[i];
            }
            FileOutputStream fos = new FileOutputStream("./serverFiles/" + fileName);
            fos.write(temp);
            fos.close();
        } catch (Exception e) {
            System.out.println("Exception was reached: " + e);
        }
    }

    public static String getFileFromArg(String arg) {
        return arg.substring(arg.indexOf("/") + 1);
    }

}