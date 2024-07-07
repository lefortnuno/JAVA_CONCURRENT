class Operation extends Thread {
    private CompteBkNuno bk;
    private float s;

    Operation(CompteBkNuno b, float montant) {
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

public class CompteBkNuno {

    private float solde;

    CompteBkNuno(float soldi) {
        solde = soldi;
    }

    public synchronized void retrait(float s) {
        while (solde < s) {
            try {
                System.out.println("Retrait " + s + " en attente de nouveau dépôt ! Solde = " + solde);
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        solde = solde - s;
        System.out.println("Retrait effectué: " + s + ", Nouveau solde: " + solde);
    }

    public synchronized void depot(float s) {
        solde = solde + s;
        System.out.println("Dépôt effectué: " + s + ", Nouveau solde: " + solde);
        notifyAll();
    }

    public static void main(String[] args) throws InterruptedException {
        CompteBkNuno b = new CompteBkNuno(0);
        System.out.println("Solde initial : " + b.getsolde());
        Operation A1 = new Operation(b, -500);
        Operation A2 = new Operation(b, -600);
        A1.start();
        A2.start();

        Thread delayedDeposit = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(10000);
                    Operation depositOp = new Operation(b, 500);
                    depositOp.start();
                    depositOp.join();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        delayedDeposit.start();
        A1.join();
        A2.join();
        delayedDeposit.join();

        System.out.println("Solde final : " + b.getsolde());
    }

    public synchronized float getsolde() {
        return solde;
    }
}
