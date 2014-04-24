import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.*;
import java.net.*;

public class SorryApplet extends JApplet {

    public static final String HOME = "http://www.uvm.edu/~jfgould/SorryGame/";
   
    //Called when this applet is loaded into the browser.
    public void init() {
              Image imagOneWay = getImage( getDocumentBase(), "AA.jpg" ) ;
              AppletHandlerPanel content = new AppletHandlerPanel();
                    add(content);
    }
    
    
}