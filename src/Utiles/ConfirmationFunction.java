package Utiles;

import java.awt.Color;
import javax.swing.*;

public class ConfirmationFunction {
	public void showConfirmationDialog(String message, Runnable function) {
        // Customize the dialog with an icon
        Icon confirmationIcon = UIManager.getIcon("OptionPane.questionIcon");
        UIManager.put("OptionPane.background", new Color(248, 248, 255)); // Soft white-blue
        UIManager.put("Panel.background", new Color(248, 248, 255));
        UIManager.put("Button.background", new Color(220, 220, 255));
        UIManager.put("Button.foreground", Color.BLACK);

        // Show the dialog
        int response = JOptionPane.showConfirmDialog(
            null,
            wrapMessage(message),
            "Confirmation Required",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            confirmationIcon
        );
        if (response == JOptionPane.YES_OPTION) {
            function.run();
        }
    }
    public String wrapMessage(String message) {
        return "<html><div style='font-family: Arial; font-size: 14px; text-align: center;'>"
                + message
                + "</div></html>";
    }
}