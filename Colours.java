import java.awt.Color;

/**
 * This documents the various colours (in RGB format) for cells as well as different 
 * states for certain cells.
 * 
 * @author Raj Mohammad (K20126462), Sam Thomas (K21065562)
 * @version 2023.02.28
 */

public enum Colours {
    MYCOPLASMA(24, 88, 171),
    MYCOPLASMA_DISEASED(144, 33, 33),
    HARMFUL(255, 18, 0),
    PROTECTOR_ACTIVE(24, 180, 13),
    PROTECTOR_INACTIVE(24, 80, 13),
    HEALER(220, 209, 31),
    BACKGROUND_COLOUR(12, 2, 31);

    // Field containing the colour for an instance
    private Color color;
    
    /**
     * Creates a new Colours enum with the RGB values being stored in the color field.
     * @param r red value
     * @param g green value
     * @param b blue value
     */
    private Colours(final int r, final int g, final int b) {
        color = new Color(r, g, b);
    }

    /**
     * Returns the colour of the instance
     * @return the colour of the instance
     */
    public Color getColor() {
        return color;
    }
}