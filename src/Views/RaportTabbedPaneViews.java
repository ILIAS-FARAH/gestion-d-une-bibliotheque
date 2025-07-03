package Views;

import javax.swing.*;
import Controllers.ClientControllerReader;
import Controllers.EmpruntControllerReader;
import Controllers.RapportController;
import Controllers.ReturnControllerReader;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class RaportTabbedPaneViews extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;

    public RaportTabbedPaneViews() throws IOException {
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        int nombreClients = ClientControllerReader.readAllClients().size();
        double revenuTotal = ReturnControllerReader.calculerRevenusDuMois();
        int nombreLivresEmpruntes = EmpruntControllerReader.readAllEmprunts().size();
        List<String> clientsEnRetard = EmpruntControllerReader.getClientsEnRetard();

        tabbedPane.addTab("Résumé", createSummaryTab(nombreClients, revenuTotal, nombreLivresEmpruntes));
        tabbedPane.addTab("Clients en Retard", createDelayedClientsTab(clientsEnRetard));

        JPanel buttonPanel = new JPanel();
        JButton downloadButton = new JButton("Download Report");
        downloadButton.setBackground(new Color(77, 144, 255));
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setFocusPainted(false);
        downloadButton.addActionListener(e -> openNotepad()); 
        buttonPanel.add(downloadButton);

        add(buttonPanel, BorderLayout.SOUTH);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createSummaryTab(int nombreClients, double revenuTotal, int nombreLivresEmpruntes) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelTitle = new JLabel("Résumé du Rapport Mensuel");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitle.setAlignmentX(CENTER_ALIGNMENT);

        JLabel labelClients = new JLabel("Nombre de clients : " + nombreClients);
        JLabel labelRevenus = new JLabel("Revenus générés : " + String.format("%.2f", revenuTotal) + " €");
        JLabel labelLivres = new JLabel("Nombre de livres empruntés : " + nombreLivresEmpruntes);

        labelClients.setAlignmentX(CENTER_ALIGNMENT);
        labelRevenus.setAlignmentX(CENTER_ALIGNMENT);
        labelLivres.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(labelTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(labelClients);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(labelRevenus);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(labelLivres);

        return panel;
    }

    private JPanel createDelayedClientsTab(List<String> clientsEnRetard) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel labelTitle = new JLabel("Clients en Retard", JLabel.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(labelTitle, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        if (clientsEnRetard.isEmpty()) {
            textArea.setText("Aucun client en retard.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String client : clientsEnRetard) {
                sb.append(client).append("\n");
            }
            textArea.setText(sb.toString());
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    private void openNotepad() {
        RapportController rapportController = new RapportController();
        rapportController.genererRapportMensuel();

        LocalDate dateCourante = LocalDate.now();
        int anneeCourante = dateCourante.getYear();
        String moisCourant = dateCourante.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String reportFileName = "RAPPORT_" + anneeCourante + "_" + moisCourant.toUpperCase() + ".txt";
        File reportFile = new File(reportFileName);

        if (reportFile.exists()) {
            try {
                String command = "notepad.exe " + reportFile.getAbsolutePath();
                Runtime.getRuntime().exec(command);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening the report file in Notepad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Report not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    
}
