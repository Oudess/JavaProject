
import java.util.ArrayList;

public abstract class Robot {
    //Attributs 
    protected String id;
    protected int x,y;
    protected int energie;
    protected int heuresUtilisation;
    protected boolean enMarche;
    protected ArrayList<String> historiqueActions=new ArrayList<String>();
    protected int nhist=0;
    //La visiblite la plus appropriee est protected pour que les classes qui heritent de robot auront acces a ses attributs
    //Methodes
    public Robot(String id,int x,int y, int energie, int heuresUtilisation) throws RobotException{
        
        
        if(energie<=100 && energie>=0){
            this.energie=energie;
            ajouterHistorique("Robot Crée");
            enMarche=false;
            this.id=id;
            this.x=x;
            this.y=y;
        }
        else
        throw new RobotException("Energie est en pourcent %");
        
        

    }
    public void ajouterHistorique(String action){
        historiqueActions.add(action);
        nhist++;
    }
    public boolean verifierEnergie(int energieRequise) throws EnergieInsuffisanteException{
        if (this.energie>=energieRequise)
        return true;
        else
        throw new EnergieInsuffisanteException();
    }
    public boolean verifierMaintenance() throws MaintenanceRequiseException{
        if (heuresUtilisation<200)
        return true;
        else 
        throw new MaintenanceRequiseException();
    }
    public void demarrer() throws RobotException{
        if (this.verifierEnergie(10)){
            this.enMarche=true;
            this.ajouterHistorique("Robot demarré");
        }
        else{
            throw new RobotException("Echec de demarrage du Robot!");
        }
    }
    public void arreter()
    {
        this.enMarche=false;
        this.ajouterHistorique("Robot arreté");
    }
    public void cosommerEnergie(int quantity) throws EnergieInsuffisanteException{
        if (this.energie>quantity){
        this.ajouterHistorique("Energie conseommé: "+quantity+" %");
        this.energie-=quantity;}
        else{
            throw new EnergieInsuffisanteException();
        }
    }
    public void recharger(int quantity){
        if (this.energie+quantity<=100){
            this.ajouterHistorique("Energie rechargé: "+quantity+" %");
            this.energie+=quantity;
        }else{
            this.ajouterHistorique("Energie rechargé: "+(100-this.energie)+" %");
            this.energie=100;
        }
    }
    public abstract void deplacer(int x,int y) throws RobotException;
    public abstract ArrayList<String> effectuerTache(Réseau reseau,String destination) throws RobotException;
    public String getHistorique(){
        String Historique=String.join("\n",this.historiqueActions);
        return Historique;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "RobotIndustriel [ID : "+this.id+", Position : ("+this.x+","+this.y+"), Énergie : "+this.energie+"%, Heures : "+this.heuresUtilisation+"]";
    }
    public boolean getEtat(){
        return this.enMarche;
    }




}