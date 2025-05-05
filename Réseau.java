import java.util.ArrayList;
import java.util.List;

public class Réseau{
    public Ligne[] Tab_line;
    public int nbline;

    public Réseau(){
        Tab_line=new Ligne[10];
        nbline=0;
    }
    public Réseau(Ligne[] T)
    {
        Tab_line=T;
        int i=0;
        while(Tab_line[i]!=null)
        {
            nbline++;
            i++;
        }
    }
    public void add_line(String stdepart,String starrivee,double poids)
    {
        Ligne L=new Ligne(stdepart,starrivee,poids);
        Tab_line[nbline]=L;
        nbline++;
    }
    public String[] adjascence(String N)
    {

        String[] Tab=new String[10];
        int k=0;
        for(int i=0;i<nbline;i++)
        {
            if (Tab_line[i].stdepart.compareTo(N)==0)
            {
                Tab[k]=Tab_line[i].starrivee;
                k++;
            }
            if (Tab_line[i].starrivee.compareTo(N)==0)
            {
                Tab[k]=Tab_line[i].stdepart;
                k++;
            }
        }
        return Tab;
    }
    public int index(String S)
    {
        for (int i=0;i<Ligne.nbstation;i++)
        {
            if (Ligne.Tab_station[i].equals(S))
            return i;
        }
        return -1;
    }
    public List<String> plusCourtChemin(String depart, String arrivee) {
    int n = Ligne.nbstation;
    double[] dist = new double[n];
    String[] pred = new String[n];

    for (int i = 0; i < n; i++) {
        dist[i] = Double.MAX_VALUE;
        pred[i] = null;
    }

    dist[index(depart)] = 0;

    // Relaxation des arêtes
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < nbline; j++) {
            int u = index(Tab_line[j].stdepart);
            int v = index(Tab_line[j].starrivee);
            double poids = Tab_line[j].getPoids();

            if (dist[u] + poids < dist[v]) {
                dist[v] = dist[u] + poids;
                pred[v] = Tab_line[j].stdepart;
            }

            // Vérification dans l’autre sens (graph non orienté)
            if (dist[v] + poids < dist[u]) {
                dist[u] = dist[v] + poids;
                pred[u] = Tab_line[j].starrivee;
            }
        }
    }

    // Reconstruction du chemin
    List<String> chemin = new ArrayList<>();
    String current = arrivee;
    while (current != null && !current.equals(depart)) {
        chemin.add(0, current);
        current = pred[index(current)];
    }
    if (current != null) chemin.add(0, depart);
    return chemin;
}


}
