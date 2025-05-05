
import java.util.List;
import java.util.Scanner;


public class RobotLivraison extends RobotConnecte {
    //Attributs
    private int colisActuel;
    private String destination;
    private boolean enLivraison;
    static final int ENERGIE_LIVRAISON=15;
    static final int ENERGIE_CHARGEMENT=5;
    //Methodes
    public RobotLivraison(String id, int x, int y) throws RobotException{
        super(id, x, y, 0, 0);
        this.colisActuel=0;
        this.destination="Tunis";
        this.enLivraison=false;
    }
    @Override
    public void effectuerTache(Réseau reseau) throws RobotException {
    if (!this.enMarche) throw new RobotException("Le robot doit être démarré.");

    Scanner scanner = new Scanner(System.in);
    System.out.println("Entrez la destination (ex: Paris, Tokyo, ...): ");
    String destination = scanner.nextLine();

    if (Ligne.contains(destination) == 0) {
        throw new RobotException("Destination inconnue dans le réseau !");
    }

    List<String> chemin = reseau.plusCourtChemin("Tunis", destination);;
    if (chemin.isEmpty()) {
        throw new RobotException("Aucun chemin trouvé vers " + destination);
    }

    System.out.println("Chemin optimal trouvé : " + chemin);

    for (int i = 1; i < chemin.size(); i++) {
        String from = chemin.get(i - 1);
        String to = chemin.get(i);

        // Trouver la ligne pour calculer le coût
        for (Ligne l : reseau.Tab_line) {
            if (l != null && (
                (l.stdepart.equals(from) && l.starrivee.equals(to)) ||
                (l.stdepart.equals(to) && l.starrivee.equals(from)))) {

                double poids = l.getPoids();
                int energieRequise = (int)(poids * 2); // fictif

                if (!verifierEnergie(energieRequise)) {
                    throw new RobotException("Pas assez d'énergie pour aller de " + from + " à " + to);
                }

                cosommerEnergie(energieRequise);
                ajouterHistorique("Déplacement de " + from + " à " + to + " (énergie consommée: " + energieRequise + ")");
                break;
            }
        }
    }

    
}
    public void FaireLivraison(int destx,int desty) throws RobotException{
        if(verifierEnergie(this.ENERGIE_LIVRAISON)){
            this.deplacer(destx, desty);
            this.cosommerEnergie(this.ENERGIE_LIVRAISON);
            colisActuel=1;
            enLivraison=true;
            this.ajouterHistorique("Livaison Terminee a "+this.destination);
        }
        else throw new RobotException("Echcec de Livraison!");
    }
    @Override
    public void deplacer(int x, int y) throws RobotException{
        double distance = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
        if(distance>100){
                throw new RobotException("Distance tres importante!");
            }
        else{
            int energieNess=(int)(distance/0.3);
            int heures=(int)(distance/10);
            if(verifierEnergie(energieNess)){
                this.cosommerEnergie(energieNess);
                this.x=x;
                this.y=y;
                this.heuresUtilisation=heures;
                this.ajouterHistorique("Robot deplace a ("+x+","+y+")");

            }else throw new RobotException("Echec de Deplacement!");

        }
    }
    public void ChargerColis(String destination) throws RobotException{
        if(enLivraison==false && colisActuel==0 && verifierEnergie(this.ENERGIE_CHARGEMENT)){
            colisActuel=1;
            this.cosommerEnergie(this.ENERGIE_CHARGEMENT);
            this.ajouterHistorique("Colis charge a:"+destination);
        }
        else throw new RobotException("Echec de Chargement!");
    }
    
    @Override
    public String toString() {
        
        return "RobotLivraison [ID : "+this.id+" , Position:("+this.x+","+this.y+"), Énergie: "+this.energie+"%, Heures : "+this.heuresUtilisation+" , Colis: "+this.colisActuel+" , Destination : "+this.destination+" , Connecté : "+this.connecte+" ]";
    }

    public void setDestination(String destination) {
        this.destination=destination;
    }
}

    

    

