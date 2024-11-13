import java.net.*;
import java.util.Scanner;

public class UDPServer {
    private DatagramSocket socket;
    private InetAddress clientAddress;
    private int clientPort;

    public UDPServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("UDP Server started on port " + port);
    }

    public void start() {
        byte[] buffer = new byte[1024];

        // Thread to receive messages from the client
        new Thread(() -> {
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    clientAddress = packet.getAddress();
                    clientPort = packet.getPort();
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Received from client: " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Thread to send messages to the client
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter message to client: ");
            String message = scanner.nextLine();
            if (clientAddress != null && clientPort != 0) {
                DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), clientAddress, clientPort);
                try {
                    socket.send(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No client connected yet.");
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        UDPServer server = new UDPServer(12346);
        server.start();
    }
}
