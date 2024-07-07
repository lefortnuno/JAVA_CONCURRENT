// Synchronized sur l'objet bk
class Operation extends Thread {
    private CompteBk bk;
    private float s;

    Operation(CompteBk b, float montant) {
        bk = b;
        s = montant;
    }

    @Override
    public void run() {
        synchronized (bk) {
            if (s < 0)
                bk.retrait(-1 * s);
            else
                bk.depot(s);

        }
    }
}

public class CompteBk {
    float solde;

    CompteBk(float soldi) {
        solde = soldi;
    }

    public void retrait(float s) {
        synchronized (this) {
            solde = solde - s;
        }
    }

    public void depot(float s) {
        synchronized (this) {
            solde = solde + s;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CompteBk b = new CompteBk(100);
        Operation A1 = new Operation(b, 100);
        Operation A2 = new Operation(b, -200);
        A1.start();
        A2.start();
        Thread.sleep(100);
        System.out.println("Solde Finale : " + b.getsolde());

        int nbDeCoeurs = Runtime.getRuntime().availableProcessors();
        System.out.println(nbDeCoeurs + " coeurs détéctés");
    }

    private float getsolde() {
        return solde;
    }
}