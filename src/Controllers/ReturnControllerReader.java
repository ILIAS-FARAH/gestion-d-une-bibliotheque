package Controllers;

import Modeles.Return;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class ReturnControllerReader {

    private static String filePath = "Returns.csv";

    public static List<Return> readAllReturns() throws IOException {
        List<Return> returns = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty()||fields.length != 9) {
                    continue; 
                }
                Return returnRecord = parseCsvLineToReturn(line);
                returns.add(returnRecord);
            }
        }
        return returns;
    }
    
    public static float calculerRevenusDuMois() throws IOException {
        List<Return> allReturns = readAllReturns();
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        float totalRevenus = 0.0f;

        for (Return returnRecord : allReturns) {
            LocalDate returnDate = new java.sql.Date(returnRecord.getDate3().getTime()).toLocalDate();
            if (returnDate.getMonthValue() == currentMonth && returnDate.getYear() == currentYear) {
                totalRevenus += returnRecord.getPrix();
            }
        }

        return totalRevenus;
    }


    public static Return findReturnById(int id) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty()||fields.length != 9) {
                    continue; 
                }
                Return returnRecord = parseCsvLineToReturn(line);
                if (returnRecord.getId() == id) {
                    return returnRecord;
                }
            }
        }
        return null; 
    }

    public static Return readLastReturn() throws IOException {
        Return lastReturn = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                lastReturn = parseCsvLineToReturn(line);
            }
        }
        return lastReturn;
    }

    // Parses a CSV line into a Return object
    private static Return parseCsvLineToReturn(String line) {
        String[] fields = line.split(",");
        if (fields.length != 9) {
            throw new IllegalArgumentException("Invalid CSV line format: " + line);
        }


        try {
        	int id = Integer.parseInt(fields[0].trim());
            int idClient = Integer.parseInt(fields[1].trim());
            String clientName = fields[2].trim();
            int idBook = Integer.parseInt(fields[3].trim());
            String bookName = fields[4].trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
            Date date1 = dateFormat.parse(fields[5].trim());
            Date date2 = dateFormat.parse(fields[6].trim());
            Date date3 = dateFormat.parse(fields[7].trim());
            Float prix = Float.parseFloat(fields[8].trim());
            return new Return(
            		id,           // id
            		idClient,           // idClient
            		clientName,           // clientName (remove quotes)
            		idBook,           // idBook
            		bookName,           // bookName (remove quotes)
            		date1,           // date1
            		date2,           // date2
            		date3,           // date3
            		prix           // prix
            );
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format in line: " + line, e);
        }
    }
}
