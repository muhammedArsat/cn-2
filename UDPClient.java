import java.net.*;
import java.util.Scanner;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public UDPClient(String host, int port) throws Exception {
        socket = new DatagramSocket();
        address = InetAddress.getByName(host);
        this.port = port;
    }

    public void start() {
        byte[] buffer = new byte[1024];

        // Thread to receive messages from the server
        new Thread(() -> {
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Server says: " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Main thread for sending messages to the server
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter message to server: ");
            String message = scanner.nextLine();
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, port);
            try {
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        UDPClient client = new UDPClient("localhost", 12346);
        client.start();
    }
}
