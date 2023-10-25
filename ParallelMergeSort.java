public class ParallelMergeSort {
    public static void main(String[] args) {
        int[] arr = Vector.populateVector(100); // Use a classe Vector para criar o vetor desordenado
        System.out.println("Array desordenado:");
        printArray(arr);

        parallelMergeSort(arr, Runtime.getRuntime().availableProcessors());

        System.out.println("\nArray ordenado:");
        printArray(arr);
    }

    private static void parallelMergeSort(int[] arr, int processors) {
        Thread currentThread = Thread.currentThread();
        String threadInfo = "Thread " + currentThread.getId();
        System.out.println(threadInfo + ": Iniciada para ordenar vetor de tamanho " + arr.length);

        if (processors <= 1 || arr.length <= 1) {
            mergeSort(arr);
        } else {
            int middle = arr.length / 2;
            int[] leftHalf = new int[middle];
            int[] rightHalf = new int[arr.length - middle];

            System.arraycopy(arr, 0, leftHalf, 0, middle);
            System.arraycopy(arr, middle, rightHalf, 0, arr.length - middle);

            Thread leftThread = new Thread(() -> parallelMergeSort(leftHalf, processors / 2));
            Thread rightThread = new Thread(() -> parallelMergeSort(rightHalf, processors / 2));

            leftThread.start();
            rightThread.start();

            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(
                    threadInfo + ": Mesclando vetores de tamanho " + leftHalf.length + " e " + rightHalf.length);
            System.out.print(threadInfo + ": Vetor esquerdo: ");
            printArray(leftHalf);
            System.out.print(threadInfo + ": Vetor direito: ");
            printArray(rightHalf);
            System.out.println();
            merge(arr, leftHalf, rightHalf);
        }
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < left.length) {
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

    private static void mergeSort(int[] arr) {
        if (arr.length > 1) {
            int middle = arr.length / 2;
            int[] left = new int[middle];
            int[] right = new int[arr.length - middle];

            System.arraycopy(arr, 0, left, 0, middle);
            System.arraycopy(arr, middle, right, 0, arr.length - middle);

            mergeSort(left);
            mergeSort(right);

            merge(arr, left, right);
        }
    }

    private static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
