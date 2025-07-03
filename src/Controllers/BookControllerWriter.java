package Controllers;

import Modeles.Book;
import java.io.*;
import java.util.*;

public class BookControllerWriter {

    private static String filePath = "Book.csv";
    public static boolean addBook(Book book) throws IOException {
        try (
            FileWriter fw = new FileWriter(filePath, true); 
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw)
        ) {
            Book lastBook = BookControllerReader.readLastBook();
            int c=0;
			c=lastBook.getId()+1;
            book.setId(c);
            pw.println(bookToCsvLine(book));
            return true;
        }
    }
    public static boolean editBook(int bookId, Book updatedBook) throws IOException {
        List<Book> books = BookControllerReader.readAllBooks();
        boolean found = false;

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == bookId) {
                books.set(i, updatedBook);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllBooks(books);
        }
        return found;
    }
    public static boolean deleteBook(int bookId) throws IOException {
        List<Book> books = BookControllerReader.readAllBooks();
        boolean found = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == bookId) {
                books.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllBooks(books);
        }
        return found;
    }
    private static void writeAllBooks(List<Book> books) throws IOException {
        try (FileWriter fw = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println("id,title,author,publisher,isbn,type");
            for (Book book : books) {
                pw.println(bookToCsvLine(book));
            }
        }
    }
    private static String bookToCsvLine(Book book) {
        return book.getId() + "," +
                book.getTitle() + "," +
                book.getAuthor() + "," +
                book.getPublisher() + "," +
                book.getIsbn() + "," +
                book.getType();
    }
}
