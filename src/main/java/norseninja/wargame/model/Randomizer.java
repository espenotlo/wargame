package norseninja.wargame.model;

import java.util.Random;

public class Randomizer
{
    // The default seed for control of randomization.
    private static final int SEED = 1111;
    // A shared Random object, if required.
    private static final Random rand = new Random(SEED);
    // Determine whether a shared random generator is to be provided.
    private static final boolean USE_SHARED = false;

    /**
     * Constructor for objects of class Randomizer
     */
    private Randomizer() {
        //intentionally empty
    }

    /**
     * Provide a random generator.
     * @return A random object.
     */
    public static Random getRandom()
    {
        if(USE_SHARED) {
            return rand;
        }
        else {
            return new Random();
        }
    }

    /**
     * Reset the randomization.
     * This will have no effect if randomization is not through a shared Random generator.
     */
    public static void reset()
    {
        if(USE_SHARED) {
            rand.setSeed(SEED);
        }
    }
}