package aufgabe1;

// 22. 10. 10
/**
 *
 * @author Peter Altenberd (Translated into English by Ronald Moore) Computer Science Dept. Fachbereich Informatik Darmstadt Univ. of Applied Sciences Hochschule Darmstadt
 */
import java.io.*;
import java.net.*;

public class TCPServer {

    private String line;
    private BufferedReader fromClient;
    private DataOutputStream toClient;
    private int sleepTime;
    //private ;

    public static void main(String[] args) throws Exception {
        try {
            TCPServer server = new TCPServer(1000);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TCPServer(int sleepTime) {

    }

    public void start() throws Exception {

        ServerSocket listenSocket = new ServerSocket(9999);
        while (true) {                            // Handle connection request
            Socket client = listenSocket.accept(); // creat communication socket
            System.out.println("Connection with: " + client.getRemoteSocketAddress());
            handleRequests(client);
        }
    }

    public void handleRequests(Socket s) {
        try {
            fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));// Datastream FROM Client
            toClient = new DataOutputStream(s.getOutputStream());                 // Datastream TO Client
            while (receiveRequest()) {              // As long as connection exists
                sendResponse();
            }
            fromClient.close();
            toClient.close();
            s.close();
            System.out.println("Session ended, Server remains active");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean receiveRequest() throws IOException {
        boolean holdTheLine = true;
        System.out.println("Received: " + (line = fromClient.readLine()));
        if (line.equals(".")) {                         // End of session?
            holdTheLine = false;
        }
        return holdTheLine;
    }

    public void sendResponse() throws IOException {
        toClient.writeBytes(line.toUpperCase() + '\n');  // Send answer
    }
}
