package Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Controllers.ClientControllerReader;
import Controllers.ClientControllerWriter;
import Modeles.Client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientTabbedPaneViews extends JPanel {

    private static final long serialVersionUID = 1L;
    public static List<Client> clients = new ArrayList<>();
    public static JTable clientTable;
    public static DefaultTableModel tableModel;
    public static JTextField nameField, emailField, phoneField, addressField;
    public static JTextField searchField = new JTextField();
    public static JButton searchButton = new JButton("Search");
    public static JButton refreshButton = new JButton("Refresh");
    
    public ClientTabbedPaneViews() throws IOException {

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
                    searchClients();
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
                    refreshClients();
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

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Client Details"));
        formPanel.setBackground(new Color(245, 245, 245));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        add(formPanel, BorderLayout.WEST);

        // Table Panel
        clients = ClientControllerReader.readAllClients();
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone", "Address"}, 0);
        for (Client client : clients) {
            tableModel.addRow(new Object[]{
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhone(),
                    client.getAddress()
            });
        }

        clientTable = new JTable(tableModel);
        clientTable.setRowHeight(30);
        clientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        clientTable.setBackground(new Color(240, 240, 240));
        clientTable.setGridColor(new Color(220, 220, 220));
        JScrollPane tableScrollPane = new JScrollPane(clientTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 400));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(formPanel); 
        mainPanel.add(tableScrollPane); 

        add(mainPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        buttonPanel.setBackground(new Color(200, 221, 242));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JButton addButton = new JButton("Add Client");
        addButton.setBackground(new Color(77, 144, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addClient();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete Client");
        deleteButton.setBackground(new Color(77, 144, 255));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteClient();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(deleteButton);

        JButton modifyButton = new JButton("Modify Client");
        modifyButton.setBackground(new Color(77, 144, 255));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setFocusPainted(false);
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    modifyClient();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(modifyButton);

        add(buttonPanel, BorderLayout.SOUTH);

        clientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = clientTable.getSelectedRow();
                if (selectedRow != -1) {
                    Client selectedClient = clients.get(selectedRow);
                    nameField.setText(selectedClient.getName());
                    emailField.setText(selectedClient.getEmail());
                    phoneField.setText(selectedClient.getPhone());
                    addressField.setText(selectedClient.getAddress());
                } else {
                    clearForm();
                }
            }
        });
    }

    private void addClient() throws IOException {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = clients.isEmpty() ? 1 : clients.get(clients.size() - 1).getId() + 1;
        Client client = new Client(id, name, email, phone, address);
        boolean success = ClientControllerWriter.addClient(client);

        if (success) {
            JOptionPane.showMessageDialog(this, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshClients();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add client!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteClient() throws IOException {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the selected client?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                int clientId = clients.get(selectedRow).getId();
                boolean success = ClientControllerWriter.deleteClient(clientId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Client deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshClients();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete client!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No client selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyClient() throws IOException {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            Client client = clients.get(selectedRow);
            client.setName(nameField.getText().trim());
            client.setEmail(emailField.getText().trim());
            client.setPhone(phoneField.getText().trim());
            client.setAddress(addressField.getText().trim());

            boolean success = ClientControllerWriter.editClient(client.getId(), client);

            if (success) {
                JOptionPane.showMessageDialog(this, "Client updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshClients();
            } else {
                JOptionPane.showMessageDialog(this, "Client update failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No client selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchClients() throws IOException {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Client> searchResults = ClientControllerReader.findClientsByName(query);
        tableModel.setRowCount(0);

        for (Client client : searchResults) {
            tableModel.addRow(new Object[]{
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhone(),
                    client.getAddress()
            });
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No clients found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshClients() throws IOException {
        clients = ClientControllerReader.readAllClients();
        tableModel.setRowCount(0);

        for (Client client : clients) {
            tableModel.addRow(new Object[]{
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
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        searchField.setText("");
    }
}
