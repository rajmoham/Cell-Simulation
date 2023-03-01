import java.util.List;
import java.util.Random;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author Sam Thomas (K21065562), Raj Mohammad (K20126462), David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2023.02.28
 */

public class Mycoplasma extends Cell {

    // The probability that Mycoplasma spawns
    private static final double ALIVE_PROB = 0.1;

    /**
     * Create a new Mycoplasma.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Field field, Location location) {
        super(field, location, Colours.MYCOPLASMA.getColor());
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        harmfulCellCheck();
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        if (isAlive()) {
            if (getDiseased()) {
                if (neighbours.size() == 2) {
                    setNextState(true);
                }
                if (getHealerCells().size() > 0) {
                    setDiseased(false);
                }
            } else {
                if (neighbours.size() == 2 || neighbours.size() == 3) {
                    setNextState(true);
                }
            }
        } else {
            if (neighbours.size() == 3) {
                if (getDiseased()) {
                    Random rand = Randomizer.getRandom();
                    setDiseased(rand.nextBoolean());
                }
                setNextState(true);
            }
        }

    }

    /**
     * Returns the probability of Mycoplasma spawning
     * @return the probability of Mycoplasma spawning
     */
    public static double getAliveProbability() {
        return ALIVE_PROB;
    }
}
