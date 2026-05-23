package org.sim;

import processing.core.PVector;

import java.util.Random;

public class RND {
    private static final Random RANDOM = new Random(Consts.RANDOM_SEED);

    // increaseTrue + decreaseTrue should be in the range -10 to 10
    public static boolean Chance(float increaseTrue, float decreaseTrue)
    {
        return RANDOM.nextFloat() < 0.5f + ((increaseTrue + decreaseTrue)/20);
    }

    public static float Scale(float value, float maxValue)
    {
        return value / maxValue;
    }

    public static float Scale(float value, float maxValue, float modifier)
    {
        return Scale(value, maxValue) * modifier;
    }

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

    public static float RandomFloat(float min, float max)
    {
        return min + RANDOM.nextFloat() * (max - min);
    }

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
