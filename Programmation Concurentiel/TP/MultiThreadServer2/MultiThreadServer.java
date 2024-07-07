import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {

    public static void main(String[] args) {
        String serverAddress = ServerConfig.SERVER_ADDRESS; // Adresse IP du serveur
        int portNumber = ServerConfig.PORT_NUMBER; // Port sur lequel le serveur écoute

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Serveur en attente de connexions sur " + serverAddress + ":" + portNumber + "...");

            int clientNumber = 1;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion reçue : Client N-" + clientNumber + " depuis " + clientSocket.getRemoteSocketAddress());
                clientNumber++;

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écoute sur " + serverAddress + ":" + portNumber);
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private final Socket clientSocket;

        ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Message reçu du client : " + inputLine);
                    String capitalizedMessage = inputLine.toUpperCase();
                    out.println(capitalizedMessage);
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la communication avec le client.");
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Connexion avec le client fermée.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
