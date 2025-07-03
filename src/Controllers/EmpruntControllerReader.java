package Controllers;

import Modeles.Emprunt;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class EmpruntControllerReader {

    private static String filePath = "Emprunts.csv";

    public static List<Emprunt> readAllEmprunts() throws IOException {
        List<Emprunt> emprunts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 7) {  
                    continue;
                }
                Emprunt emprunt = parseCsvLineToEmprunt(line);
                emprunts.add(emprunt);
            }
        }
        return emprunts;
    }
    
    public static List<String> getClientsEnRetard() throws IOException {
        List<Emprunt> allEmprunts = readAllEmprunts();
        LocalDate today = LocalDate.now();
        List<String> clientsEnRetard = new ArrayList<>();

        for (Emprunt emprunt : allEmprunts) {
            LocalDate date2 = emprunt.getDate2().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date2.isBefore(today)) { 
                clientsEnRetard.add(emprunt.getClientName());
            }
        }

        return clientsEnRetard;
    }

    public static Emprunt findEmpruntById(int id) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 7) {  
                    continue;
                }
                Emprunt emprunt = parseCsvLineToEmprunt(line);
                if (emprunt.getId() == id) {
                    return emprunt;
                }
            }
        }
        return null;  
    }
    
    public static List<Emprunt> findEmpruntByUserName(String userName) throws IOException {
        List<Emprunt> matchingEmprunts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 7) { 
                    continue;
                }
                Emprunt emprunt = parseCsvLineToEmprunt(line);
                if (emprunt.getClientName().toLowerCase().contains(userName.toLowerCase())) {
                    matchingEmprunts.add(emprunt);
                }
            }
        }
        return matchingEmprunts;
    }

    public static List<Emprunt> findEmpruntByBookName(String bookName) throws IOException {
        List<Emprunt> matchingEmprunts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 7) {
                    continue;
                }
                Emprunt emprunt = parseCsvLineToEmprunt(line);
                if (emprunt.getBookName().toLowerCase().contains(bookName.toLowerCase())) {
                    matchingEmprunts.add(emprunt);
                }
            }
        }
        return matchingEmprunts;
    }

    public static Emprunt readLastEmprunt() throws IOException {
        Emprunt lastEmprunt = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 7) { 
                    continue;
                }
                lastEmprunt = parseCsvLineToEmprunt(line);
            }
        }
        return lastEmprunt;
    }

    private static Emprunt parseCsvLineToEmprunt(String line) {
        String[] fields = line.split(",");
        if (fields.length != 7) {
            throw new IllegalArgumentException("Invalid CSV line format: " + line);
        }
        try {
            int id = Integer.parseInt(fields[0].trim());
            int idClient = Integer.parseInt(fields[1].trim());
            String clientName = fields[2].trim();
            int idBook = Integer.parseInt(fields[3].trim());
            String bookName = fields[4].trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = dateFormat.parse(fields[5].trim());
            Date date2 = dateFormat.parse(fields[6].trim());
            return new Emprunt(id, idClient, clientName, idBook, bookName, date1, date2);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing CSV line: " + line, e);
        }
    }
}
