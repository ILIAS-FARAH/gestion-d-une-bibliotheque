package Views;

import Controllers.BookControllerReader;
import Controllers.BookControllerWriter;
import Modeles.Book;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookTabbedPaneViews extends JPanel {

	private static final long serialVersionUID = 1L;
	public static List<Book> books = new ArrayList<>();
    public static JTable bookTable;
    public static DefaultTableModel tableModel;
    public static JTextField titleField, authorField, publisherField, isbnField, typeField;
    public static JButton searchButton = new JButton("Search");
    public static JButton refreshButton = new JButton("Refresh");
    public static JTextField searchField = new JTextField();

    public BookTabbedPaneViews() throws IOException {

    	setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);
        setBackground(new Color(60, 63, 65));
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchButton, BorderLayout.WEST);
        searchButton.setBackground(new Color(77, 144, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    searchBooks();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        searchPanel.add(refreshButton, BorderLayout.EAST);
        refreshButton.setBackground(new Color(77, 144, 255));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    refreshBooks();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(77, 144, 255)));
        searchField.setPreferredSize(new Dimension(250, 30));
        add(searchPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Book Details"));
        formPanel.setBackground(new Color(245, 245, 245));

        formPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        formPanel.add(authorField);

        formPanel.add(new JLabel("Publisher:"));
        publisherField = new JTextField();
        formPanel.add(publisherField);

        formPanel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        formPanel.add(isbnField);

        formPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        formPanel.add(typeField);

        books = BookControllerReader.readAllBooks();
        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Publisher", "ISBN", "Type"}, 0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getIsbn(),
                    book.getType()
            });
        }
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(30);
        bookTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookTable.setBackground(new Color(240, 240, 240));
        bookTable.setGridColor(new Color(220, 220, 220));
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 400));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(formPanel); 
        mainPanel.add(tableScrollPane); 

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        buttonPanel.setBackground(new Color(200, 221, 242));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        JButton addButton = new JButton("Add Book");
        addButton.setBackground(new Color(77, 144, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addBook();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.setBackground(new Color(77, 144, 255));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteBook();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(deleteButton);

        JButton modifyButton = new JButton("Modify Book");
        modifyButton.setBackground(new Color(77, 144, 255));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setFocusPainted(false);
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    modifyBook();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(modifyButton);

        add(buttonPanel, BorderLayout.SOUTH);

        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {
                    Book selectedBook = books.get(selectedRow);
                    titleField.setText(selectedBook.getTitle());
                    authorField.setText(selectedBook.getAuthor());
                    publisherField.setText(selectedBook.getPublisher());
                    isbnField.setText(selectedBook.getIsbn());
                    typeField.setText(selectedBook.getType());
                } else {
                    clearForm();
                }
            }
        });
    }

    static void refreshBooks() throws IOException {
        books = BookControllerReader.readAllBooks();
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getIsbn(),
                    book.getType()
            });
        }
        clearForm();
    }

    static void searchBooks() throws IOException {
        String searchQuery = searchField.getText().trim();
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search query.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            List<Book> booksByTitle = BookControllerReader.findBooksByName(searchQuery);
            List<Book> booksByType = BookControllerReader.findBooksByType(searchQuery);
            booksByTitle.addAll(booksByType);
            clearForm();
            tableModel.setRowCount(0);
            for (Book book : booksByTitle) {
                tableModel.addRow(new Object[]{
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getIsbn(),
                        book.getType()
                });
            }
            if (booksByTitle.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No books found matching the search query.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred during the search.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void addBook() throws IOException {
        try {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String publisher = publisherField.getText().trim();
            String isbn = isbnField.getText().trim();
            String type = typeField.getText().trim();

            if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || isbn.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = books.isEmpty() ? 1 : books.get(books.size() - 1).getId() + 1;
            Book book = new Book(id, title, author, publisher, isbn, type);
            boolean success = BookControllerWriter.addBook(book);
            if (success) {
                JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add book!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding the book.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void deleteBook() throws IOException {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the selected book?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    int bookId = books.get(selectedRow).getId();
                    boolean success = BookControllerWriter.deleteBook(bookId);
                    if (success) {
                        books.remove(selectedRow);
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshBooks();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete book!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred while deleting the book.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No book selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void modifyBook() throws IOException {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Book book = books.get(selectedRow);
                book.setTitle(titleField.getText().trim());
                book.setAuthor(authorField.getText().trim());
                book.setIsbn(isbnField.getText().trim());
                book.setType(typeField.getText().trim());
                book.setPublisher(publisherField.getText().trim());

                boolean success = BookControllerWriter.editBook(book.getId(), book);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshBooks();
                } else {
                    JOptionPane.showMessageDialog(this, "Book update failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid year format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No book selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void clearForm() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        publisherField.setText("");
        typeField.setText("");
        searchField.setText("");

    }

}
