// package fork24.ts;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TabSumTask extends RecursiveTask<Long> {
    long[] t;
    int start;
    int end;

    // Constructeur de la classe
    public TabSumTask(long[] t, int istart, int iend) {
        this.t = t;
        start = istart;
        end = iend;
    }

    // Méthode pour calculer la somme des éléments du tableau
    public Long add() {
        Long S = (long) 0;
        for (int i = start; i < end; i++) {
            S += t[i];
        }
        // System.out.println("add " + Thread.currentThread().getName());
        return S;
    }

    @Override
    protected Long compute() {
        // Calcul de la taille de la sous-tâche
        int n = end - start;
        // Si la taille de la sous-tâche est inférieure ou égale à 5, calculer la somme directement
        if (n <= 5)
            return add();
        // Créer deux sous-tâches pour diviser le travail
        TabSumTask first = new TabSumTask(t, start, start + n / 2);
        TabSumTask second = new TabSumTask(t, (n / 2) + start, end);
        // Lancer la première sous-tâche
        long r = first.compute();
        // Démarrer la deuxième sous-tâche de manière asynchrone
        second.fork();
        // Attendre la fin de la deuxième sous-tâche et additionner son résultat
        r = r + second.join();
        return r;
    }

    public static void main(String[] args) {
        // Créer un tableau de nombres de 1 à 999999
        long[] t = LongStream.range(1, 1000000).toArray();
        // Créer une tâche ForkJoin pour calculer la somme du tableau
        ForkJoinTask<Long> tache = new TabSumTask(t, 0, t.length);
        // Créer un pool de threads ForkJoin
        ForkJoinPool executor = new ForkJoinPool();
        // Exécuter la tâche et obtenir le résultat
        Long resultat = executor.invoke(tache);
        // Afficher le résultat
        System.out.println("Main:" + LongStream.range(1, 1000).sum() + "exec=" + resultat);
    }
}
