package Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import Controllers.UserControllerReader;
import Controllers.UserControllerWriter;
import Modeles.User;
import java.util.ArrayList;
import java.util.List;

public class UserTabbedPaneViews extends JPanel {

	private static final long serialVersionUID = 1L;
	public static List<User> users = new ArrayList<>();
    public static JTable userTable;
    public static DefaultTableModel tableModel;
    public static JTextField nameField, cinField, emailField, passwordField, salaryField, jobField;
    public static JCheckBox adminCheckBox;
    public JButton chercher =new  JButton("Search");
    public JButton refrecher = new JButton("Refrecher");
    public static JTextField searchField = new JTextField();

    public UserTabbedPaneViews() throws IOException {

    	setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);
        setBackground(new Color(60, 63, 65));
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(chercher, BorderLayout.WEST);
        chercher.setBackground(new Color(77, 144, 255));
        chercher.setForeground(Color.WHITE);
        chercher.setFocusPainted(false);
        chercher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    rechercher();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        searchPanel.add(refrecher, BorderLayout.EAST);
        refrecher.setBackground(new Color(77, 144, 255));
        refrecher.setForeground(Color.WHITE);
        refrecher.setFocusPainted(false);
        refrecher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    refrechUsers();
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
        formPanel.setLayout(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("User Details"));
        formPanel.setBackground(new Color(245, 245, 245));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("CIN:"));
        cinField = new JTextField();
        formPanel.add(cinField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        formPanel.add(salaryField);

        formPanel.add(new JLabel("Job:"));
        jobField = new JTextField();
        formPanel.add(jobField);

        formPanel.add(new JLabel("Admin:"));
        adminCheckBox = new JCheckBox();
        formPanel.add(adminCheckBox);

        users = UserControllerReader.readAllUsers();
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "CIN", "Email", "Salary", "Job", "Admin"}, 0);
        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getName(),
                user.getCin(),
                user.getEmail(),
                user.getSalary(),
                user.getJob(),
                user.getAdmin() == 1 ? "Yes" : "No"
            });
        }
        userTable = new JTable(tableModel);
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        buttonPanel.setBackground(new Color(200, 221, 242));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        JButton addButton = new JButton("Add User");
        addButton.setBackground(new Color(77, 144, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete User");
        deleteButton.setBackground(new Color(77, 144, 255));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteUser();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(deleteButton);

        JButton modifyButton = new JButton("Modify User");
        modifyButton.setBackground(new Color(77, 144, 255));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setFocusPainted(false);
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    modifyUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(modifyButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Prevent double-triggering
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Populate the form fields with the selected user's details
                    User selectedUser = users.get(selectedRow);
                    nameField.setText(selectedUser.getName());
                    cinField.setText(selectedUser.getCin());
                    emailField.setText(selectedUser.getEmail());
                    passwordField.setText(selectedUser.getPassword());
                    salaryField.setText(String.valueOf(selectedUser.getSalary()));
                    jobField.setText(selectedUser.getJob());
                    adminCheckBox.setSelected(selectedUser.getAdmin() == 1);
                } else {
                	clearForm();
                }
            }
        });
    }    
    static void refrechUsers() throws IOException {
        users = UserControllerReader.readAllUsers();
        tableModel.setRowCount(0);
        for (User u : users) {
            tableModel.addRow(new Object[]{
                u.getId(),
                u.getName(),
                u.getCin(),
                u.getEmail(),
                u.getSalary(),
                u.getJob(),
                u.getAdmin() == 1 ? "Yes" : "No"
            });
        }
        clearForm();
    }
    static void rechercher() throws IOException {
        String searchQuery = searchField.getText().trim();
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search query.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            List<User> usersByName = UserControllerReader.findUserByName2(searchQuery);
            List<User> usersByCin = UserControllerReader.findUserByCin2(searchQuery);
            usersByName.addAll(usersByCin);
            clearForm();
            tableModel.setRowCount(0);
            for (User user : usersByName) {
                tableModel.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getCin(),
                    user.getEmail(),
                    user.getSalary(),
                    user.getJob(),
                    user.getAdmin() == 1 ? "Yes" : "No"
                });
            }
            if (usersByName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No users found matching the search query.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred during the search.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void addUser() throws IOException {
        try {
            // Validate and fetch user details from the form
            String name = nameField.getText().trim();
            String cin = cinField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String job = jobField.getText().trim();
            float salary;
            if (name.isEmpty() || cin.isEmpty() || email.isEmpty() || password.isEmpty() || job.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                salary = Float.parseFloat(salaryField.getText().trim());
                if (salary < 0) {
                    throw new IllegalArgumentException("Salary cannot be negative.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid salary format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int admin = adminCheckBox.isSelected() ? 1 : 0;
            int id = users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;
            User user = new User(id, name, cin, email, password, salary, job, admin);
            boolean success = UserControllerWriter.addUser(user);
            if (success) {
                JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refrechUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add user!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteUser() throws IOException {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the selected user?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    int userId = users.get(selectedRow).getId();
                    boolean success = UserControllerWriter.deleteUser(userId);
                    if (success) {
                        users.remove(selectedRow);
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refrechUsers();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete user!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred while deleting the user.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No user selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


 // Method to modify the selected user
    public void modifyUser() throws IOException {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                User user = users.get(selectedRow);
                user.setName(nameField.getText().trim());
                user.setCin(cinField.getText().trim());
                user.setEmail(emailField.getText().trim());
                user.setPassword(passwordField.getText().trim());
                float salary = Float.parseFloat(salaryField.getText().trim());
                if (salary < 0) {
                    throw new IllegalArgumentException("Salary cannot be negative.");
                }
                user.setSalary(salary);
                user.setJob(jobField.getText().trim());
                user.setAdmin(adminCheckBox.isSelected() ? 1 : 0);
                boolean success = UserControllerWriter.editUser(user.getId(), user);
                if (success) {
                    JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refrechUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "User update failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid salary format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No user selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void clearForm() {
        nameField.setText("");
        cinField.setText("");
        emailField.setText("");
        passwordField.setText("");
        salaryField.setText("");
        jobField.setText("");
        searchField.setText("");
        adminCheckBox.setSelected(false);
    }
	
}
