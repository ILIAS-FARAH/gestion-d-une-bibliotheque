package Views;

import Modeles.*;
import javax.swing.*;

import Controllers.LoginController;

import java.awt.*;
import java.io.IOException;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JPanel contentPanel;

    public HomeView(User user) throws IOException {
        setTitle("Home");
        setSize(1125, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Images/logo.png")).getImage());
        setLayout(new BorderLayout());

        HeadreView HeadreView1 = new HeadreView(user,this);
        HeadreView1.logoutButton.addActionListener(e -> LoginController.logout(this));
        add(HeadreView1, BorderLayout.NORTH);
           
        TabbedPaneViews tabbedPaneViews1 = new TabbedPaneViews(user);
        add(tabbedPaneViews1, BorderLayout.CENTER);
        setVisible(true);
    }
  
}
