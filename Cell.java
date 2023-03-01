import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author David J. Barnes, Michael Kölling, Jeffery Raphael, Raj Mohammad (K20126462), Sam Thomas (K21065562)
 * @version 2023.02.28
 */

public abstract class Cell {

    // The number of generations taken for harmful cells to reach peak danger before resetting
    static final double GEN_CYCLE = 100;

    // Whether the cell is alive or not.
    private boolean alive;

    // Whether the cell is diseased or not.
    private boolean diseased;

    // Whether the cell will be alive in the next generation.
    private boolean nextAlive;

    // The cell's field.
    private Field field;

    // The cell's position in the field.
    private Location location;

    // The cell's color
    private Color color;

    // The probability of a cell being in threat of getting diseased.
    private double threatProbability;


    /**
     * Create a new cell at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Cell(Field field, Location location, Color col) {
        alive = true;
        nextAlive = false;
        this.field = field;
        setLocation(location);
        setColor(col);
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the
     * next generation.
     */
    abstract public void act();

    /**
     * Check whether the cell is alive or not.
     *
     * @return true if the cell is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the cell is no longer alive.
     */
    protected void setDead() {
        alive = false;
    }

    /**
     * Indicate that the cell will be alive or dead in the next generation.
     */
    public void setNextState(boolean value) {
        nextAlive = value;
    }

    /**
     * Changes the state of the cell
     */
    public void updateState() {
        alive = nextAlive;
    }

    /**
     * Changes the color of the cell
     */
    public void setColor(Color col) {
        color = col;
    }

    /**
     * Returns the cell's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the diseased status of the cell, updates colour to match it.
     * @param diseased A boolean to set whether the cell is diseased or not.
     */
    public void setDiseased(boolean diseased) {
        this.diseased = diseased;
        if(diseased) {
            setColor(Colours.MYCOPLASMA_DISEASED.getColor());
        }
        else {
            setColor(Colours.MYCOPLASMA.getColor());
        }
    }

    /**
     * Returns the diseased status of a cell
     * @return the diseased status of a cell
     */
    public boolean getDiseased() {
        return diseased;
    }

    /**
     * Return the cell's location.
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /**
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }

    /**
     * A method that filters the list of all neighbours to show just the diseased cells.
     * @return A list of all harmful cells in surrounding area to cells location.
     */
    protected List<Cell> getHarmfulNeighbors() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        return neighbours.stream().filter(cell -> cell.getDiseased()).collect(Collectors.toList());
    }

    /**
     * A method that filters the list of all neighbours to show just the protector cells.
     * @return A list of all protector cells in surrounding area to cells location.
     */
    protected List<Cell> getProtectorNeighbours() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        return neighbours.stream().filter(cell -> cell instanceof Protector && ((Protector) cell).isActive()).collect(Collectors.toList());
    }

    /**
     * A method that filters the list of all neighbours to show just the healer cells.
     * @return A list of all healer cells in surrounding area to cells location.
     */
    protected List<Cell> getHealerCells() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        return neighbours.stream().filter(cell -> cell instanceof Healer).collect(Collectors.toList());
    }

    /**
     * Calculates the danger level of the cells area by comparing the number of harmful cells to protector cells, if there is at least one more harmful than protector there becomes a chance
     * the cell will become diseased. This is worked out by generating a random double value and if it's less than the current threat probability of the harmful cells it becomes diseased.
     */
    protected void harmfulCellCheck() {
        boolean isDanger = getHarmfulNeighbors().size() > getProtectorNeighbours().size();

        if (isDanger) {
            Random rand = Randomizer.getRandom();
            if (rand.nextDouble() < threatProbability) {
                setDiseased(true);
            }

        }
    }

    /**
     * Sets the threatProbability of the harmful cells each generation, the danger level of the harmful cells peaks every certain amount of generations set in the GEN_CYCLE variable.
     * The probability will go from 0.0 to 1.0 over the set number of generations.
     * @param generation, the current generation of the simulation.
     */
    public void updateThreatProbability(int generation) {
        threatProbability = (generation % GEN_CYCLE) * 1/GEN_CYCLE;
    }
}
