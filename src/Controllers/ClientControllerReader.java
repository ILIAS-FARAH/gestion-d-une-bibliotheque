package Controllers;

import Modeles.Client;
import java.io.*;
import java.util.*;

public class ClientControllerReader {
    public static String filePath = "Clents.csv";

    public static List<Client> readAllClients() throws IOException {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty()||fields.length != 5) {
                    continue; 
                }
                Client client = parseCsvLineToClient(line);
                clients.add(client);
            }
        }
        return clients;
    }

    public static Client findClientById(int id) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
            	line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty()||fields.length != 5) {
                    continue; 
                }
                Client client = parseCsvLineToClient(line);
                if (client.getId() == id) {
                    return client;
                }
            }
        }
        return null;
    }

    public static Client findClientByName(String name) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
            	line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty()||fields.length != 5) {
                    continue; 
                }
                Client client = parseCsvLineToClient(line);
                if (client.getName().equalsIgnoreCase(name)) {
                    return client;
                }
            }
        }
        return null;
    }

    public static List<Client> findClientsByName(String name) throws IOException {
        List<Client> matchingClients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
            	line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty()||fields.length != 5) {
                    continue; 
                }
                Client client = parseCsvLineToClient(line);
                if (client.getName().toLowerCase().contains(name.toLowerCase())) {
                    matchingClients.add(client);
                }
            }
        }
        return matchingClients;
    }

    public static Client readLastClient() throws IOException {
        Client client = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
            	line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty()||fields.length != 5) {
                    continue; 
                }
                client = parseCsvLineToClient(line); 
            }
        }
        return client;
    }

    private static Client parseCsvLineToClient(String line) {
        String[] fields = line.split(",");
        if (fields.length != 5) {
            throw new IllegalArgumentException("Invalid CSV line format: " + line);
        }
        return new Client(
            Integer.parseInt(fields[0].trim()),  // id
            fields[1].trim(),                   // name
            fields[2].trim(),                   // email
            fields[3].trim(),                   // phone
            fields[4].trim()                    // address
        );
    }
}
