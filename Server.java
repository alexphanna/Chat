import java.net.*;
import java.awt.*;
import java.awt.event.*;
public class Server {
    private Frame frame;
    private DatagramSocket socket;
    private LargeLabel largeLabel;
    public Frame getFrame() { return frame; }
    public void setFrame(Frame frame) { this.frame = frame; }
    public static void main(String[] args) throws Exception {
        new Server();
    }
    public Server() throws Exception {
        setFrame(new Frame("Server"));
        getFrame().setResizable(false);

        Panel panel = new Panel(null);
        panel.setPreferredSize(new Dimension(275, 125));

        Label ipLabel = new Label("IP:");
        ipLabel.setBounds(25, 0, 100, 25);
        panel.add(ipLabel);

        TextField ipTextField = new TextField();
        ipTextField.setBounds(25, 25, 100, 25);
        ipTextField.setText(InetAddress.getByName("localhost").toString().substring(10));
        ipTextField.setEnabled(false);
        panel.add(ipTextField);

        Label portLabel = new Label("Port:");
        portLabel.setBounds(150, 0, 100, 25);
        panel.add(portLabel);

        TextField portTextField = new TextField();
        portTextField.setBounds(150, 25, 100, 25);
        panel.add(portTextField);

        Button button = new Button("Start");
        button.setBounds(150, 75, 100, 25);
        button.addActionListener(new StartClicked(ipTextField, portTextField));
        panel.add(button);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    class StartClicked implements ActionListener {
        private TextField address, port;
        public StartClicked(TextField address, TextField port) {
            this.address = address;
            this.port = port;
        }
        public void actionPerformed(ActionEvent e) {
            if (address.getText().length() == 0 || port.getText().length() == 0) return;
            getFrame().removeAll();
            getFrame().setTitle(address.getText());

            Panel panel = new Panel(null);
            panel.setPreferredSize(new Dimension(275, 375));

            largeLabel = new LargeLabel(225, 275);
            largeLabel.setLocation(25, 25);
            panel.add(largeLabel);

            TextField textField = new TextField();
            textField.setBounds(25, 325, 150, 25);
            panel.add(textField);

            Button button = new Button("Send");
            button.setBounds(200, 325, 50, 25);
            panel.add(button);

            getFrame().add(panel);
            getFrame().pack();

            byte[] buffer = new byte[512];
            DatagramPacket sender = new DatagramPacket(buffer, buffer.length);
            try {
                socket = new DatagramSocket(Integer.parseInt(port.getText()));
                socket.receive(sender);
            } catch (Exception f) {
                
            }
            largeLabel.print(new String(buffer, 0, buffer.length));

            button.addActionListener(new TextFieldEntered(textField, largeLabel, sender.getSocketAddress()));
            textField.addActionListener(new TextFieldEntered(textField, largeLabel, sender.getSocketAddress()));
            Receiver receiver = new Receiver(socket, largeLabel);
            receiver.start();
        }
        class TextFieldEntered implements ActionListener {
            private LargeLabel largeLabel;
            private TextField textField;
            private SocketAddress address;
            public TextFieldEntered(TextField textField, LargeLabel largeLabel, SocketAddress address) {
                this.largeLabel = largeLabel;
                this.textField = textField;
                this.address = address;
            }
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().length() == 0) return;
                largeLabel.print(textField.getText());
                try {
                    byte[] buffer = textField.getText().getBytes();
                    DatagramPacket message = new DatagramPacket(buffer, buffer.length, address);
                    socket.send(message);
                    
                } catch (Exception f) {
                    
                }
                textField.setText(null);
            }
        }
    }
    
}
