package Controllers;
import Modeles.User;
import Views.LoginView;

import java.awt.Window;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LoginController {
	public static boolean authenticate(String cin, String password) throws IOException {
		User user1= UserControllerReader.findUserByCin(cin);
		if(user1==null)
			return false;
		else if(user1.getPassword().equalsIgnoreCase(password))
			return true;
		else
			return false;
    }
	public static void logout(JFrame homeView) {
		if (homeView != null) {
	        homeView.dispose(); // Directly dispose the home view
	    } else {
	        System.out.println("homeView is null");
	    }
	}
}
