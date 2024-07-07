
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    static int nbr = 0;
    int num;

    @Override
    public void run() {
        try {
            Socket sc = new Socket("localhost", 3003);
            OutputStream os = sc.getOutputStream();
            InputStream is = sc.getInputStream();

            Scanner scin = new Scanner(System.in);
            System.out.println("Client " + num + ">:");
            String msg = scin.next();
            // System.out.println("Client "+num+":"+msg );
            // l'envoi
            PrintWriter pr = new PrintWriter(os, true);
            pr.println(msg);
            Thread.sleep((long) (Math.random() * 10000));

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String res = br.readLine();
            System.out.println("Client " + num + "Recoit:" + res);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Client() {
        num = ++nbr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            Client c = new Client();
            Thread exec = new Thread(c);
            exec.start();

        }
    }

}