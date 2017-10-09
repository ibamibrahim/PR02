import java.io.*;
import java.net.*;

class TCPServer {
    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            System.out.println("Waiting for connection....");
            Socket connectionSocket = welcomeSocket.accept();


            String ipAddress = connectionSocket.getInetAddress().toString().substring(1);
            int port = connectionSocket.getPort();

            System.out.printf("New client conected from ip %s port %d %n", ipAddress, port);
            
            BufferedReader inFromClient =
                new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            System.out.println("sampe sini_1");

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            System.out.println("sampe sini_2");
            
            clientSentence = inFromClient.readLine();
            System.out.println("sampe sini_3");

            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
        }
    }
}