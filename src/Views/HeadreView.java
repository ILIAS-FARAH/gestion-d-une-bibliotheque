package Views;

import Modeles.User;
import Utiles.Styles;
import Utiles.Times;
import javax.swing.*;
import java.awt.*;

public class HeadreView extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private JLabel welcomeLabel;
	public JButton logoutButton=new JButton("Log Out");
    private static JLabel dateTimeLabel = new JLabel();

    public HeadreView(User user, JFrame parentFrame) {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(200, 221, 242)); 
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Set up custom font for the header
        Font customFont = new Font("Segoe UI", Font.BOLD, 18);

        // Left Section: Welcome Message
        String formattedUsername = Styles.capitalizeFirstLetter(user.getName());
        welcomeLabel = new JLabel("Welcome, " + formattedUsername + "!");
        welcomeLabel.setFont(customFont);
        welcomeLabel.setBackground(new Color(60, 63, 65));
        welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add padding to the welcome label
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        add(welcomeLabel, BorderLayout.WEST);

        // Center Section: Date & Time Display
        dateTimeLabel.setFont(customFont);
        dateTimeLabel.setForeground(new Color(60, 63, 65));
        dateTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Times.updateDateTime(); // Initial update
        Times.startClock(); // Start live clock
        add(dateTimeLabel, BorderLayout.CENTER);
        Times.initialize(dateTimeLabel);
        Times.updateDateTime(); // Initial update
        Times.startClock(); // Start live clock

        // Right Section: Logout Button
        logoutButton.setBackground(new Color(77, 144, 255));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        add(logoutButton, BorderLayout.EAST);
    }

	

}
