package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import Modeles.User;
import javax.swing.JLabel;

public class TabbedPaneViews extends JPanel {

	
	private static final long serialVersionUID = 1L;

	public TabbedPaneViews(User user) throws IOException {

		setLayout(new BorderLayout());
        setBackground(new Color(200, 221, 242));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(200, 221, 242));

        
        BookTabbedPaneViews BookTabbedPaneViews1 = new BookTabbedPaneViews();
        tabbedPane.addTab("Books", BookTabbedPaneViews1);
        ClientTabbedPaneViews ClientTabbedPaneViews1 =new ClientTabbedPaneViews();
        tabbedPane.addTab("Clients", ClientTabbedPaneViews1);
        EmpruntTabbedPaneViews EmpruntTabbedPaneViews1=new EmpruntTabbedPaneViews();
        tabbedPane.addTab("Loans", EmpruntTabbedPaneViews1);
        ReturnTabbedPaneViews ReturnTabbedPaneViews1= new ReturnTabbedPaneViews();
        tabbedPane.addTab("Return", ReturnTabbedPaneViews1);
        
        if(user.getAdmin()==1) {
        UserTabbedPaneViews UserTabbedPaneViews1 = new UserTabbedPaneViews();
        tabbedPane.addTab("Users", UserTabbedPaneViews1);
        RaportTabbedPaneViews RaportTabbedPaneViews1= new RaportTabbedPaneViews();
        tabbedPane.addTab("Reports",RaportTabbedPaneViews1);
        }
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTabPanel(String content) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 63, 65));  // Set background color
        panel.add(new JLabel(content));  // Add content label to the panel
        return panel;
    }
}
