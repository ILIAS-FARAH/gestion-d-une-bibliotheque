package Controllers;

import Modeles.Book;
import java.io.*;
import java.util.*;

public class BookControllerReader {

    private static String filePath = "Book.csv"; 
// This Java method reads a CSV file (Book.csv) and returns a list of 
// Book
//  objects. It skips the first line, then for each subsequent line, it trims and splits the line into fields, ignoring lines with incorrect format. It then converts each valid line into a 
// Book
//  object using the 
// parseCsvLineToBook
//  method and adds it to the list.
    public static List<Book> readAllBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim(); 
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 6) {
                    continue; 
                }
                Book book = parseCsvLineToBook(line);
                books.add(book);
            }
        }
        return books;
    }

//     This Java method reads a CSV file (Book.csv) and returns a 
// Book
//  object with the matching id. It skips the first line, trims and splits each subsequent line into fields, and ignores lines with incorrect format. If a matching 
// Book
//  is found, it returns the object; otherwise, it returns null.
    public static Book findBookById(int id) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim(); 
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 6) {
                    continue; 
                }
                Book book = parseCsvLineToBook(line);
                if (book.getId() == id) {
                    return book;
                }
            }
        }
        return null;
    }
    
//     This Java method reads a CSV file (Book.csv) and returns a list of 
// Book
//  objects that match a specified book type. It ignores lines with incorrect format and performs a case-insensitive search for the book type.

// (Note: This explanation is based on the provided code snippet and the context from src/Controllers/BookControllerReader.java:Controllers.BookControllerReader.findBooksByType)
    public static List<Book> findBooksByType(String type) throws IOException {
        List<Book> matchingBooks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim(); 
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 6) {
                    continue; 
                }
                Book book = parseCsvLineToBook(line);
                if (book.getType().toLowerCase().contains(type.toLowerCase())) {
                    matchingBooks.add(book);
                }
            }
        }
        return matchingBooks;
    }

//     This Java method reads a CSV file (Book.csv) and returns a list of 
// Book
//  objects whose title contains the specified name (case-insensitive). It skips the first line, ignores lines with incorrect format, and trims each line before processing.
    public static List<Book> findBooksByName(String name) throws IOException {
        List<Book> matchingBooks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 6) {
                    continue; 
                }
                Book book = parseCsvLineToBook(line);
                if (book.getTitle().toLowerCase().contains(name.toLowerCase())) {
                    matchingBooks.add(book);
                }
            }
        }
        return matchingBooks;
    }
//     This Java method reads a CSV file (located at filePath) and returns the last book record. It skips the first line, trims and splits each subsequent line into fields, and ignores lines with incorrect format. If a valid book record is found, it is parsed into a 
// Book
//  object and stored in the 
// book
//  variable, overwriting any previous value.
    public static Book readLastBook() throws IOException {
        Book book = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] fields = line.split(",");
                if (line.isEmpty() || fields.length != 6) {
                    continue;
                }
                book = parseCsvLineToBook(line);
            }
        }
        return book;
    }
//     This Java method 
// parseCsvLineToBook
//  takes a string line representing a CSV line and attempts to parse it into a 
// Book
//  object. It splits the line into fields using commas as delimiters, checks if there are exactly 6 fields (id, title, author, publisher, isbn, and type), and if so, creates a new 
// Book
//  object with the corresponding values. If the line format is invalid (i.e., not 6 fields), it throws an IllegalArgumentException.
    private static Book parseCsvLineToBook(String line) {
        String[] fields = line.split(",");
        if (fields.length != 6) {
            throw new IllegalArgumentException("Invalid CSV line format: " + line);
        }
        return new Book(
            Integer.parseInt(fields[0].trim()),  // id
            fields[1].trim(),                    // title
            fields[2].trim(),                    // author
            fields[3].trim(),                    // publisher
            fields[4].trim(),                    // isbn
            fields[5].trim()                     // type
        );
    }
}
