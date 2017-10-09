import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.Random;
import java.util.Scanner;

class TCPServer {

	public static void main(String argv[]) throws Exception {
		String query;
		String capitalizedSentence;
		HashMap<String, String> clientData = new HashMap<>();

		/** Scan the port */
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the port to listen: ");
		int usedPort = sc.nextInt();
		
		while (true) {
			ServerSocket welcomeSocket = new ServerSocket(usedPort);
			while (true) {

				System.out.println("\n Waiting for connection....");

				final Socket clientSocket = welcomeSocket.accept();

				String ipAddress = clientSocket.getInetAddress().toString().substring(1);
				int port = clientSocket.getPort();
				System.out.printf("New client conected from IP %s port %d %n", ipAddress, port);

				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

				query = in.readLine();
				System.out.println(">> USER_QUERY: " + query);
				/* check if query is login or msg */
				String[] temp = query.split(" ");

				if (temp[0].equals("LOGIN")) {
					String clientName = temp[1];
					String sessionID = randomStr();
					clientData.put(sessionID, clientName);

					String result = "NEW_SESSION " + sessionID;
					out.writeBytes(result);
					System.out.println("Client has close the connection");
				} else if (temp[0].equals("MSG")) {
					String sessionID = temp[1];
					String message = temp[2];

					if (clientData.get(sessionID) != null) {
						out.writeBytes("JAWABAN " + clientData.get(sessionID) + " " + message);

					} else {
						// session ID is not found
						out.writeBytes("JAWABAN Anda belum login");
					}

					// close socket and stop the loop
					new Thread() {
						public void run() {
							System.out.println("Closing socket...");
							try {
								welcomeSocket.close();
								clientSocket.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}.run();

					break;
				}
			}
		}
	}

	private static String randomStr() {
		return UUID.randomUUID().toString().substring(0, 4);
	}

}