package Views;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import Controllers.*;
import Modeles.*;

public class ReturnTabbedPaneViews extends JPanel {
	private static final long serialVersionUID = 1L;
	public static List<Emprunt> Emprunts = new ArrayList<>();
    public static JTable userTable;
    public static DefaultTableModel tableModel;
    public static JTextField date333;
    public JButton chercher = new JButton("Search");
    public JButton refrecher = new JButton("Refresh");
    public static JTextField searchField = new JTextField();

    public ReturnTabbedPaneViews() throws IOException {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);
        setBackground(new Color(60, 63, 65));

        // Search panel setup
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(chercher, BorderLayout.WEST);
        chercher.setBackground(new Color(77, 144, 255));
        chercher.setForeground(Color.WHITE);
        chercher.setFocusPainted(false);
        chercher.addActionListener(e -> {
            try {
                rechercher();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        searchPanel.add(refrecher, BorderLayout.EAST);
        refrecher.setBackground(new Color(77, 144, 255));
        refrecher.setForeground(Color.WHITE);
        refrecher.setFocusPainted(false);
        refrecher.addActionListener(e -> {
            try {
                refrechUsers();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(77, 144, 255)));
        searchField.setPreferredSize(new Dimension(250, 30));
        add(searchPanel, BorderLayout.NORTH);

        // Form panel setup
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 1, 3, 3));
        formPanel.setBorder(BorderFactory.createTitledBorder("Emprunt Details"));
        formPanel.setBackground(new Color(245, 245, 245));

        formPanel.add(new JLabel("Real Return Day :"));
        date333 = new JTextField();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat.format(new Date());
        date333.setText(todayDate);
        formPanel.add(date333);

     // Table setup
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Emprunts = EmpruntControllerReader.readAllEmprunts();
        tableModel = new DefaultTableModel(new Object[]{"ID", "ClientName", "BookName", "Emprunt Date", "ReturnDate", "Days", "Prix"}, 0);
        userTable = new JTable(tableModel);

        // Add rows to the table
        for (Emprunt emprunt : Emprunts) {
            tableModel.addRow(new Object[]{
                emprunt.getId(),
                emprunt.getClientName(),
                emprunt.getBookName(),
                dateFormatter.format(emprunt.getDate1()),  // Format date1
                dateFormatter.format(emprunt.getDate2()),  // Format date2
                emprunt.calculateDaysFromToday(),
                emprunt.calculatePrix(),
            });
        }


        // Custom Renderer
        userTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int modelRow = userTable.convertRowIndexToModel(row);
                Emprunt emprunt = Emprunts.get(modelRow);

                if (emprunt.isDate2PassedToday()) {
                    cell.setBackground(Color.DARK_GRAY);
                    cell.setForeground(Color.WHITE);
                } else {
                    cell.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                    cell.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                }

                return cell;
            }
        });

        
        userTable.setRowHeight(30);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.setBackground(new Color(240, 240, 240));
        userTable.setGridColor(new Color(220, 220, 220));
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 400));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(formPanel);
        mainPanel.add(tableScrollPane);
        mainPanel.setBackground(new Color(200, 221, 242));
        add(mainPanel, BorderLayout.CENTER);
        // Button panel setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        buttonPanel.setBackground(new Color(200, 221, 242));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        JButton addButton = new JButton("Payer");
        addButton.setBackground(new Color(77, 144, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            try {
                Payer();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    static void refrechUsers() throws IOException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        Emprunts = EmpruntControllerReader.readAllEmprunts();
        tableModel.setRowCount(0);
        for (Emprunt emprunt : Emprunts) {
            tableModel.addRow(new Object[]{
                emprunt.getId(),
                emprunt.getClientName(),
                emprunt.getBookName(),
                dateFormatter.format(emprunt.getDate1()),  // Format date1
                dateFormatter.format(emprunt.getDate2()),  // Format date2
                emprunt.calculateDaysFromToday(),
                emprunt.calculatePrix(),
            });
        }
        clearForm();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat.format(new Date());
        date333.setText(todayDate);
    }
    static void rechercher() throws IOException {
        String searchQuery = searchField.getText().trim();
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search query.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            List<Emprunt> results = EmpruntControllerReader.findEmpruntByUserName(searchQuery);
            results.addAll(EmpruntControllerReader.findEmpruntByBookName(searchQuery));
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
            tableModel.setRowCount(0);
            for (Emprunt emprunt : results) {
                tableModel.addRow(new Object[]{
                    emprunt.getId(),
                    emprunt.getClientName(),
                    emprunt.getBookName(),
                    dateFormatter.format(emprunt.getDate1()),  // Format date1
                    dateFormatter.format(emprunt.getDate2()), 
                    emprunt.calculateDaysFromToday(),
                    emprunt.calculatePrix(),
                });
            }
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No results found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred during the search.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    public void Payer() throws IOException { 
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Emprunt emprunt = Emprunts.get(selectedRow);
                String date3String = date333.getText().trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                
                Date date11;
                try {
                    if (!date3String.isEmpty()) {
                        date11 = dateFormat.parse(date3String);
                    } else {
                        JOptionPane.showMessageDialog(this, "The date field cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "Invalid date format. Please use M/d/yyyy, e.g., 5/8/2025.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Return returnObj = new Return(0, emprunt.getIdClient(), emprunt.getClientName(), emprunt.getIdBook(),
                        emprunt.getBookName(), emprunt.getDate1(), emprunt.getDate2(), date11, emprunt.calculatePrix());
                EmpruntControllerWriter.deleteEmprunt(emprunt.getId());
                boolean success = ReturnControllerWriter.addReturn(returnObj);
                refrechUsers();
                if (success) {
                    JOptionPane.showMessageDialog(this, "Payment processed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refrechUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No record selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void clearForm() {
        date333.setText("");
    }
}

