import java.awt.*;
import java.awt.event.*;
public class WindowCloser implements WindowListener {
    public void windowActivated(WindowEvent e) {}    
    public void windowClosed(WindowEvent e) {}    
    public void windowClosing(WindowEvent e) {    
        System.exit(0);   
    }    
    public void windowDeactivated(WindowEvent e) {}    
    public void windowDeiconified(WindowEvent e) {}    
    public void windowIconified(WindowEvent e) {}    
    public void windowOpened(WindowEvent arg0) {}    
}
