/**
 * Provide a counter for a participant in the simulation.
 * This includes an identifying string and a count of how
 * many participants of this type currently exist within
 * the simulation.
 *
 * @author David J. Barnes, Michael Kölling, Raj Mohammad (K20126462), Sam Thomas (K21065562)
 * @version 2022.02.28
 */

public class Counter {
    // A name for this type of simulation participant
    private String name;

    // How many of this type exist in the simulation.
    private int count;

    // How many of this type that are diseased that exist in the simulation
    private int diseasedCount;

    /**
     * Provide a name for one of the simulation types.
     * @param name  A class of life
     */
    public Counter(String name) {
        this.name = name;
        count = 0;
        diseasedCount = 0;
    }

    /**
     * @return The short description of this type.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The current count for this type.
     */
    public int getCount() {
        return count;
    }

    /**
     * Increment the current count by one.
     */
    public void increment() {
        count++;
    }

    /**
     * Reset the current count to zero.
     */
    public void reset() {
        count = 0;
        diseasedCount = 0;
    }

    /**
     * Increment the current count that is diseased by one.
     */
    public void incrementDiseased() {
        diseasedCount++;
    }

    /**
     * @return The current count that are diseased for this type.
     */
    public int getDiseasedCount() {
        return diseasedCount;
    }
}
