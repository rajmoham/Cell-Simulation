
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author Sam Thomas (K21065562), Raj Mohammad (K20126462), David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public class Simulator {
    // Boolean to track if the simulation is paused
    private boolean paused = false;

    // The delay in milliseconds for each simulation
    private static int delay = 50;

    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;

    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // List of cells in the field.
    private List<Cell> cells;

    // The current state of the field.
    private Field field;

    // The current generation of the simulation.
    private int generation;

    // A graphical view of the simulation.
    private SimulatorView view;

    /**
     * Execute simulation
     */
    public static void main(String[] args) {
        Simulator sim = new Simulator();
    }

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        cells = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width, this);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 generations).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of
     * generations.  Stop before the given number of generations if the
     * simulation ceases to be viable. Can be paused to stop cell action
     * and generation progressing.
     *
     * @param numGenerations The number of generations to run for.
     */
    public void simulate(int numGenerations) {
        generation = 1;
        while(generation < numGenerations && view.isViable(field)) {
            delay = view.getDelay();
            view.updateProgressBar();

            if(!paused) {
                simOneGeneration();
                delay(delay);
                
            }
        }
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            cell.updateThreatProbability(generation);
            cell.act();
        }

        for (Cell cell : cells) {
            cell.updateState();
        }
        view.showStatus(generation, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(generation, field);
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);

                if (rand.nextDouble() <= Harmful.getAliveProbability()) {
                    Harmful harmful = new Harmful(field, location);
                    cells.add(harmful);

                } else if (rand.nextDouble() <= Protector.getAliveProbability()) {
                    Protector protector = new Protector(field, location);
                    protector.setDead();
                    cells.add(protector);

                }
                else if (rand.nextDouble() <= Healer.getAliveProbability()) {
                    Healer healer = new Healer(field, location);
                    cells.add(healer);

                }
                else {
                    Mycoplasma myco = new Mycoplasma(field, location);
                    if (rand.nextDouble() > Mycoplasma.getAliveProbability()) {
                        myco.setDead();
                    }
                    cells.add(myco);
                }
            }
        }
    }


    /**
     * Pause for a given time.
     *
     * @param millisec The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }

    /**
     * Sets the boolean paused to the parameter paused, this stops it incrementing the generation count and updating cell behaviour in the simulate method.
     * @param paused
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Returns the pause state of the simulation.
     * @return paused
     */
    public boolean getPaused() {
        return paused;
    }
}
