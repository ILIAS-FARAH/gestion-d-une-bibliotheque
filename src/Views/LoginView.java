package Views;

import Interfaces.LoginInterface;
import Modeles.User;

import javax.swing.*;

import Controllers.LoginController;
import Controllers.UserControllerReader;

import java.awt.*;
import java.io.IOException;

public class LoginView extends JFrame implements LoginInterface {

    private JLabel logoLabel = new JLabel();
    private JLabel usernameLabel = new JLabel("Cin:");
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField usernameTextField = new JTextField(15);
    private JPasswordField passwordTextField = new JPasswordField(15);
    private JButton loginButton = new JButton("Login");
    private JButton forgetPasswordButton = new JButton("Forget Password");
    private JLabel feedbackLabel = new JLabel();

    
    public LoginView() {
        setTitle("Login");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Images/logo.png")).getImage());
        setResizable(false);
        addComponents();
        loginButton.addActionListener(e -> clickLogin());
        forgetPasswordButton.addActionListener(e -> handleForgetPassword());
        setVisible(true);
    }

	@Override
    public void addComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(true);
        mainPanel.setBackground(new Color(60, 63, 65));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(true);
        inputPanel.setBackground(new Color(60, 63, 65));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.LIGHT_GRAY);
        usernameLabel.setBounds(50, 120, 100, 20);
        inputPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameTextField.setBounds(50, 150, 300, 35);
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameTextField.setBackground(new Color(80, 80, 80));
        usernameTextField.setForeground(Color.WHITE);
        inputPanel.add(usernameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordLabel.setBounds(50, 120, 100, 20);
        inputPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordTextField.setBounds(50, 150, 300, 35);
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordTextField.setBackground(new Color(80, 80, 80));
        passwordTextField.setForeground(Color.WHITE);
        inputPanel.add(passwordTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(feedbackLabel, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        loginButton.setBounds(50, 300, 300, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(51, 153, 255));
        loginButton.setForeground(Color.WHITE);
        inputPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        forgetPasswordButton.setBounds(50, 300, 300, 40);
        forgetPasswordButton.setFont(new Font("Arial", Font.BOLD, 16));
        forgetPasswordButton.setBackground(new Color(51, 153, 255));
        forgetPasswordButton.setForeground(Color.WHITE);
        inputPanel.add(forgetPasswordButton, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
	
	@Override
	public void handleForgetPassword() {
		//to do
	}

	public void clickLogin() {
	    String cin = usernameTextField.getText().trim();
	    String password = new String(passwordTextField.getPassword());

	    if (cin.isEmpty() || password.isEmpty()) {
	        feedbackLabel.setText("Please fill in all fields!");
	        feedbackLabel.setForeground(Color.RED);
	        return;
	    }
	    try {
	        if (LoginController.authenticate(cin, password)) {
	            User user1 = UserControllerReader.findUserByCin(cin);
	            String username = user1.getName();
	            displaySuccessMessage(username);

	            // Create and show the HomeView
	            HomeView homeView = new HomeView(user1);
	            homeView.setVisible(true);

	            // Access the parent JFrame correctly
	            Window parentWindow = SwingUtilities.getWindowAncestor(this);
	            if (parentWindow instanceof JFrame) {
	                JFrame parentFrame = (JFrame) parentWindow;
	                parentFrame.dispose(); // Close the login window
	            }

	        } else {
	            displayErrorMessage1();
	        }
	    } catch (IOException e) {
	        displayErrorMessage2();
	    }
	}

    @Override
    public void displaySuccessMessage(String username) {
        String message = "Welcome, " + username + "!";
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.GREEN);
    }

    @Override
    public void displayErrorMessage1() {
        feedbackLabel.setText("Incorrect password!");
        feedbackLabel.setForeground(Color.RED);
    }
    @Override
    public void displayErrorMessage2() {
        feedbackLabel.setText("Incorrect cin!");
        feedbackLabel.setForeground(Color.RED);
    }  
}
