public abstract  class RobotConnecte extends Robot implements Connectable {
    //Attributs
    protected boolean connecte;
    protected String reseauConnecte;
    //Methodes
    public RobotConnecte(String id,int x,int y, int energie, int heuresUtilisation) throws RobotException{
        super(id, x, y, energie, heuresUtilisation);
        this.connecte=false;
        this.reseauConnecte=null;
    }
    @Override
    public void connecter(String reseau) throws RobotException{
        if(this.verifierEnergie(5)){
            this.reseauConnecte=reseau;
            this.connecte=true;
            this.cosommerEnergie(5);
            this.ajouterHistorique("Robot connecte au reseau: "+reseau+"!");
        }
        else throw new RobotException("echec de connexion!");
    }
    @Override
    public void deconnecter(){
        this.reseauConnecte=null;
        this.connecte=false;
        this.ajouterHistorique("Robot deconnecte du reseau");
    }
    @Override
    public void envoyerDonnees(String donnees) throws RobotException{
        if(this.connecte){
            if(this.verifierEnergie(3)){
                this.cosommerEnergie(3);
                this.ajouterHistorique("Donnees envoyees: "+donnees);
            }
            
        }
        else
        throw  new RobotException("Echec d'envoie de donnees!");

    }

    
}
