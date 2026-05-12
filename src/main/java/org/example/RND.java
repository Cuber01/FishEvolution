package org.example;

public class RND {
    // increaseTrue + decreaseTrue should be in the range -10 to 10
    public static boolean Chance(float increaseTrue, float decreaseTrue)
    {
        return Math.random() < 0.5f + ((increaseTrue + decreaseTrue)/20);
    }

    public static float Scale(float value, float maxValue)
    {
        return value / maxValue;
    }

    public static float Scale(float value, float maxValue, float modifier)
    {
        return Scale(value, maxValue) * modifier;
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
