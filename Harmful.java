
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * The Harmful cell within the simulator, it's role is to heal the spread disease to Mycoplasma. It has a probability
 * of threat that increases and decreases overtime during the simulation.
 *
 * @author Sam Thomas (K21065562), Raj Mohammad (K20126462)
 * @version 2022.01.06 (1)
 */
public class Harmful extends Cell {

    // The probability that the Harmful cell spawns
    private static final double ALIVE_PROB = 0.02;

    // The age of the cell
    private int age;

    // The life expectancy of the cell
    private int lifeExpectancy;

    /**
     * Creates a Harmfil cell at a location in the field of the simulation
     * 
     * @param field The field of the simulation
     * @param location The location in the field
     */
    public Harmful(Field field, Location location) {
        super(field, location, Colours.HARMFUL.getColor());
        setDiseased(true);
        age = 0;

        //The life expectancy is set to a random number between 50 and 100
        Random rand = Randomizer.getRandom();
        lifeExpectancy = rand.nextInt(50) + 50;
    }

    /**
     * This is how the Harmful decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        if (isAlive()) {
            if (age <= lifeExpectancy || neighbours.size() > 1) {
                setNextState(true);
            }
        } else {
            if (neighbours.size() > 2) {
                setNextState(true);
                age = 0;
            }
        }
        age++;
    }

    /**
     * Returns the probability of the Harmful cell being alive.
     * @return the probability of the Harmful cell being alive.
     */
    public static double getAliveProbability() {
        return ALIVE_PROB;
    }
}
