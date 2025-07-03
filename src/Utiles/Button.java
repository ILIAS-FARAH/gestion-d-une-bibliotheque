package Utiles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class Button {

	
	public static final Color ACCENT = new Color(60, 60, 60);

	public static JButton createSidebarButton2(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 80, 80));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }
	// Creates a beautifully styled button
	public static JButton createStyledButton(String text) {
	    JButton button = new JButton(text);

	    // Font and text styling
	    button.setFont(new Font("Segoe UI", Font.BOLD, 16));
	    button.setForeground(Color.WHITE); // White text for contrast

	    // Background and border styling
	    button.setBackground(new Color(255, 90, 90)); // Vibrant red color
	    Border roundedBorder = BorderFactory.createLineBorder(new Color(255, 70, 70), 2, true);
	    button.setBorder(roundedBorder);

	    // Remove default focus and enable custom cursor
	    button.setFocusPainted(false);
	    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    button.setOpaque(true);

	    // Add smooth hover effects
	    button.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseEntered(java.awt.event.MouseEvent evt) {
	            button.setBackground(new Color(220, 70, 70)); // Slightly darker red
	            button.setBorder(BorderFactory.createLineBorder(new Color(200, 50, 50), 3, true)); // Dynamic border on hover
	        }

	        public void mouseExited(java.awt.event.MouseEvent evt) {
	            button.setBackground(new Color(255, 90, 90)); // Restore original color
	            button.setBorder(roundedBorder); // Restore original border
	        }
	    });
	 

        return button;
    }

	
}
