package sort;
import java.util.*;

public class sortishik {
    public static void main(String[] args) {
        String svxod = ("hd"); //искомое
        List<String> words = new ArrayList<>(Arrays.asList(args).subList(0, args.length));
        System.out.println("Исходный массив:");
        System.out.println(words);

        // по количеству вхождений 
        BinaryInsertionSort<String> binaryInsertionSort = new BinaryInsertionSort<>();
        binaryInsertionSort.sort(words.toArray(new String[0]), new Comparator<String>() {
            public int compare(String s1, String s2) {
                int count1 = countSubstring(s1, svxod);
                int count2 = countSubstring(s2, svxod);
                return Integer.compare(count1, count2);
            }
        });

        System.out.println("\nМассив после первой сортировки:");
        printWords(words);
        System.out.println("Операций сравнения: " + binaryInsertionSort.getComparisonCount());
        System.out.println("Операций обмена: " + binaryInsertionSort.getExchangeCount());

        //  по длине строки
        CocktailSort<String> cocktailSort = new CocktailSort<>();
        cocktailSort.sort(words.toArray(new String[0]), Comparator.comparingInt(String::length));

        System.out.println("\nМассив после второй сортировки:");
        printWords(words);
        System.out.println("Операций сравнения: " + cocktailSort.getComparisonCount());
        System.out.println("Операций обмена: " + cocktailSort.getExchangeCount());
    }

    private static int countSubstring(String word, String substring) {
        int count = 0;
        int index = 0;
        while ((index = word.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        return count;
    }

    private static void printWords(List<String> words) {
        for (String word : words) {
            System.out.println(word);
        }
    }
}

interface SortingAlgorithm<T> {
    void sort(T[] array, Comparator<? super T> c);
    int getComparisonCount();
    int getExchangeCount();
}

class BinaryInsertionSort<T> implements SortingAlgorithm<T> {
    private int comparisonCount;
    private int exchangeCount;

    @Override
    public void sort(T[] array, Comparator<? super T> c) {
        comparisonCount = 0;
        exchangeCount = 0;
        for (int i = 1; i < array.length; i++) {
            T key = array[i];
            int j = Arrays.binarySearch(array, 0, i, key, c);
            if (j < 0) {
                j = -j - 1;
            }
            comparisonCount++;
            exchangeCount += i - j;
            System.arraycopy(array, j, array, j + 1, i - j);
            array[j] = key;
        }
    }

    @Override
    public int getComparisonCount() {
        return comparisonCount;
    }

    @Override
    public int getExchangeCount() {
        return exchangeCount;
    }
}

class CocktailSort<T> implements SortingAlgorithm<T> {
    private int comparisonCount;
    private int exchangeCount;

    @Override
    public void sort(T[] array, Comparator<? super T> c) {
        comparisonCount = 0;
        exchangeCount = 0;
        boolean swapped = true;
        int start = 0;
        int end = array.length;

        while (swapped) {
            swapped = false;

            for (int i = start; i < end - 1; ++i) {
                if (c.compare(array[i], array[i + 1]) > 0) {
                    T temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                    exchangeCount++;
                }
                comparisonCount++;
            }

            if (!swapped) {
                break;
            }

            swapped = false;
            end--;

            for (int i = end - 1; i >= start; --i) {
                if (c.compare(array[i], array[i + 1]) > 0) {
                    T temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                    exchangeCount++;
                }
                comparisonCount++;
            }

            start++;
        }
    }

    @Override
    public int getComparisonCount() {
        return comparisonCount;
    }

    @Override
    public int getExchangeCount() {
        return exchangeCount;
    }
}
