package org.sim;

import processing.core.PVector;

import java.util.Random;
/**
 * Static utility class providing convenient helper methods for random number generation
 * and random vector positioning.
 */
public class RND {
    private static final Random RANDOM = new Random(Consts.RANDOM_SEED);

    // increaseTrue + decreaseTrue should be in the range -10 to 10
    /**
     * Returns true or false based on a customized probability scale.
     */
    public static boolean Chance(float increaseTrue, float decreaseTrue)
    {
        return RANDOM.nextFloat() < 0.5f + ((increaseTrue + decreaseTrue)/20);
    }
    /**
     * Scales a raw number down to a proportional value based on a maximum limit.
     */
    public static float Scale(float value, float maxValue)
    {
        return value / maxValue;
    }
    /**
     * Scales a raw number and applies an extra weight multiplier factor.
     */
    public static float Scale(float value, float maxValue, float modifier)
    {
        return Scale(value, maxValue) * modifier;
    }
    /**
     * Generates a random 2D vector coordinate within specified boundary box coordinates.
     */
    public static PVector RandomVector2(float minX, float maxX, float minY, float maxY)
    {
        float startX = Math.min(minX, maxX);
        float endX = Math.max(minX, maxX);
        float startY = Math.min(minY, maxY);
        float endY = Math.max(minY, maxY);

        float x = startX + RANDOM.nextFloat() * (endX - startX);
        float y = startY + RANDOM.nextFloat() * (endY - startY);

        return new PVector(x, y);
    }
    /**
     * Generates a random decimal float number between a minimum and a maximum value range.
     */
    public static float RandomFloat(float min, float max)
    {
        return min + RANDOM.nextFloat() * (max - min);
    }
    /**
     * Generates a completely random boolean flag (true or false).
     */
    public static boolean RandomBoolean()
    {
        return RANDOM.nextBoolean();
    }

    /*
    Chance function probability table
    -10 -> 0%
    -9 -> 5%
    -8 -> 10%
    -7 -> 15%
    -6 -> 20%
    -5 -> 25%
    -4 -> 30%
    -3 -> 35%
    -2 -> 40%
    -1 -> 45%
    0 -> 50%
    1 -> 55%
    2 -> 60%
    3 -> 65%
    4 -> 70%
    5 -> 75%
    6 -> 80%
    7 -> 85%
    8 -> 90%
    9 -> 95%
    10 -> 100%
     */
}
