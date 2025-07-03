package Controllers;

import Modeles.Client;
import java.io.*;
import java.util.*;

public class ClientControllerWriter {

    private static String filePath = "Clents.csv";

    public static boolean addClient(Client client) throws IOException {
        try (
                FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)
        ) {
            Client lastClient = ClientControllerReader.readLastClient();
            int c=0;
			c=lastClient.getId()+1;
            client.setId(c);

            String line = clientToCsvLine(client);
            pw.println(line);
            List<Client> clients = ClientControllerReader.readAllClients();
            writeAllClients(clients);
            return true;
        }
    }

    public static boolean editClient(int clientId, Client updatedClient) throws IOException {
        List<Client> clients = ClientControllerReader.readAllClients();
        boolean found = false;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == clientId) {
                updatedClient.setId(clientId); 
                clients.set(i, updatedClient);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllClients(clients);
        }
        return found;
    }

    public static boolean deleteClient(int clientId) throws IOException {
        List<Client> clients = ClientControllerReader.readAllClients();
        boolean found = false;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == clientId) {
                clients.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllClients(clients);
        }
        return found;
    }

    private static void writeAllClients(List<Client> clients) throws IOException {
        try (FileWriter fw = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println("id,name,email,phone,address"); 
            for (Client client : clients) {
                pw.println(clientToCsvLine(client));
            }
        }
    }

    private static String clientToCsvLine(Client client) {
        return client.getId() + "," +
                client.getName() + "," +
                client.getEmail() + "," +
                client.getPhone() + "," +
                client.getAddress();
    }
}
