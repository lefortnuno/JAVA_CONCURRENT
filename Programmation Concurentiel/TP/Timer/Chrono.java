import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

public class Chrono extends TimerTask {
   
   int i=0;
   private String name;
   private Toolkit toolkit;

   public Chrono(String n) {
       // TODO Auto-generated constructor stub
       name=n;
       toolkit = Toolkit.getDefaultToolkit();
   }

   public void run() {
       // TODO Auto-generated method stub
       System.out.println(name+":"+i+"s");
       toolkit.beep(); // Émet un bip sonore
       i++;
   }

   public static void main(String[] args) {
       System.out.println("start");
       Timer chr=new Timer();
       chr.schedule(new Chrono("chr1"), 0, 1000); 
   }
}
