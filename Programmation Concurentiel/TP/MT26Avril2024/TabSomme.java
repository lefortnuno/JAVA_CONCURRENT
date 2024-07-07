// package MT26Avril2024;

public class TabSomme extends Thread{
    int []t;
    int S=10;
    TabSomme(int []T){
        t=T;
    }
    @Override
    public void run() {
        
        for (int i = 0; i < t.length; i++) {
            S+=t[i];
        }
        System.out.println("FR "+S);
    }
    public int getSomme() {
        return S;
    }
    
    public static void main(String[] args) {
        int []t= {1,1,1,1,1,1,1};
        int []t2= {1,1,1,1,1,1,1};
        TabSomme calculateur =
                new TabSomme(t);
 calculateur.start();
 try {
    calculateur.join();
 } catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
 }
 
 System.out.println("somme "+
        calculateur.getSomme());
    }
 
 }