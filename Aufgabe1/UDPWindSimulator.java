package aufgabe1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.json.simple.JSONObject;

/**
 *
 * @author debian
 */
public class UDPWindSimulator {

    private int test = 0;
    
    private String host;
    private int port;
    private int sleepTime;
    private DatagramSocket socket;
    private byte data[];
    private InetAddress address;
    private DatagramPacket packet;
    private boolean run;
    

    public static void main(String[] args) {
        try {
            UDPWindSimulator windSimulator = new UDPWindSimulator();
            windSimulator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UDPWindSimulator() throws Exception {
        this.host = "localhost";
        this.port = 9997;
        this.sleepTime = 10;
        this.socket = new DatagramSocket();
        this.data = new byte[256];
        this.address = InetAddress.getByName(this.host);
        this.run = true;
    }

//    public UDPWindSimulator(int sleepTime) {
//        this.sleepTime = sleepTime;
//    }

    public void run() throws Exception {
        while(this.run) {
            this.sendPacket();
            Thread.sleep(sleepTime);
        }
    }

    public void runThread() throws Exception {
        final UDPWindSimulator sim = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sim.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int simulateValues() throws Exception {
        test++;
        return test;
    }

    public void buildPacketBody() throws Exception {
        JSONObject json = new JSONObject();
        json.put("type", "wind");
        json.put("windspeed", simulateValues());
        json.put("timestamp", System.currentTimeMillis());
        System.out.println(json);
        this.data = json.toString().getBytes();
    }

    public void sendPacket() throws Exception {
        this.buildPacketBody();
        this.packet = new DatagramPacket(this.data, this.data.length, this.address, this.port);
        this.socket.send(this.packet);
    }
}
