package Utiles;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Times {
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
    private static JLabel dateTimeLabel;

    public static void initialize(JLabel label) {
        dateTimeLabel = label;
    }

    public static void updateDateTime() {
        if (dateTimeLabel != null) {
            String currentDateTime = DATE_TIME_FORMAT.format(new Date());
            dateTimeLabel.setText(currentDateTime);
        }
    }

    public static void startClock() {
        Timer timer = new Timer(1000, e -> updateDateTime()); // Update every second
        timer.start();
    }
}
	
	

