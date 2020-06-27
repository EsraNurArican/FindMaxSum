import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Java class written to read a file includes a pyramid of numbers
 * And finds the max sum of the numbers (non-prime)
 */
public class FindMaxSum {

    public static int rowSize =15;
    public static int colSize = 15;
    public static int[][] arr2DValues = new int[rowSize][colSize];

    /**
     * Inner class to keep number's path coordinates
     */
    private static class Position {
        private int x; // x coordinate of number
        private int y; // y coordinate of number
        private int number; // number

        public Position(int x, int y, int number) {
            this.x = x;
            this.y = y;
            this.number = number;
        }

        /**
         * getter method for x position
         * @return x
         */
        public int getX() {
            return x;
        }

        /**
         * getter method for y position
         * @return y
         */
        public int getY() {
            return y;
        }

        /**
         * getter method for number
         * @return number value
         */
        public int getNumber() {
            return number;
        }
    }

    /**
     * Reads given file as string and converts them to integer
     * Assigns values to arr2DValues
     * @param fileName    filename
     * @param arr2DValues 2D array
     */
    private static void readNumbersFromFile(String fileName, int[][] arr2DValues) {

        try {
            InputStream inputStream = new FileInputStream(fileName);
            Scanner scanner = new Scanner(inputStream);

            int count = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] numbers = line.split("\\s+");
                for (int i = 0; i < numbers.length; ++i) {
                    arr2DValues[count][i] = Integer.parseInt(numbers[i]); //convert to int and assign to array
                }
                count++;

            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            java.lang.System.exit(1);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Something wrong with array indexes.");
            java.lang.System.exit(1);
        }
    }

    /**
     * Helper method will be used to control prime numbers
     * Checks whether number is prime
     * @param number input number
     * @return true if number is prime, false if number is non prime
     */
    private static boolean primeCheck(int number) {

        for (int i = 2; i < number; ++i) {
            if ((number % i) == 0)
                return false;
        }
        return true;
    }

    /**
     * Wrapper method
     */
    public static int findMaxSum(String fileName, ArrayList<Position> path) {

        readNumbersFromFile(fileName, arr2DValues);

        if (primeCheck(arr2DValues[0][0])) {
            return 0;
        }

        else {
            return findMaxSumRec(0, 0, rowSize, path);
        }
    }

    /**
     * Method that finds maximum sum and its path recursively
     * Moves downwards or diagonally
     * Walks over only non prime numbers
     * Uses helper methods to check prime numbers and reading input from file
     * @param x    row index
     * @param y    column index
     * @param size row size
     * @return maximum sum value with non-prime number check
     */
    private static int findMaxSumRec(int x, int y, int size, ArrayList<Position> path) {

        int leftSum = 0, rightSum = 0;

        ArrayList<Position> tempListLeft = new ArrayList<>();
        ArrayList<Position> tempListRight = new ArrayList<>();

        // Base case
        if (x >= size) {
            return 0;
        }
        //Base case
        else if (x == (size - 1)) {
            path.add(new Position(x, y, arr2DValues[x][y]));
            return arr2DValues[x][y];
        }

        //moving downwards case
        if (!primeCheck(arr2DValues[x + 1][y])) {
            rightSum = findMaxSumRec(x + 1, y, size, tempListRight);
        }

        //moving diagonally case
        if (!primeCheck(arr2DValues[x + 1][y + 1])) {
            leftSum = findMaxSumRec(x + 1, y + 1, size, tempListLeft);
        }

        // max sum path will be full path
        if (primeCheck(arr2DValues[x + 1][y + 1]) && primeCheck(arr2DValues[x + 1][y]))
            return Integer.MIN_VALUE;

        // back tracing
        if (leftSum > rightSum) {
            path.addAll(tempListLeft);
            path.add(new Position(x, y, arr2DValues[x][y]));
            return leftSum + arr2DValues[x][y];
        }
        else {
            path.addAll(tempListRight);
            path.add(new Position(x, y, arr2DValues[x][y]));
            return rightSum + arr2DValues[x][y];
        }
    }

    /**
     * Static main method to test the program
     * @param args command line parameter
     */
    public static void main(String args[]) {

        ArrayList<Position> path = new ArrayList<>();

        System.out.println("Result of Maximum Sum :" + findMaxSum("src/input.txt", path));

        System.out.println("Path of Maximum Sum: ");
        for (int i = path.size() - 1; i >= 0; --i) {
            System.out.print(path.get(i).number + " > ");
        }

    } //end of main method
} // end of class FindMaxSum



