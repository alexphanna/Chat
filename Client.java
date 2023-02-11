import java.net.*;
import java.util.Scanner;
public class Client {
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter port: ");
        int port = scanner.nextInt();
        InetAddress address = InetAddress.getByName("localhost");
        DatagramSocket socket = new DatagramSocket();
        while (true) {
            byte[] buffer = scanner.nextLine().getBytes();
            DatagramPacket message = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(message);

            buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            System.out.println(response.getSocketAddress() + ": " + new String(buffer, 0, buffer.length));
        }
    }
}
