// package MT26Avril2024;

public class Priorite extends Thread { // l'exemple fonctionne bien pour les données de priorité, mais pas l'ordre
    String chaine;

    public Priorite(String chaine) {
        this.chaine = chaine;
    }

    public void run() {
        try {
            sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(chaine);
    }

    public static void main(String[] argv) {
        System.out.println("priorite actuelle : " + Thread.currentThread().getPriority());
        System.out.println("priorite minimale : " + Thread.MIN_PRIORITY);
        System.out.println("priorite normale : " + Thread.NORM_PRIORITY);
        System.out.println("priorite maximale : " + Thread.MAX_PRIORITY);
        System.out.println("Nombre de threads : " + Thread.activeCount());
        // les trois threads qui suivent vont tourner en parallèle avec le thread main,
        Priorite t1 = new Priorite("Taza");
        t1.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        Priorite t2 = new Priorite("Rabat");
        t2.setPriority(Thread.NORM_PRIORITY);
        t2.start();
        Priorite t3 = new Priorite("Casa");
        t3.setPriority(Thread.MAX_PRIORITY);
        t3.start();
    }
}

// Les règles de priorité ne sont pas toujours respectées.