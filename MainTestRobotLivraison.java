
import java.util.List;

public class MainTestRobotLivraison {
    public static void main(String[] args) {
        try {
            // Étape 1 : Créer le réseau
            Réseau reseau = new Réseau();
            reseau.add_line("Tunis", "Paris", 4.5);
            reseau.add_line("Paris", "Tokyo", 6.2);
            reseau.add_line("Tunis", "Cairo", 3.0);
            reseau.add_line("Cairo", "Tokyo", 5.5);
            reseau.add_line("Tokyo", "New York", 8.1);
            reseau.add_line("Cairo", "Manilla", 7.2);
            reseau.add_line("Paris", "New York", 7.9);
            reseau.add_line("Manilla", "New York", 4.3);
            
            

            // Étape 2 : Créer le robot
            RobotLivraison robot = new RobotLivraison("EcoBot1", 0, 0);
            robot.recharcher(100); // Recharger à 100%
            robot.demarrer();

            // Étape 3 : Choisir une destination
            String destinationFinale = "New York";
            robot.setDestination(destinationFinale);

            // Étape 4 : Calculer et suivre le chemin
            List<String> chemin = Bellmann.cheminBellman(reseau, "Paris", "New York");
            System.out.println("Chemin calculé : " + chemin);
            for (String ville : chemin) {
            int x = Math.abs(ville.hashCode() % 20);
            int y = Math.abs(ville.hashCode() % 20);
            
            robot.setDestination(ville);
            robot.FaireLivraison(x, y);
            
            }
            // Étape 5 : Affichage de l'état final
            System.out.println("\n--- Résumé ---");
            System.out.println(robot.toString());
            System.out.println("\nHistorique des actions :");
            System.out.println(robot.getHistorique());

        } catch (RobotException e) {
            System.err.println("Erreur Robot : " + e.getMessage());
        }
    }
}