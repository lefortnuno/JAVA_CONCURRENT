import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) { 
        String serverAddress = ServerConfig.SERVER_ADDRESS; // Adresse IP du serveur
        int portNumber = ServerConfig.PORT_NUMBER; // Port sur lequel le serveur écoute

        try (
            Socket socket = new Socket(serverAddress, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connecté au serveur sur le port " + portNumber);

            String userInput;
            while ((userInput = stdIn.readLine()) != null) { 
                out.println(userInput); // Envoi de la chaîne au serveur
                String response = in.readLine(); // Réception de la réponse du serveur
                System.out.println("Réponse du serveur : " + response);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la connexion au serveur.");
            e.printStackTrace();
        }
    }
}
