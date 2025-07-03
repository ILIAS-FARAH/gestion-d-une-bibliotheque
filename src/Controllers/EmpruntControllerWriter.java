package Controllers;

import Modeles.Emprunt;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmpruntControllerWriter {

    private static String filePath = "Emprunts.csv";

    public static boolean addEmprunt(Emprunt emprunt) throws IOException {
        try (
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw)
        ) {
            Emprunt lastEmprunt = EmpruntControllerReader.readLastEmprunt();
            int c = 0;
            c = lastEmprunt.getId() + 1;
            emprunt.setId(c);
            String line = empruntToCsvLine(emprunt);
            pw.println(line);
            List<Emprunt> emprunts = EmpruntControllerReader.readAllEmprunts();
            writeAllEmprunts(emprunts);
            return true;
        }
    }

    public static boolean editEmprunt(int empruntId, Emprunt updatedEmprunt) throws IOException {
        List<Emprunt> emprunts = EmpruntControllerReader.readAllEmprunts();
        boolean found = false;
        for (int i = 0; i < emprunts.size(); i++) {
            if (emprunts.get(i).getId() == empruntId) {
                emprunts.set(i, updatedEmprunt);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllEmprunts(emprunts);
        }
        return found;
    }

    public static boolean deleteEmprunt(int empruntId) throws IOException {
        List<Emprunt> emprunts = EmpruntControllerReader.readAllEmprunts();
        boolean found = false;
        for (int i = 0; i < emprunts.size(); i++) {
            if (emprunts.get(i).getId() == empruntId) {
                emprunts.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllEmprunts(emprunts);
        }
        return found;
    }

    private static void writeAllEmprunts(List<Emprunt> emprunts) throws IOException {
        try (FileWriter fw = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println("id,idClient,clientName,idBook,bookName,date1,date2");
            for (Emprunt emprunt : emprunts) {
                pw.println(empruntToCsvLine(emprunt));
            }
        }
    }

    private static String empruntToCsvLine(Emprunt emprunt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");

        String formattedDate1 = dateFormat.format(emprunt.getDate1());
        String formattedDate2 = dateFormat.format(emprunt.getDate2());
        return String.format(
            "%d,%d,%s,%d,%s,%s,%s",
            emprunt.getId(),
            emprunt.getIdClient(),
            emprunt.getClientName(),
            emprunt.getIdBook(),
            emprunt.getBookName(),
            formattedDate1,
            formattedDate2
        );
    }

}
