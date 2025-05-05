import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bellmann {
    public static List<String> cheminBellman(Réseau r, String origine, String destination) {
    int n = Ligne.nbstation;
    double[] dist = new double[n];
    String[] pred = new String[n];

    Arrays.fill(dist, Double.MAX_VALUE);
    dist[r.index(origine)] = 0;

    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < r.nbline; j++) {
            Ligne l = r.Tab_line[j];
            int u = r.index(l.stdepart);
            int v = r.index(l.starrivee);
            if (dist[u] != Double.MAX_VALUE && dist[u] + l.getPoids() < dist[v]) {
                dist[v] = dist[u] + l.getPoids();
                pred[v] = l.stdepart;
            }
        }
    }

    List<String> chemin = new ArrayList<>();
    String courant = destination;
    while (courant != null) {
        chemin.add(0, courant); // ajoute en tête pour inverser
        courant = pred[r.index(courant)];
    }

    return chemin;
}
}
