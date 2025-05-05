public class Ligne{
    public static String[] Tab_station=new String[10];
    public static int nbstation=0;
    public String stdepart;
    public String starrivee;
    private double poids;
     public static int contains(String S)
     {
        for(int i=0;i<nbstation;i++)
        {
            if(Ligne.Tab_station[i].equals(S))
            return 1;
        }
        return 0;
     }
    
    
    public Ligne(String stdepart,String starrivee,double poids){
        this.stdepart=stdepart;
        this.starrivee=starrivee;
        this.poids=poids;
        if (Ligne.contains(starrivee)==1)
        {
           if(Ligne.contains(stdepart)==0)
           {Tab_station[nbstation]=stdepart;
            nbstation++;}

        }
        else if (Ligne.contains(stdepart)==1)
        {
           if(Ligne.contains(starrivee)==0)
           {Tab_station[nbstation]=starrivee;
            nbstation++;}

        }
        else{
            Tab_station[nbstation]=stdepart;
            nbstation++;
            Tab_station[nbstation]=starrivee;
            nbstation++;
        }

    }
    public double getPoids(){return poids;}
    public void setPoids(double poids){this.poids=poids;}
    public String toString()
    {
        int r=(int)((poids*100)%100);
        if (r>=60)
        r=r/10;

    
    return("Pour aller de la station "+stdepart+" a la station "+starrivee+" compter "+(int)poids+" minutes et "+r+" secondes.");

    }



    
}