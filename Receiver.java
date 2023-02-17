import java.net.*;
public class Receiver extends Thread {
    private DatagramSocket socket;
    private LargeLabel largeLabel;
    private DatagramPacket sender;
    public DatagramPacket getSender() { return sender; }
    public Receiver(DatagramSocket socket, LargeLabel largeLabel) {
        this.socket = socket;
        this.largeLabel = largeLabel;
    }
    public void run() {
        while (true) {
            try {
                byte[] buffer = new byte[512];
                sender = new DatagramPacket(buffer, buffer.length);
                socket.receive(sender);
                largeLabel.print(new String(buffer, 0, buffer.length));
                System.out.println(new String(buffer, 0, buffer.length));
            } catch (Exception e) {

            }
        }
    }
}