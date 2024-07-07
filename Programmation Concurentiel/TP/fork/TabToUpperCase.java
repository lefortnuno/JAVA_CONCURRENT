import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

public class TabToUpperCase extends RecursiveAction {
    char[] array;
    int start;
    int end;

    public TabToUpperCase(char[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    private char[] toUpperCase(char[] array, int start, int end) {
        char[] result = new char[end - start];
        for (int i = start; i < end; i++) {
            result[i - start] = Character.toUpperCase(array[i]);
        }
        return result;
    }

    @Override
    protected void compute() {
        int length = end - start;
        if (length <= 10) {
            char[] result = toUpperCase(array, start, end);
            System.arraycopy(result, 0, array, start, length);
        } else {
            int mid = start + (end - start) / 2;
            invokeAll(new TabToUpperCase(array, start, mid),
                    new TabToUpperCase(array, mid, end));
        }
    }

    public static void main(String[] args) {
        int size = 1000000;
        char[] array = IntStream.range(0, size)
                .mapToObj(i -> (char) ('a' + (i % 26)))
                .mapToInt(c -> (int) c)
                .mapToObj(c -> (char) c)
                .toArray(length -> new char[length]); // Fill array with lowercase alphabet

        ForkJoinTask<Void> task = new TabToUpperCase(array, 0, array.length);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);

        // Print the converted array
        System.out.println(new String(array));
    }
}
