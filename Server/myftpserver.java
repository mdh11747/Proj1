package Server;

import java.net.*;
import java.util.Arrays;
import java.io.*;

public class myftpserver {

    private static String pwd = "./";
    private static Socket clientSock;
    private static PrintStream ps;

    public static void main(String[] args) {
        String port = args[0];
        try {
            ServerSocket serverSock = new ServerSocket(Integer.parseInt(port));
            System.out.println("Waiting for client...");
            clientSock = serverSock.accept();
            System.out.println("Client accepted");
            ps = new PrintStream(clientSock.getOutputStream());
            DataInputStream in = new DataInputStream(new BufferedInputStream(clientSock.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(clientSock.getOutputStream());
            String inputLine, inputArg, directArg, command;
            command = "";
            while (!(command.equals("quit"))) {
                inputLine = in.readUTF();
                System.out.println(inputLine);
                command = inputLine.substring(0, inputLine.contains(" ") ? inputLine.indexOf(" ") : inputLine.length());
                System.out.println(command);
                inputArg = getFileFromArg(
                        inputLine.substring(inputLine.contains(" ") ? inputLine.indexOf(" ") + 1 : inputLine.length()));
                directArg = inputLine
                        .substring(inputLine.contains(" ") ? inputLine.indexOf(" ") + 1 : inputLine.length());
                String fileName = inputLine
                        .substring(inputLine.contains(" ") ? inputLine.indexOf(" ") + 1 : inputLine.length());
                System.out.println(inputArg);
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
                        boolean worked = deleteFile(inputArg);
                        if (worked == true) {
                            outputStream.writeUTF("File successfully deleted");
                        } else {
                            outputStream.writeUTF("Error deleting file");
                        }
                        break;
                    case ("ls"):
                        System.out.println("ls command recognized");
                        File currDirectory = new File(getPwd());
                        File[] files = currDirectory.listFiles();
                        String rtn = "";
                        for (File file : files) {
                            rtn += file.getName();
                            rtn += "\n";
                        }
                        outputStream.writeUTF(rtn);
                        break;
                    case ("cd"):
                        System.out.println("cd command recognized");
                        cd(inputArg);
                        break;
                    case ("mkdir"):
                        System.out.println("mkdir command recognized");
                        mkdir(directArg);
                        break;
                    case ("pwd"):
                        System.out.println("pwd command recognized");
                        pwd();
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
        try {
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            try {
                File serverFile = new File(getPwd() + fileName);
                byte[] serverFileBytes = new byte[(int) serverFile.length()];
                FileInputStream fis = new FileInputStream(serverFile);
                fis.read(serverFileBytes);
                out.writeUTF(fileName);
                out.write(serverFileBytes, 0, serverFileBytes.length);
                System.out.println("Succesfully sent file to client");
            } catch (Exception e) {
                out.writeUTF("ERROR: " + e);
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println("Exception");
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
            FileOutputStream fos = new FileOutputStream(getPwd() + fileName);
            fos.write(temp);
            fos.close();
        } catch (Exception e) {
            System.out.println("Exception was reached: " + e);
        }
    }

    public static String getFileFromArg(String arg) {
        return arg.substring(arg.indexOf("/") + 1);
    }

    private static void cd(String directory) {
        try {
            File file = new File(pwd + directory);
        } catch (Exception e) {
            System.out.print(e);
        }
        if (directory.equals("~")) {
            pwd = "./";
        } else if (directory.substring(0, 2).equals("..")) {
            File file = new File(pwd);
            if (!pwd.equals("./")) {
                pwd = file.getParent() + "/";
            }
        } else if (directory.substring(0, 1).equals(".")) {
            pwd += directory.substring(2);
        } else {
            if (directory.substring(0, 1).equals("/")) {
                directory = directory.substring(1);
            }
            pwd += directory;
            if (!(pwd.charAt(pwd.length() - 1) == '/')) {
                pwd += "/";
            }
        }
    }

    private static void mkdir(String directory) {
        try {
            String[] forbidden = { "/", "\\", ":", "!", "*", "\"", "<", ">", "?" };
            if (Arrays.stream(forbidden).anyMatch(directory::contains)) {
                System.out.println("Folder name not accepted");
                ps.println("Folder name not accepted, please try again");
            } else {
                File folder = new File(pwd + directory);
                System.out.println(pwd + directory);
                folder.mkdirs();
                byte[] serverFileBytes = new byte[(int) folder.length()];
                clientSock.getOutputStream().write(serverFileBytes, 0, serverFileBytes.length);
                ps.println(directory + " successfully created");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String getPwd() {
        return pwd;
    }

    private static void pwd() {
        System.out.println(pwd);
        ps.println(pwd);
    }

    public static boolean deleteFile(String fileName) {
        File fileToDelete = new File(getPwd() + fileName);
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Unable to delete the file");
                return false;
            }
        } else {
            System.out.println("File does not exist");
            return false;
        }
        return true;
    }
}