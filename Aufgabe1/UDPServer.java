package aufgabe1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author debian
 */
public class UDPServer {

    private boolean run;
    private byte data[];
    private DatagramPacket packet;
    private DatagramSocket socket;
    private int port;
    private String msg;
    private JSONParser jsonParser;
    private List windList;
    private List powerList;
    private int bufferSize;

    public static void main(String[] args) {
        try {
            UDPServer udpServer = new UDPServer();
            udpServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UDPServer() throws Exception {
        this.run = true;
        this.data = new byte[256];
        this.port = 9997;
        this.socket = new DatagramSocket(this.port);
        this.jsonParser = new JSONParser();
        this.windList = new LinkedList();
        this.powerList = new LinkedList();
        this.bufferSize = 10;
    }

    public void run() throws Exception {
        while (this.run) {
            packet = new DatagramPacket(data.clone(), data.length);
            socket.receive(packet);
            msg = new String(packet.getData()).replaceAll("