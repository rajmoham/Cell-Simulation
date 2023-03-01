import javax.swing.*;

/**
 * This class contains the GUI for the Icons used in the simulation
 * @author Sam Thomas (K21065562), Raj Mohammad (K20126462)
 * @version 2023.02.28
 */
public class IconGUI {
    JFrame frame = new JFrame();
    JLabel image = new JLabel();

    /**
     * Creates a window for the GUI
     */
    public IconGUI(){
        frame.add(new JLabel(new ImageIcon((getClass()).getResource("iconGUI.png"))));
        frame.setSize(400,435);
        frame.setVisible(true);

    }
}
