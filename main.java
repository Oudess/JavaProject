import java.util.List;

public class main {
    public static void main(String[] args) throws RobotException {
        Réseau reseau = new Réseau();
        reseau.add_line("New York", "Tokyo", 4.8);
        reseau.add_line("New York", "Manilla", 2);
        reseau.add_line("Tokyo", "Paris", 10.12);
        reseau.add_line("Tokyo", "Manilla", 5);
        reseau.add_line("Manilla", "Cairo", 3.58);
        reseau.add_line("Cairo", "Paris", 4);
        reseau.add_line("Paris", "Tunis", 11);

List<String> chemin = Bellmann.cheminBellman(reseau, "Tunis", "New York");
System.out.println(chemin);
    }
    
}
