
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.net.Socket;

public class Client implements Runnable {
    private static final String SERVER_IP = ConfigServeur.SERVER_ADDRESS;
    private static final String SECRET_KEY = ConfigServeur.SECRET_KEYX;
    private static final int SERVER_PORT = ConfigServeur.PORT_NUMBER;
    private static final int BIT = ConfigServeur.BIT_NUMBER;

    static int nbr = 0;
    int num;

    @Override
    public void run() {
        try {
            // Connexion au serveur
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Client N°" + num + " connecté : ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message = reader.readLine();

            // Chiffrement et Envoi du message chiffré au serveur
            byte[] encryptedBytes = encrypt(message);
            OutputStream os = socket.getOutputStream();
            os.write(encryptedBytes);

            Thread.sleep((long) (Math.random() * 1000));
            String em = new String(encryptedBytes);
            System.out.println("Message n°" + num + " chiffrée : " + em);
            Thread.sleep((long) (Math.random() * 10000));

            // Récupération de la réponse du serveur
            InputStream is = socket.getInputStream();
            byte[] responseBytes = new byte[BIT];
            int bytesRead = is.read(responseBytes);
            String response = new String(responseBytes, 0, bytesRead);
            System.out.print("Client N°" + num + " recoit : " + response);

            os.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fonction pour chiffrer un message avec Cipher
    private byte[] encrypt(String message) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(message.getBytes());
    }

    public Client() {
        num = ++nbr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            Client client = new Client();
            Thread exec = new Thread(client);
            exec.start();
        }
    }
}
