public class Main {
    public static void main(String[] args) {
        int[] arr = { 12, 11, 13, 5, 6, 7 };
        System.out.println("Array desordenado:");
        printArray(arr);

        ParallelMergeSort.parallelMergeSort(arr);

        System.out.println("\nArray ordenado:");
        printArray(arr);
    }

    private static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
