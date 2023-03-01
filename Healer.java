/**
 * The Healer cell within the simulator, it's role is to heal the surrounding
 * area of disease,
 * it has a max number of heals before it dies. Once dead it can come back to
 * life if there
 * are enough diseased neighbours in its surrounding area.
 *
 * @author Sam Thomas (K21065562), Raj Mohammad (K20126462)
 * @version 2023.02.28
 */

public class Healer extends Cell {
    // The maxiumum number of heals the cell can make in a lifetime
    private static final int maxHeals = 10;

    // The number of diseeased cells that would bring the healer cell back to life
    private static final int minDiseasedCellNeededToRevive = 2;

    // The probability the cell will spawn
    private static final double ALIVE_PROB = 0.02;

    // The number of heals the cell has made in a lifetime
    private int cellHeals = 0;

    /**
     * Creates a Healer cell in the field at a certain location
     * 
     * @param field    the field of the simulation
     * @param location the location in the field
     */
    public Healer(Field field, Location location) {
        super(field, location, Colours.HEALER.getColor());
    }

    /**
     * This decides how the healer cell is alive or not, as well the conditions that
     * bring it back to life
     */
    public void act() {
        if (isAlive()) {
            setNextState(true);
            cellHeals += getHarmfulNeighbors().size();
            if (cellHeals >= maxHeals) {
                setNextState(false);
            }
        } else {
            setNextState(false);
            if (getHarmfulNeighbors().size() >= minDiseasedCellNeededToRevive) {
                setNextState(true);
                cellHeals = 0;
            }
        }
    }

    /**
     * Returns the probability of the Healer cell being alive.
     * 
     * @return the probability of the Healer cell being alive.
     */
    public static double getAliveProbability() {
        return ALIVE_PROB;
    }
}
