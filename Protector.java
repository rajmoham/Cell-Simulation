import java.util.Random;

/**
 * The protector cell within the simulator, it's role is to protect the surrounding area by slowing and in some cases stopping disease spread. Upon creation, it will be inactive however
 * after reaching a certain age (default 20) it has a 5% chance to become active with each passing generation. While alive it has a max lifespan (default 100) and after that it will die. Once
 * dead if a harmful cell enters its surrounding area it will come back to life and the life begins again.
 *
 * @author Sam Thomas (k21065562), Raj Mohammad (k20126462)
 * @version 2023.02.28
 */
public class Protector extends Cell {

    // Whether or not the Protector cell is active (can protect)
    private boolean active;

    // The age of the Protector cell
    private int age;

    // The probability that a Protector spawns
    private static final double ALIVE_PROB = 0.05;

    /**
     * Creates a Protector cell at a location in the field of the simulation
     * @param field the field of the simulation
     * @param location the location in the field
     */
    public Protector(Field field, Location location) {
        super(field, location, Colours.PROTECTOR_INACTIVE.getColor());
        age = 0;
    }

    /**
     * Returns whether the cell is active or not
     * @return whether the cell is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Changes the active state to true or false
     * @param active defines if the cell is active or not
     */
    private void setActive(boolean active) {
        this.active = active;
        updateColor();
    }

    /**
     * Updates the colour of the Protector cell depending on whether it is active
     */
    private void updateColor() {
        if (active) {
            setColor(Colours.PROTECTOR_ACTIVE.getColor());
        } else {
            setColor(Colours.PROTECTOR_INACTIVE.getColor());
        }
    }

    /**
     * This is how the Protector cell decides if it's alive or not
     */
    public void act() {

        if (age > 20) {
            Random rand = Randomizer.getRandom();
            if (rand.nextDouble() < 0.05) {
                setActive(true);
            }
        }

        if (isAlive()) {
            if (age > 100) {
                setNextState(false);
            } else {
                setNextState(true);
            }
        } else {
            if (getHarmfulNeighbors().size() > 0) {
                setNextState(true);
                setActive(false);
                age = 0;
            }
        }
        age++;
    }

    /**
     * Returns the probability of the Protector cell being alive.
     * @return the probability of the Protector cell being alive.
     */
    public static double getAliveProbability() {
        return ALIVE_PROB;
    }
}