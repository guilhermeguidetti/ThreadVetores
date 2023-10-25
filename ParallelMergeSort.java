import java.util.Arrays;

public class ParallelMergeSort {
    private static final ProcessorManager processorManager = new ProcessorManager(
            Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        int[] arr = Vector.populateVector(30);
        if (arr.length > 100) {
            System.out.println("Array desordenado:");
            System.out.println(Arrays.toString(arr));
        }

        parallelMergeSort(arr);

        if (arr.length > 100) {
            System.out.println("Array ordenado:");
            System.out.println(Arrays.toString(arr));
        }
    }

    private static void parallelMergeSort(int[] arr) {
        if (processorManager.hasAvailableProcessor() && arr.length > 1) {
            int middle = arr.length / 2;
            int[] leftHalf = new int[middle];
            int[] rightHalf = new int[arr.length - middle];

            for (int i = 0; i < middle; i++) {
                leftHalf[i] = arr[i];
            }
            for (int i = middle; i < arr.length; i++) {
                rightHalf[i - middle] = arr[i];
            }

            Thread leftThread = new Thread(() -> {
                processorManager.allocateProcessor();
                parallelMergeSort(leftHalf);
                processorManager.releaseProcessor();
            });
            Thread rightThread = new Thread(() -> {
                processorManager.allocateProcessor();
                parallelMergeSort(rightHalf);
                processorManager.releaseProcessor();
            });

            leftThread.start();
            rightThread.start();

            System.out.println(
                    "Thread " + Thread.currentThread().getId() + " ordenando vetor [ESQ] de tamanho "
                            + leftHalf.length);
            System.out.println(
                    "Thread " + Thread.currentThread().getId() + " ordenando vetor [DIR] de tamanho "
                            + rightHalf.length);

            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(
                    "Thread " + Thread.currentThread().getId() + " Mesclando vetores " + Arrays.toString(leftHalf)
                            + " e " + Arrays.toString(rightHalf));
            merge(arr, leftHalf, rightHalf);
        } else {
            mergeSort(arr);
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

            for (int i = 0; i < middle; i++) {
                left[i] = arr[i];
            }
            for (int i = middle; i < arr.length; i++) {
                right[i - middle] = arr[i];
            }

            mergeSort(left);
            mergeSort(right);

            merge(arr, left, right);
        }
    }
}
