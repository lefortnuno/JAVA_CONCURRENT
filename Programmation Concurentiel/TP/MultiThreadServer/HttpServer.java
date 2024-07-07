import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    public static void main(String[] args) {
        String serverAddress = ServerConfig.SERVER_ADDRESS;
        int portNumber = ServerConfig.PORT_NUMBER;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Serveur HTTP en attente de connexions sur " + serverAddress + ":" + portNumber + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion reçue : " + clientSocket.getRemoteSocketAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream();

                String inputLine;
                StringBuilder request = new StringBuilder();

                // Lecture de la requête HTTP
                while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
                    request.append(inputLine).append("\r\n");
                }

                // Traitement de la requête GET
                String response;
                if (request.toString().startsWith("GET")) {
                    String message = parseMessageFromUrl(request.toString());
                    String capitalizedMessage = message.toUpperCase();
                    
                    // Réponse HTTP avec le message en majuscule dans une page HTML
                    response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                               "<!DOCTYPE html><html><head><title>Message en majuscule</title></head>" +
                               "<body><h1>Message en majuscule :</h1><h1>" + capitalizedMessage + "</h1></body></html>";
                } else {
                    response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";
                }

                out.write(response.getBytes(StandardCharsets.UTF_8));

                // Fermeture des flux et de la connexion
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écoute sur " + serverAddress + ":" + portNumber);
            e.printStackTrace();
        }
    }

    private static String parseMessageFromUrl(String request) {
        String[] lines = request.split("\r\n");
        String firstLine = lines[0];
        String[] parts = firstLine.split(" ");
        if (parts.length >= 2) {
            String url = parts[1];
            String[] urlParts = url.split("\\?");
            if (urlParts.length >= 2) {
                String[] queryParams = urlParts[1].split("&");
                for (String param : queryParams) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length >= 2 && keyValue[0].equals("message")) {
                        return URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    }
                }
            }
        }
        return "";
    }
}
