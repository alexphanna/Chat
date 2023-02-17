import java.awt.*;
import java.awt.event.*;
import java.net.*;
public class Client {
    private Frame frame;
    private DatagramSocket socket;
    public Frame getFrame() { return frame; }
    public void setFrame(Frame frame) { this.frame = frame; }
    public static void main(String[] args) throws Exception{
        new Client();
    }
    public Client() throws UnknownHostException {
        setFrame(new Frame("Client"));
        getFrame().setResizable(false);

        Panel panel = new Panel(null);
        panel.setPreferredSize(new Dimension(275, 125));

        Label ipLabel = new Label("IP:");
        ipLabel.setBounds(25, 0, 100, 25);
        panel.add(ipLabel);

        TextField ipTextField = new TextField();
        ipTextField.setBounds(25, 25, 100, 25);
        panel.add(ipTextField);

        Label portLabel = new Label("Port:");
        portLabel.setBounds(150, 0, 100, 25);
        panel.add(portLabel);

        TextField portTextField = new TextField();
        portTextField.setBounds(150, 25, 100, 25);
        panel.add(portTextField);

        Button button = new Button("Connect");
        button.setBounds(150, 75, 100, 25);
        button.addActionListener(new ConnectClicked(ipTextField, portTextField));
        panel.add(button);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    class ConnectClicked implements ActionListener {
        private TextField address, port;
        public ConnectClicked(TextField address, TextField port) throws UnknownHostException {
            this.address = address;
            this.port = port;
        }
        public void actionPerformed(ActionEvent e) {
            if (address.getText().length() == 0 || port.getText().length() == 0) return;

            try {
                socket = new DatagramSocket();
            } catch (Exception f) {
                
            }

            getFrame().removeAll();

            Panel panel = new Panel(null);
            panel.setPreferredSize(new Dimension(275, 375));

            LargeLabel largeLabel = new LargeLabel(225, 275);
            largeLabel.setLocation(25, 25);
            panel.add(largeLabel);
            
            try {
                socket = new DatagramSocket();
            } catch (Exception f) {

            }
            new Receiver(socket, largeLabel).start();

            TextField textField = new TextField();
            textField.setBounds(25, 325, 150, 25);
            textField.addActionListener(new TextFieldEntered(textField, largeLabel));
            panel.add(textField);

            Button button = new Button("Send");
            button.setBounds(200, 325, 50, 25);
            button.addActionListener(new TextFieldEntered(textField, largeLabel));
            panel.add(button);

            getFrame().add(panel);
            getFrame().pack();
        }
        class TextFieldEntered implements ActionListener {
            private LargeLabel largeLabel;
            private TextField textField;
            public TextFieldEntered(TextField textField, LargeLabel largeLabel) {
                this.largeLabel = largeLabel;
                this.textField = textField;
            }
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().length() == 0) return;
                largeLabel.print(textField.getText());
                byte[] buffer = textField.getText().getBytes();
                try {
                    DatagramPacket message = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), Integer.parseInt(port.getText()));
                    socket.send(message);
                } catch (Exception f) {
                    
                }
                textField.setText(null);
            }
        }
    }
}
