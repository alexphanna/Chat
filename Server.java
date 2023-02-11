import java.net.*;
import java.util.Scanner;
public class Server {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter port: ");
        int port = scanner.nextInt();
        DatagramSocket socket = new DatagramSocket(port);
        while (true) {
            byte[] buffer = new byte[512];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);
            System.out.println(request.getSocketAddress() + ": " + new String(buffer, 0, buffer.length));
            
            buffer = scanner.nextLine().getBytes();
            DatagramPacket response = new DatagramPacket(buffer, buffer.length, request.getSocketAddress());
            socket.send(response);
        }
    }
}
