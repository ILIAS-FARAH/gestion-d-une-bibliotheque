package Controllers;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import Modeles.Client;

public class RapportController {

    public void genererRapportMensuel() {
        try {
            int nombreClients = ClientControllerReader.readAllClients().size();
            double revenuTotal = ReturnControllerReader.calculerRevenusDuMois();
            int nombreLivresEmpruntes = EmpruntControllerReader.readAllEmprunts().size();
            List<String> clientsEnRetard = EmpruntControllerReader.getClientsEnRetard();

            LocalDate dateCourante = LocalDate.now();
            int anneeCourante = dateCourante.getYear();
            String moisCourant = dateCourante.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String nomFichier = "RAPPORT_" + anneeCourante + "_" + moisCourant.toUpperCase() + ".txt";

            StringBuilder contenu = new StringBuilder();
            contenu.append("===== RAPPORT MENSUEL =====\n");
            contenu.append("Mois : ").append(moisCourant).append(" ").append(anneeCourante).append("\n");
            contenu.append("Nombre de clients : ").append(nombreClients).append("\n");
            contenu.append("Revenus générés : ").append(String.format("%.2f", revenuTotal)).append(" €\n");
            contenu.append("Nombre de livres empruntés : ").append(nombreLivresEmpruntes).append("\n");

            if (!clientsEnRetard.isEmpty()) {
                contenu.append("===== Détails des retards =====\n");
                for (String nomClient : clientsEnRetard) {
                    Client client = ClientControllerReader.findClientByName(nomClient);
                    if (client != null) {
                        contenu.append("- ").append(client.getName())
                               .append(" (Email : ").append(client.getEmail())
                               .append(")\n");
                    } else {
                        contenu.append("- ").append(nomClient).append(" (Client non trouvé)\n");
                    }
                }
            }
            contenu.append("=============================\n");

            // Specify the path where the report file is saved
            File fichier = new File(nomFichier);
            if (!fichier.exists()) {
                fichier.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true))) {
                writer.write(contenu.toString());
                writer.newLine();
            }

            System.out.println("Rapport généré avec succès : " + nomFichier);

        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du rapport : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
        }
    }
}
