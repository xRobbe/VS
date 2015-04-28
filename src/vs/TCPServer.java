package vs;

// 22. 10. 10
/**
 *
 * @author Peter Altenberd (Translated into English by Ronald Moore) Computer Science Dept. Fachbereich Informatik Darmstadt Univ. of Applied Sciences Hochschule Darmstadt
 */
import java.io.*;
import java.net.*;

public class TCPServer {

    static int sleepTime = 5000;
    static String line;
    static BufferedReader fromClient;
    static DataOutputStream toClient;
    static WindSimulator windSim;
    static PowerSimulator powerSim;
    static boolean runServer = true;

    public static void main(String[] args) throws Exception {
        try {
            ServerSocket contactSocket = new ServerSocket(9999);
            windSim = new WindSimulator(sleepTime);
            powerSim = new PowerSimulator(sleepTime);
            windSim.startThread();
            powerSim.startThread();
            while (runServer) {                            // Handle connection request
                Socket client = contactSocket.accept(); // creat communication socket
                System.out.println("Connection with: " + client.getRemoteSocketAddress());
                handleRequests(client);
            }
            windSim.stopSimulator();
            powerSim.stopSimulator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void handleRequests(Socket s) {
        try {
            fromClient = new BufferedReader( // Datastream FROM Client
                    new InputStreamReader(s.getInputStream()));
            toClient = new DataOutputStream(
                    s.getOutputStream());                 // Datastream TO Client
            if (receiveRequest()) {              // As long as connection exists
                sendResponse();
            } else {
                sendResponseStop();
                runServer = false;
            }
            line = fromClient.readLine();
            /*while (line.length() >= 5) {
                System.out.println("Request: " + line);
                line = fromClient.readLine();
            }*/
            //System.out.println("Request: " + line);
            fromClient.close();
            toClient.close();
            s.close();
            if (runServer) {
                System.out.println("Session ended, Server remains active");
            } else {
                System.out.println("Server stopped");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    static boolean receiveRequest() throws IOException {
        boolean holdTheLine = true;
        System.out.println("Received: " + (line = fromClient.readLine()));
        if (line.split(" ")[1].equals("/stop")) {                         // End of session?
            System.out.println(line.split(" ")[1]);
            holdTheLine = false;
        }
        line = fromClient.readLine();
        while (!line.equals("")) {
            System.out.println("Request: " + line);
            line = fromClient.readLine();
        }
        System.out.println("Request: " + line);
        return holdTheLine;
    }

    static void sendResponse() throws IOException {
        System.out.println("Hier wird geantwortet");
        String htmlString = "<!DOCTYPE html><html><head><title>Simulator</title></head><body><h1>Wind Speed</h1><p>" + windSim.getWind() + "</p><h1>Power Consumtion</h1><p>" + powerSim.getConsumption() + "</p></body></html>";
        int numOfBytes = htmlString.length();
        toClient.writeBytes("HTTP/1.0 200 Document Follows\r\n");  // Send answer 
        toClient.writeBytes("Content-Length: " + numOfBytes + "\r\n");
        toClient.writeBytes("\r\n");
        toClient.write(htmlString.getBytes(), 0, numOfBytes);  // Send answer
    }

    static void sendResponseStop() throws IOException {
        String htmlString = "<!DOCTYPE html><html><head><title>Simulator</title></head><body><h1>Server ist gestoppt</h1><p>";
        int numOfBytes = htmlString.length();
        toClient.writeBytes("HTTP/1.0 200 Document Follows\r\n");  // Send answer 
        toClient.writeBytes("Content-Length: " + numOfBytes + "\r\n");
        toClient.writeBytes("\r\n");
        toClient.write(htmlString.getBytes(), 0, numOfBytes);  // Send answer
    }
}
