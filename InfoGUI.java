import javax.swing.*;

/**
 * This class contains the GUI for the Icons used in the simulation
 * @author Sam Thomas (K21065562), Raj Mohammad (K20126462)
 * @version 2023.02.28
 */
public class InfoGUI {
    JFrame frame = new JFrame();
    JLabel image = new JLabel();
    
    /**
     * Creates a window for the GUI
     */
    public InfoGUI(){
        frame.add(new JLabel(new ImageIcon((getClass()).getResource("infoGUI.png"))));
        frame.setSize(410,400);
        frame.setVisible(true);

    }
}
