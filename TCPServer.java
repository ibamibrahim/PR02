import java.io.*;
import java.net.*;

class TCPServer {
    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket clientSocket;

        while (true) {
            System.out.println("Waiting for connection....");
            clientSocket = welcomeSocket.accept();

            String ipAddress = clientSocket.getInetAddress().toString().substring(1);
            int port = clientSocket.getPort();

            System.out.printf("New client conected from ip %s port %d %n", ipAddress, port);
            
            BufferedReader in =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            
            clientSentence = in.readLine();

            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            out.writeBytes(capitalizedSentence);
        
        }
    }
}