// Synchronized sur les methodes
class Operation extends Thread {
    private CompteBkM bk;
    private float s;

    Operation(CompteBkM b, float montant) {
        bk = b;
        s = montant;
    }

    @Override
    public void run() {
        if (s < 0)
            bk.retrait(-1 * s);
        else
            bk.depot(s);
    }
}

public class CompteBkM {

    float solde;

    CompteBkM(float soldi) {
        solde = soldi;
    }

    public synchronized void retrait(float s) {
        solde = solde - s;
    }

    public synchronized void depot(float s) {
        solde = solde + s;
    }

    public static void main(String[] args) throws InterruptedException {
        CompteBkM b = new CompteBkM(100);
        Operation A1 = new Operation(b, 100);
        Operation A2 = new Operation(b, -200);
        A1.start();
        A2.start();
        Thread.sleep(100);
        System.out.println(b.getsolde());
    }

    private float getsolde() {
        // TODO Auto-generated method stub
        return solde;
    }
}