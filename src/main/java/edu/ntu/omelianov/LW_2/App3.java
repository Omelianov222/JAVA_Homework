package edu.ntu.omelianov.LW_2;
import java.util.Scanner;
import java.util.Random;

public class App3 {

    private static final int MAX_SIZE = 20;
    private static final int MIN_SIZE = 1;
    private static final int MANUAL_CREATION = 1;
    private static final int RANDOM_CREATION = 2;
    private static final int MIN_RANDOM_VALUE = -100;
    private static final int MAX_RANDOM_VALUE = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = readMatrixDimension(scanner, "Введіть кількість рядків (" + MIN_SIZE + "–" + MAX_SIZE + "): ");
        int cols = readMatrixDimension(scanner, "Введіть кількість стовпців (" + MIN_SIZE + "–" + MAX_SIZE + "): ");
        int[][] matrix = createMatrixFromUserSelection(scanner, rows, cols);

        System.out.println("\nМатриця:");
        displayMatrix(matrix);

        printResults(matrix);
        scanner.close();
    }

    private static int readMatrixDimension(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            if (!scanner.hasNextInt()) {
                System.out.println(ErrorMessages.notInteger());
                scanner.next();
                continue;
            }
            int value = scanner.nextInt();
            if (value < MIN_SIZE || value > MAX_SIZE) {
                System.out.println(ErrorMessages.invalidRange(MIN_SIZE, MAX_SIZE));
                continue;
            }
            return value;
        }
    }

    private static int[][] createMatrixFromUserSelection(Scanner scanner, int rows, int cols) {
        System.out.println("Оберіть спосіб створення матриці:");
        System.out.println(MANUAL_CREATION + " — Ввести вручну");
        System.out.println(RANDOM_CREATION + " — Згенерувати випадково");

        return readMatrixCreationMode(scanner) == MANUAL_CREATION
                ? createMatrixFromKeyboardInput(scanner, rows, cols)
                : createMatrixWithRandomValues(rows, cols);
    }

    private static int readMatrixCreationMode(Scanner scanner) {
        while (true) {
            System.out.print("Ваш вибір: ");
            if (!scanner.hasNextInt()) {
                System.out.println(ErrorMessages.notInteger());
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            if (choice == MANUAL_CREATION || choice == RANDOM_CREATION) return choice;
            System.out.println(ErrorMessages.invalidChoice(MANUAL_CREATION, RANDOM_CREATION));
        }
    }

    private static int[][] createMatrixFromKeyboardInput(Scanner scanner, int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        System.out.println("Введіть елементи матриці (" + rows + "×" + cols + "):");

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                matrix[i][j] = readIntegerElement(scanner, i, j);

        return matrix;
    }

    private static int readIntegerElement(Scanner scanner, int i, int j) {
        while (true) {
            System.out.print("Елемент [" + i + "][" + j + "]: ");
            if (scanner.hasNextInt()) return scanner.nextInt();
            System.out.println(ErrorMessages.notInteger());
            scanner.next();
        }
    }

    private static int[][] createMatrixWithRandomValues(int rows, int cols) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];
        int range = MAX_RANDOM_VALUE - MIN_RANDOM_VALUE + 1;

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                matrix[i][j] = random.nextInt(range) + MIN_RANDOM_VALUE;

        return matrix;
    }

    private static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) System.out.printf("%6d", element);
            System.out.println();
        }
    }

    private static void printResults(int[][] matrix) {
        int min = findMatrixMinimum(matrix);
        int max = findMatrixMaximum(matrix);
        double meanA = computeMatrixArithmeticMean(matrix);
        double meanG = computeMatrixGeometricMean(matrix);

        System.out.println("\nРезультати:");
        System.out.println("Мінімальний елемент: " + min);
        System.out.println("Максимальний елемент: " + max);
        System.out.printf("Середнє арифметичне: %.4f%n", meanA);
        System.out.printf("Середнє геометричне: %.4f%n", meanG);
    }

    private static int findMatrixMinimum(int[][] matrix) {
        int min = matrix[0][0];
        for (int[] row : matrix)
            for (int element : row)
                min = Math.min(min, element);
        return min;
    }

    private static int findMatrixMaximum(int[][] matrix) {
        int max = matrix[0][0];
        for (int[] row : matrix)
            for (int element : row)
                max = Math.max(max, element);
        return max;
    }

    private static double computeMatrixArithmeticMean(int[][] matrix) {
        long sum = 0;
        int count = 0;
        for (int[] row : matrix)
            for (int element : row) {
                sum += element;
                count++;
            }
        return (double) sum / count;
    }

    private static double computeMatrixGeometricMean(int[][] matrix) {
        double logSum = 0.0;
        int count = 0;

        for (int[] row : matrix)
            for (int element : row) {
                if (element <= 0) {
                    System.out.println(ErrorMessages.nonPositiveForGeometric());
                    return Double.NaN;
                }
                logSum += Math.log(element);
                count++;
            }

        return Math.exp(logSum / count);
    }
}

final class ErrorMessages {

    private ErrorMessages() {}

    public static String notInteger() {
        return "Помилка: введіть ціле число.";
    }

    public static String invalidRange(int min, int max) {
        return "Помилка: число повинно бути в діапазоні від " + min + " до " + max + ".";
    }

    public static String invalidChoice(int option1, int option2) {
        return "Помилка: введіть " + option1 + " або " + option2 + ".";
    }

    public static String nonPositiveForGeometric() {
        return "Помилка: середнє геометричне можна обчислити лише для додатних чисел.";
    }
}
