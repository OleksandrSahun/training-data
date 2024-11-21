import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList надає методи для виконання основних операцiй з даними типу LocalDateTime.
 * 
 * <p>Цей клас зчитує данi з файлу "list/LocalDateTime.data", сортує їх та виконує пошук значення в масивi та списку.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основнi операцiї з даними.</li>
 *   <li>{@link #sortArray()} - Сортує масив LocalDateTime.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi LocalDateTime.</li>
 *   <li>{@link #sortList()} - Сортує список LocalDateTime.</li>
 *   <li>{@link #searchList()} - Виконує пошук значення в списку LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInList()} - Знаходить мiнiмальне та максимальне значення в списку LocalDateTime.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingList(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змiннi екземпляра:</p>
 * <ul>
 *   <li>{@link #dateTimeValueToSearch} - Значення LocalDateTime для пошуку.</li>
 *   <li>{@link #dateTimeArray} - Масив LocalDateTime.</li>
 *   <li>{@link #dateTimeList} - Список LocalDateTime.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingList "2024-03-16T00:12:38Z"
 * }
 * </pre>
 */
public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/double.data";

    double valueToSearch;
    double[] doubleArray;
    List<Double> doubleList;

    public static void main(String[] args) {
        BasicDataOperationUsingList app = new BasicDataOperationUsingList(args);
        app.doDataOperation();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingList(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Відсутнє значення для пошуку");
        }

        String searchValue = args[0];
        valueToSearch = Double.parseDouble(searchValue);

        doubleArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        doubleList = new ArrayList<>();
        for (double d : doubleArray) {
            doubleList.add(d);
        }
    }

    /**
     * Виконує основнi операцiї з даними.
     * 
     * Метод зчитує масив та список об'єктiв LocalDateTime з файлу, сортує їх та виконує пошук значення.
     */
    void doDataOperation() {
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        searchList();
        findMinAndMaxInList();

        sortList();

        searchList();
        findMinAndMaxInList();

        Utils.writeArrayToFile(doubleArray, PATH_TO_DATA_FILE + ".sorted");
    }

    void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(doubleArray);

        Utils.printOperationDuration(startTime, "сортування масиву");
    }

    void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(doubleArray, valueToSearch);

        Utils.printOperationDuration(startTime, "пошук в масиві");

        if (index >= 0) {
            System.out.println("Значення '" + valueToSearch + "' знайдено в масиві за індексом: " + index);
        } else {
            System.out.println("Значення '" + valueToSearch + "' в масиві не знайдено.");
        }
    }

    void findMinAndMaxInArray() {
        if (doubleArray == null || doubleArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();

        double min = Arrays.stream(doubleArray).min().orElseThrow();
        double max = Arrays.stream(doubleArray).max().orElseThrow();

        Utils.printOperationDuration(startTime, "пошук мінімального і максимального в масиві");

        System.out.println("Мінімальне значення в масиві: " + min);
        System.out.println("Максимальне значення в масиві: " + max);
    }

    void searchList() {
        long startTime = System.nanoTime();

        int index = Collections.binarySearch(doubleList, valueToSearch);

        Utils.printOperationDuration(startTime, "пошук в ArrayList");

        if (index >= 0) {
            System.out.println("Значення '" + valueToSearch + "' знайдено в ArrayList за індексом: " + index);
        } else {
            System.out.println("Значення '" + valueToSearch + "' в ArrayList не знайдено.");
        }
    }

    void findMinAndMaxInList() {
        if (doubleList == null || doubleList.isEmpty()) {
            System.out.println("ArrayList порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();

        double min = Collections.min(doubleList);
        double max = Collections.max(doubleList);

        Utils.printOperationDuration(startTime, "пошук мінімального і максимального в ArrayList");

        System.out.println("Мінімальне значення в ArrayList: " + min);
        System.out.println("Максимальне значення в ArrayList: " + max);
    }

    void sortList() {
        long startTime = System.nanoTime();

        Collections.sort(doubleList);

        Utils.printOperationDuration(startTime, "сортування ArrayList");
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу LocalDateTime.
 */
class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>> Час виконання операції '" + operationName + "': " + duration + " наносекунд");
    }

    static double[] readArrayFromFile(String pathToFile) {
        List<Double> tempList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                tempList.add(Double.parseDouble(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] array = new double[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            array[i] = tempList.get(i);
        }

        return array;
    }

    static void writeArrayToFile(double[] array, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (double d : array) {
                writer.write(Double.toString(d));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}