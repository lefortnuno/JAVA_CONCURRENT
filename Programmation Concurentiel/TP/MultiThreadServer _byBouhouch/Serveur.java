import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Service extends Thread {
    Socket s;

    public Service(Socket s2) {
        // TODO Auto-generated constructor stub
        s = s2;
    }

    public void run() {
        System.out.println("Client Accepted ..... ");
        InputStream is;
        try {
            is = s.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String msg = br.readLine();
            // System.out.println("srv recoit:"+msg);
            msg = msg.toUpperCase();

            OutputStream os = s.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println(msg);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

public class Serveur {
    int port = 3003;

    public Serveur() throws IOException {
        // TODO Auto-generated constructor stub
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Wait ..... ");
        while (true) {
            Socket s = ss.accept();
            Service srv = new Service(s);
            srv.start(); 
        }

    }

    public static void main(String[] args) {
        try {
            Serveur serveur = new Serveur();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
