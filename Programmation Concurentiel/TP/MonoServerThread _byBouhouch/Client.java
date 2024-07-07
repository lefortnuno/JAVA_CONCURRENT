import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static int num = 0;

    public static void main(String[] args) {
        num++;
        try {
            Socket sc = new Socket("localhost", 3333);
            OutputStream os = sc.getOutputStream();
            InputStream is = sc.getInputStream();

            Scanner scin = new Scanner(System.in);
            System.out.print("Client >: ");
            String msg = scin.next();
            System.out.println("Client " + num + ": " + msg);
            // l'envoi
            PrintWriter pr = new PrintWriter(os, true);
            pr.println(msg);

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String res = br.readLine();
            System.out.println("Client " + num + "| Recoit: " + res);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}