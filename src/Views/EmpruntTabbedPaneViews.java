package Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Controllers.BookControllerReader;
import Controllers.ClientControllerReader;
import Controllers.EmpruntControllerWriter;
import Modeles.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpruntTabbedPaneViews extends JPanel {
	private static final long serialVersionUID = 1L;
    public static List<Client> clients = new ArrayList<>();
    public static List<Book> books = new ArrayList<>();
    public static JTable booktTable;
    public static JTable clienttTable;
    public static DefaultTableModel ctableModel;
    public static DefaultTableModel btableModel;
    public static JTextField date1, date2;
    public static JTextField searchField1 = new JTextField();
    public static JTextField searchField2 = new JTextField();
    public static JButton searchButton1 = new JButton("Search Client:");
    public static JButton searchButton2 = new JButton("Search Book:");
    public static JButton refreshButton = new JButton("Refresh");
    
    

    public EmpruntTabbedPaneViews() throws IOException {

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);
        setBackground(new Color(60, 63, 65));
        JPanel searchPanel1 = new JPanel();
        JPanel searchPanel2 = new JPanel();
        JPanel searchPanel = new JPanel();
        searchPanel1.setLayout(new BorderLayout());
        searchPanel1.add(searchButton1, BorderLayout.WEST);
        searchButton1.setBackground(new Color(77, 144, 255));
        searchButton1.setForeground(Color.WHITE);
        searchButton1.setFocusPainted(false);
        searchButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	searchClients();
                	
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        searchPanel1.add(searchField1, BorderLayout.EAST);
        searchField1.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField1.setBorder(BorderFactory.createLineBorder(new Color(200, 221, 242)));
        searchField1.setPreferredSize(new Dimension(250, 30));
        searchPanel2.setLayout(new BorderLayout());
        searchPanel2.add(searchButton2, BorderLayout.WEST);
        searchButton2.setBackground(new Color(77, 144, 255));
        searchButton2.setForeground(Color.WHITE);
        searchButton2.setFocusPainted(false);
        searchButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	searchBook();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        searchPanel2.add(searchField2, BorderLayout.EAST);
        searchField2.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField2.setBorder(BorderFactory.createLineBorder(new Color(200, 221, 242)));
        searchField2.setPreferredSize(new Dimension(250, 30));
        searchPanel.add(searchPanel1, BorderLayout.WEST);
        searchPanel.add(searchPanel2, BorderLayout.EAST);
        searchPanel.setBackground(new Color(200, 221, 242));
        add(searchPanel, BorderLayout.NORTH);
        
        
        
        
     // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Use GridLayout for alignment
        formPanel.setBorder(BorderFactory.createTitledBorder("New Emprunt:"));
        formPanel.setBackground(new Color(245, 245, 245));

        formPanel.add(new JLabel("Enter start date (dd/MM/yyyy):"));
        date1 = new JTextField();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat.format(new Date());
        date1.setText(todayDate);
        formPanel.add(date1);

        formPanel.add(new JLabel("Enter end date (dd/MM/yyyy):"));
        date2 = new JTextField();
        formPanel.add(date2);

        books = BookControllerReader.readAllBooks();
        btableModel = new DefaultTableModel(new Object[]{"ID", "Book Name"}, 0);
        for (Book book : books) {
            btableModel.addRow(new Object[]{book.getId(), book.getTitle()});
        }
        booktTable = new JTable(btableModel);
        booktTable.setFont(new Font("Arial", Font.PLAIN, 14));
        booktTable.setBackground(new Color(240, 240, 240));
        booktTable.setGridColor(new Color(220, 220, 220));
        JScrollPane bookScrollPane = new JScrollPane(booktTable);
        bookScrollPane.setPreferredSize(new Dimension(300, 150)); // Adjusted size

        clients = ClientControllerReader.readAllClients();
        ctableModel = new DefaultTableModel(new Object[]{"ID", "Client Name"}, 0);
        for (Client client : clients) {
            ctableModel.addRow(new Object[]{client.getId(), client.getName()});
        }
        clienttTable = new JTable(ctableModel);
        clienttTable.setFont(new Font("Arial", Font.PLAIN, 14));
        clienttTable.setBackground(new Color(240, 240, 240));
        clienttTable.setGridColor(new Color(220, 220, 220));
        JScrollPane clientScrollPane = new JScrollPane(clienttTable);
        clientScrollPane.setPreferredSize(new Dimension(300, 150)); // Adjusted size

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS)); // Horizontal alignment
        tablePanel.add(clientScrollPane);
        tablePanel.add(Box.createHorizontalStrut(10)); 
        tablePanel.setBackground(new Color(200, 221, 242));
        tablePanel.add(bookScrollPane);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical alignment
        mainPanel.add(tablePanel);
        mainPanel.add(Box.createVerticalStrut(10)); // Space between tables and form
        mainPanel.add(formPanel);
        mainPanel.setBackground(new Color(200, 221, 242));

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        buttonPanel.setBackground(new Color(200, 221, 242));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JButton CreatButton = new JButton("Creat");
        CreatButton.setBackground(new Color(77, 144, 255));
        CreatButton.setForeground(Color.WHITE);
        CreatButton.setFocusPainted(false);
        CreatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    creatEmprunt();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(CreatButton);
        
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
        refreshButton.setBackground(new Color(77, 144, 255));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    refreshClients();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void creatEmprunt() throws IOException {

    	String date11String = date1.getText().trim();
        String date22String = date2.getText().trim();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date11 = null;
        java.util.Date date22 = null;
        
        try {
            if (!date11String.isEmpty()) {
                date11 = dateFormat.parse(date11String);
            }
            if (!date22String.isEmpty()) {
                date22 = dateFormat.parse(date22String);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use dd/MM/yyyy.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
            return;  
        }
        int selectedRow1 = booktTable.getSelectedRow();
        int selectedRow2 = clienttTable.getSelectedRow();
        if (selectedRow1 != -1 && selectedRow2 != -1) {
            try {
            	Client client7 = clients.get(selectedRow2);
            	Book book7 = books.get(selectedRow1);
            	Emprunt Emprunt1=new Emprunt(0,client7.getId(),client7.getName(),book7.getId(),book7.getTitle(),date11,date22);
            	EmpruntControllerWriter.addEmprunt(Emprunt1);
            	refreshClients();
            	JOptionPane.showMessageDialog(this, "Emprunt created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid salary format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No user selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void searchClients() throws IOException {
        String query = searchField2.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Client> searchResults = ClientControllerReader.findClientsByName(query);
        ctableModel.setRowCount(0);

        for (Client client : searchResults) {
            ctableModel.addRow(new Object[]{
                    client.getId(),
                    client.getName()
            });
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No clients found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void searchBook() throws IOException {
        String query = searchField1.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Book> searchResults = BookControllerReader.findBooksByName(query);
        btableModel.setRowCount(0);

        for (Book Book : searchResults) {
            btableModel.addRow(new Object[]{
            		Book.getId(),
            		Book.getTitle()
            });
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No clients found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshClients() throws IOException {
    	books = BookControllerReader.readAllBooks();
        btableModel.setRowCount(0);

        for (Client client : clients) {
            btableModel.addRow(new Object[]{
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhone(),
                    client.getAddress()
            });
        }
        clients = ClientControllerReader.readAllClients();
        ctableModel.setRowCount(0);

        for (Client client : clients) {
            ctableModel.addRow(new Object[]{
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhone(),
                    client.getAddress()
            });
        }
        clearForm();
    }
   

    private void clearForm() {
    	date1.setText("");
    	date2.setText("");
    }
}
