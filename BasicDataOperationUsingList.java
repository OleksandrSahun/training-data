import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList надає методи для виконання основних операцiй з даними типу Double.
 */
public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/double.data";

    double valueToSearch;
    Double[] doubleArray;
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
        doubleList = new ArrayList<>(Arrays.asList(doubleArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     */
    void doDataOperation() {
        searchArray();
        findMinAndMaxInArray();

        sortArrayDescending();

        searchArray();
        findMinAndMaxInArray();

        searchList();
        findMinAndMaxInList();

        sortListDescending();

        searchList();
        findMinAndMaxInList();

        Utils.writeArrayToFile(doubleArray, PATH_TO_DATA_FILE + ".sorted");
    }

    void sortArrayDescending() {
        long startTime = System.nanoTime();

        Arrays.sort(doubleArray, Collections.reverseOrder());

        Utils.printOperationDuration(startTime, "сортування масиву в зворотному порядку");
    }

    void searchArray() {
        long startTime = System.nanoTime();

        boolean found = Arrays.stream(doubleArray)
                .anyMatch(value -> value == valueToSearch);

        Utils.printOperationDuration(startTime, "пошук в масиві");

        if (found) {
            System.out.println("Значення '" + valueToSearch + "' знайдено в масиві.");
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

        double min = Arrays.stream(doubleArray).min(Double::compareTo).orElse(Double.NaN);
        double max = Arrays.stream(doubleArray).max(Double::compareTo).orElse(Double.NaN);

        Utils.printOperationDuration(startTime, "пошук мінімального і максимального в масиві");

        System.out.println("Мінімальне значення в масиві: " + min);
        System.out.println("Максимальне значення в масиві: " + max);
    }

    void searchList() {
        long startTime = System.nanoTime();

        boolean found = doubleList.stream()
                .anyMatch(value -> value == valueToSearch);

        Utils.printOperationDuration(startTime, "пошук в ArrayList");

        if (found) {
            System.out.println("Значення '" + valueToSearch + "' знайдено в ArrayList.");
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

        double min = doubleList.stream().min(Double::compareTo).orElse(Double.NaN);
        double max = doubleList.stream().max(Double::compareTo).orElse(Double.NaN);

        Utils.printOperationDuration(startTime, "пошук мінімального і максимального в ArrayList");

        System.out.println("Мінімальне значення в ArrayList: " + min);
        System.out.println("Максимальне значення в ArrayList: " + max);
    }

    void sortListDescending() {
        long startTime = System.nanoTime();

        Collections.sort(doubleList, Collections.reverseOrder());

        Utils.printOperationDuration(startTime, "сортування ArrayList в зворотному порядку");
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу Double.
 */
class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>> Час виконання операції '" + operationName + "': " + duration + " наносекунд");
    }

    static Double[] readArrayFromFile(String pathToFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            return br.lines()
                    .map(Double::valueOf) // Парсимо кожний рядок у Double
                    .toArray(Double[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання файлу: " + pathToFile, e);
        }
    }

    static void writeArrayToFile(Double[] array, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            Arrays.stream(array)
                    .forEach(value -> {
                        try {
                            writer.write(Double.toString(value));
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException("Помилка запису у файл: " + pathToFile, e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Помилка запису у файл: " + pathToFile, e);
        }
    }
}
