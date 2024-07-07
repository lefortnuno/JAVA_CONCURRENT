import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MyTabToUpperCase extends RecursiveTask<Void> {
    String[] t;
    int start;
    int end;

    public MyTabToUpperCase(String[] t, int istart, int iend) {
        this.t = t;
        start = istart;
        end = iend;
    }

    public Void upperCase() {
        for (int i = start; i < end; i++) {
            t[i] = t[i].toUpperCase();
        }
        return null;
    }

    @Override
    protected Void compute() {
        int n = end - start;
        if (n <= 10) {
            return upperCase();
        }

        MyTabToUpperCase first = new MyTabToUpperCase(t, start, start + n / 2);
        MyTabToUpperCase second = new MyTabToUpperCase(t, start + n / 2, end);

        first.fork();
        second.compute();
        first.join();

        return null;
    }

    public static void main(String[] args) {
        Random random = new Random();
        String[] t = IntStream.range(0, 1000000)
                .mapToObj(i -> String.valueOf((char) (random.nextInt(26) + 'a')))
                .toArray(String[]::new);

        // Vérifier le résultat
        for (int i = 0; i < 20; i++) { // Affiche les 20 premières chaînes
            System.out.println(t[i]);
        }

        ForkJoinTask<Void> task = new MyTabToUpperCase(t, 0, t.length);
        ForkJoinPool executor = new ForkJoinPool();
        executor.invoke(task);

        // Vérifier le résultat
        for (int i = 0; i < 20; i++) { // Affiche les 20 premières chaînes pour vérifier
            System.out.println(t[i]);
        }
    }
}
