import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

class Service extends Thread {
    Socket s;
    private static final String SECRET_KEY = ConfigServeur.SECRET_KEYX;
    private static final int BIT = ConfigServeur.BIT_NUMBER;

    public Service(Socket s2) {
        s = s2;
    }

    public void run() {
        System.out.println("Nouvelle connexion cliente acceptée");
        try {
            // Flux d'entrée pour lire les octets bruts du message chiffré recu
            InputStream is = s.getInputStream();
            byte[] encryptedBytes = new byte[BIT];
            int bytesRead = is.read(encryptedBytes);

            // Déchiffrement et Réponse
            String decryptedMessage = decrypt(encryptedBytes, bytesRead);
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            pw.println(decryptedMessage);

            pw.close();
            is.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fonction pour déchiffrer un message avec Cipher
    private String decrypt(byte[] encryptedBytes, int length) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes, 0, length);
        return new String(decryptedBytes);
    }
}

public class Serveur {
    private static final int PORT = ConfigServeur.PORT_NUMBER;

    public Serveur() throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Serveur en attente de connexion...");

        while (true) {
            Socket s = ss.accept();
            Service srv = new Service(s);
            srv.start();
        }
    }

    public static void main(String[] args) {
        try {
            Serveur s = new Serveur();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
