package Utiles;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class Styles {
	
    private static final Color DARKER_BG = new Color(25, 25, 25);

	public static void styleTextField(JTextField textField) {
        textField.setBackground(DARKER_BG);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

	// Capitalizes the first letter of the username
    public static String capitalizeFirstLetter(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

}
