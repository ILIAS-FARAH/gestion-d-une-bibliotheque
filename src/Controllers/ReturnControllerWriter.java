package Controllers;

import Modeles.Return;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReturnControllerWriter {

    private static String filePath = "Returns.csv";

    public static boolean addReturn(Return returnRecord) throws IOException {
        try (
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw)
        ) {
            Return lastReturn = ReturnControllerReader.readLastReturn();
            int newId =0;
            newId=lastReturn.getId()+1;
            returnRecord.setId(newId);

            String line = returnToCsvLine(returnRecord);
            pw.println(line);

            List<Return> returns = ReturnControllerReader.readAllReturns();
            writeAllReturns(returns);

            return true;
        }
    }

    public static boolean editReturn(int returnId, Return updatedReturn) throws IOException {
        List<Return> returns = ReturnControllerReader.readAllReturns();
        boolean found = false;
        for (int i = 0; i < returns.size(); i++) {
            if (returns.get(i).getId() == returnId) {
                returns.set(i, updatedReturn);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllReturns(returns);
        }
        return found;
    }

    public static boolean deleteReturn(int returnId) throws IOException {
        List<Return> returns = ReturnControllerReader.readAllReturns();
        boolean found = false;
        for (int i = 0; i < returns.size(); i++) {
            if (returns.get(i).getId() == returnId) {
                returns.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllReturns(returns);
        }
        return found;
    }

    private static void writeAllReturns(List<Return> returns) throws IOException {
        try (FileWriter fw = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println("id,idClient,clientName,idBook,bookName,date1,date2,date3,prix");
            for (Return returnRecord : returns) {
                pw.println(returnToCsvLine(returnRecord));
            }
        }
    }

    private static String returnToCsvLine(Return returnRecord) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	String formattedDate1 = dateFormat.format(returnRecord.getDate1());
    	String formattedDate2 = dateFormat.format(returnRecord.getDate2());
    	String formattedDate3 = dateFormat.format(returnRecord.getDate3());

        return String.format(
        		"%d,%d,%s,%d,%s,%s,%s,%s,%.2f",
        		returnRecord.getId(),
                returnRecord.getIdClient(),
                returnRecord.getClientName(),
                returnRecord.getIdBook(),
                returnRecord.getBookName(),
                formattedDate1,
                formattedDate2,
                formattedDate3,
            returnRecord.getPrix()
        );
    }
}
