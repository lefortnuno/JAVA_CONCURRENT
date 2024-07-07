public class TabSomme2 extends Thread {
    int[] t;
    int S = 10;

    TabSomme2(int[] T) {
        t = T;
    }

    @Override
    public void run() {

        for (int i = 0; i < t.length; i++) {
            S += t[i];
        }
        System.out.println("FR " + S);
    }

    public int getSomme() {
        return S;
    }

    public static void main(String[] args) {
        int[] t1 = { 1, 1, 1, 1, 1, 1, 1 };
        int[] t2 = { 2, 2, 2, 2, 2, 2, 2 };
        int somme = 0;
        TabSomme2 calculateur1 = new TabSomme2(t1);
        TabSomme2 calculateur2 = new TabSomme2(t2);
        calculateur1.start();
        calculateur2.start();
        try {
            calculateur1.join();
            calculateur2.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        somme = calculateur1.getSomme() + calculateur2.getSomme();

        System.out.println("somme " + somme);
    }

}