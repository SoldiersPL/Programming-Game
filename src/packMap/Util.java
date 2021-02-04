package packMap;

/**
 * Small class  for miscellaneous operations related to packMap
 */
public class Util {

    /**
     * Check if number is even
     * @param number Number to be checked
     * @return True if even, false if odd
     */
    public static boolean isEven(int number) {
        return (number & 1) == 0;
    }

    /**
     * Check if number is odd
     * @param number Number to be checked
     * @return True if even, false if odd
     */
    public static boolean isOdd(int number) {
        return (number & 1) == 1;
    }
}