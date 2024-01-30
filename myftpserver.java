import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class myftpserver {

    public static void main(String[] args) {
        String port = args[0];
        try {
            ServerSocket serverSock = new ServerSocket(Integer.parseInt(port));
            Socket clientSock = serverSock.accept();
            PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
            String inputLine, outputLine;
            while (!((inputLine = in.readLine()).equals("quit"))) {
                
            }
        } catch (IOException io) {
            System.out.println(io);
        }
    }

}