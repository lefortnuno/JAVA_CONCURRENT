import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

public class TimerChrono extends TimerTask {
   
   int i=0;
   private String name;
   private Toolkit toolkit;

   public TimerChrono(String n) { 
       name=n;
       toolkit = Toolkit.getDefaultToolkit();
   }

   public void run() { 
       System.out.println(name+":"+i+"s");
       toolkit.beep(); // Émet un bip sonore
       i++;
   }

   public static void main(String[] args) {
       System.out.println("start");
       Timer chr=new Timer();
       chr.schedule(new TimerChrono("chr1"), 0, 1000); 
   }
}
