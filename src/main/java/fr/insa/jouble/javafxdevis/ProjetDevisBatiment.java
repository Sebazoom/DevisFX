/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fr.insa.jouble.javafxdevis;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;
/**
 *
 * @author sjoub
 */
public class ProjetDevisBatiment {
    
    // Obtenir le répertoire de travail actuel de l'application
    static String workingDir = System.getProperty("user.dir");
    // Définir le chemin d'accès relatif du répertoire de sauvegarde
    static String saveDirectory = workingDir + File.separator + "src\\main\\java\\fr\\insa\\jouble\\javafxdevis\\CatalogueRevetement.txt";
    static int idCoin = 1;
    static int idMur = 1;
    static int idPiece = 1;
    static int idSol = 1;
    static int idPlafond = 1;
    static int idEtage = 1;
    
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        
        FileWriter fileWriter = new FileWriter("DevisBatiment.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
  
        
        // On crée une liste pour stocker tous les coins
        ArrayList<Coin> ListeCoin = new ArrayList<>();
        ArrayList<Mur> ListeMur = new ArrayList<>();
       
        double[] prixpieces = new double[250];
        double[] prixmurs = new double[250];
        int nbrpieces,comptpieces, nbrappart, comptappart, nbrniv, comptniv;
        int i = 0; 
        double prixtotalpiece = 0, prixtotalappart = 0, prixtotalniv = 0, prixtotal = 0;
        
        
        // ON DEMANDE LE NBR DE NIVEAUX
        do {
            System.out.println("Combien y a-t-il de niveaux ?");
            nbrniv = Lire.i();
            } while (nbrniv<1);
        // POUR CHAQUE NIVEAU
        for(comptniv=1;comptniv<=nbrniv;comptniv++) {
            // ON DEMANDE LE NBR D'APPARTS
            do {
                System.out.println("Combien y a-t-il d'appartements ?");
                nbrappart = Lire.i();
                } while (nbrappart<1);
            // POUR CHAQUE APPARTEMENT 
            for(comptappart = 1;comptappart<=nbrappart; comptappart++){
                // ON DEMANDE LE NBR DE PIECES
                do {
                System.out.println("Combien y a-t-il de pièces dans l'appartement n°"+comptappart);
                nbrpieces = Lire.i();
                } while (nbrpieces<1);
                // POUR CHAQUE PIECE
                for(comptpieces=1;comptpieces<=nbrpieces;comptpieces++) {

                    // Boucle qui crée les coins et les ajoute à la liste
                    for (int j=1; j<=4; j++){
                        Coin c = DemandeCoin(idCoin);
                        ListeCoin.add(c);  
                    } 
                    // Boucle qui écrit les coins dans le fichier txt
                    for (Coin c : ListeCoin) {
                        bufferedWriter.write(c.idCoin + ";" + c.cx + ";" + c.cy + ";");
                        bufferedWriter.newLine(); // Écrit une nouvelle ligne dans le fichier
                    }
                    // On vide le tampon et on écrit définitivement sur le fichier txt

                    bufferedWriter.newLine();

                    // ON CREE LES MURS DE LA PIECE
                    /*
                    for (int j=1; j<=4; j++) {
                        Mur m = DemandeMur(idMur, ListeCoin);
                        double surface = m.surface();
                        RevetementDispo(1,0,0);
                        System.out.println("Quel revêtement voulez-vous ?");
                        int idRevetement = Lire.i();
                        double prix = LectureRevetement(idRevetement);
                        prixmurs[i] = surface*prix;
                        ListeMur.add(m);
                        i++;
                    }
                    */
                    // ON CALCULE LE PRIX TOTAL DE LA PIECE
                    for (int j=0; j<=3; j++) {
                        prixtotalpiece = prixtotalpiece+prixmurs[j];
                    }

                    // ON INSCRIT LES MURS DANS LE FICHIER TXT
                    for (Mur m : ListeMur) {
                        bufferedWriter.write(m.idMur + ";" + m.debut.idCoin + ";" + m.fin.idCoin + ";");
                        bufferedWriter.newLine(); // Écrit une nouvelle ligne dans le fichier
                    }
                    // ON ENREGISTRE LE PRIX DE LA PIECE N°I EN I-EME POSITION DU TABLEAU POUR CALCULER LE PRIX DE L'APPARTEMENT
                    prixpieces[comptpieces-1] = prixtotalpiece;
                    


                    // Boucle qui écrit les murs dans le fichier txt

                    
                    bufferedWriter.newLine();

                }
            }  
        }
        
        // ON VIDE LE TAMPON ET ON ECRIT DEFINITIVEMENT SUR LE FICHIER TXT
        bufferedWriter.flush();
        bufferedWriter.close();
      
    }
   
   
    
    private static Map<Integer, Double> revetementsCache = new HashMap<>(); // Déclaration d'un Map pour stocker les revêtements lus depuis le fichier

    public static Double LectureRevetement(int recherche) {
        if (revetementsCache.containsKey(recherche)) {
            // Si le revêtement a déjà été lu, on le récupère depuis le cache
            return revetementsCache.get(recherche);
        }
        String fileName = saveDirectory;
        Double prixunitaire = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] morceaux = line.split(";");
                if (morceaux[0].equals(Integer.toString(recherche))) {
                    try {
                        prixunitaire = Double.parseDouble(morceaux[5]);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur : le prix unitaire n'est pas un nombre valide pour le revêtement " + recherche);
                        prixunitaire = null;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        revetementsCache.put(recherche, prixunitaire); // On ajoute le revêtement au cache
        return prixunitaire;
    }
  
    public static Coin DemandeCoin(int id) {
        Coin c;
        double x,y;
        
        System.out.println("L'identifiant du coin que vous créez est : "+idCoin);
        System.out.println("Abscisse du Coin");
        x=Lire.d();
        System.out.println("Ordonnée du Coin");
        y=Lire.d();
        
        c = new Coin(id,x,y);
        idCoin++;
        return c;
    }
    
    
    
    public static double Distance(Coin c1, Coin c2) {
        double area=Math.sqrt((c2.cx-c1.cx)*(c2.cx-c1.cx) + (c2.cy-c1.cy)*(c2.cy-c1.cy));
        return area;
    }
    
    

    public static ArrayList RevetementDispo(boolean mur, boolean sol, boolean plafond) { // m=1 si mur, s=1 si sol, p=1 si plafond
        ArrayList<String> listeMur = new ArrayList<>();
        ArrayList<String> listeSol = new ArrayList<>();
        ArrayList<String> listePlafond = new ArrayList<>();
        
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(saveDirectory));
            String ligne = bufferedReader.readLine();
            
            while (ligne != null) {
                String[] morceaux = ligne.split(";");
                if (morceaux[2].equals("1")) {
                    listeMur.add(ligne);
                }
                if (morceaux[3].equals("1")) {
                    listeSol.add(ligne);
                }
                if (morceaux[4].equals("1")) {
                    listePlafond.add(ligne);
                }
                ligne = bufferedReader.readLine();

            }

        } catch (FileNotFoundException err) {
            System.out.println("Erreur : le fichier n’existe pas!\n " + err);
        } catch (IOException err) {
            System.out.println("Erreur :\n " + err);
        }
        
        if (mur) {
            return listeMur;
        }
        if (sol) {
            return listeSol;
        }
        else {
            return listePlafond;
        }
    }
    
    
    public static double MontantNiveau (int id){ 
                    double montant=0;
                    
            try
                 {
                     
                    BufferedReader devis = new BufferedReader(new FileReader("Devisbat.txt"));
                    String ligne = devis.readLine();
                   
                    while (ligne != null) {
                           
                           String[] listn = ligne.split(";");
                           ligne = devis.readLine();
                            if ((listn[0].equals("Niveau"))&&(listn[1].equals(String.valueOf(id)))) {
                                
                                
                                 
                                 for (int q=3;q<listn.length;q++){
                                 montant = montant + MontantAppart(Integer.parseInt(listn[q]));
                                     }
                                 
                              return montant;   
                            }
                            
                    }
            
                } 
       
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
            return 0;
         }
     public static double MontantAppart (int id){ 
                    double montant=0;
                    
            try
                 {
                     
                    BufferedReader devis = new BufferedReader(new FileReader("Devisbat.txt"));
                    String ligne = devis.readLine();
                   
                    while (ligne != null) {
                           
                           String[] listap = ligne.split(";");
                           ligne = devis.readLine();
                            if ((listap[0].equals("Appartement"))&&(listap[1].equals(String.valueOf(id)))) {
                                
                                
                                 
                                 for (int q=3;q<listap.length;q++){
                                 montant = montant + MontantPiece(Integer.parseInt(listap[q]));
                                     }
                                 
                              return montant;   
                            }
                            
                    }
            
                } 
       
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
            return 0;
         }      
    public static double MontantPiece (int id){ 
                    double montant=0;
                    
            try
                 {
                     
                    BufferedReader devis = new BufferedReader(new FileReader("Devisbat.txt"));
                    String ligne = devis.readLine();
                   
                    while (ligne != null) {
                           
                           String[] listpi = ligne.split(";");
                           ligne = devis.readLine();
                            if ((listpi[0].equals("Piece"))&&(listpi[1].equals(String.valueOf(id)))) {
                                
                                montant=MontantSOL(Integer.parseInt(listpi[2]))+MontantPLAFOND(Integer.parseInt(listpi[3]));
                                 
                                 for (int q=4;q<listpi.length;q++){
                                 montant = montant + MontantMurs(Integer.parseInt(listpi[q]));
                                     }
                                 
                              return montant;   
                            }
                            
                    }
            
                } 
       
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
            return 0;
         }        
            
    public static double MontantMurs (int id){ 
                    ArrayList listeRevetement;
                    listeRevetement = new ArrayList<>();
                    double montant=0;
            try
                 {
                     
                    BufferedReader revetement = new BufferedReader(new FileReader("Devisbat.txt"));
                    String ligne = revetement.readLine();
                   
                    while (ligne != null) {
                           
                           String[] listm = ligne.split(";");
                           ligne = revetement.readLine();
                            if ((listm[0].equals("Mur"))&&(listm[1].equals(String.valueOf(id)))) {
                                for (int h=6;h<listm.length;h++){
                                    listeRevetement.add(listm[h]);
                                //Mur m= new Mur(listRev[1],listRev[0],listRev[0],listRev[0],listRev[0],listRev[0]);
                                }
                                 Mur m=new Mur(Integer.parseInt(listm[1]),Coins(Integer.parseInt(listm[2])),Coins(Integer.parseInt(listm[3])),Integer.parseInt(listm[4]),Integer.parseInt(listm[5]),listeRevetement);
                                
                                 for (int q=6;q<listm.length;q++){
                                 montant=montant+m.montant(Lecture2(Integer.parseInt(listm[q])));
                                //System.out.println("aaa"+Lecture2(Integer.parseInt(listm[q])));
                                     }
                                
                                 
                              return montant;   
                            }
                            
                    }
            
                } 
       
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
            return 0;
         }
          public static double MontantSOL (int id){ 
                    ArrayList listeRevetement;
                    listeRevetement = new ArrayList<>();
                     ArrayList <Coin> listeCoinbis;
                     listeCoinbis = new ArrayList<>();
                     double montant=0;
            try
                 {
                     
                    BufferedReader sols = new BufferedReader(new FileReader("Devisbat.txt"));
                    String ligne = sols.readLine();
                   
                    while (ligne != null) {
                           
                           String[] lists = ligne.split(";");
                           ligne = sols.readLine();
                            if ((lists[0].equals("Sol"))&&(lists[1].equals(String.valueOf(id)))) {
                                for (int h=6;h<lists.length;h++){
                                    listeRevetement.add(lists[h]);
                                }
                                for (int h=2;h<6;h++){
                                 listeCoinbis.add(Coins(Integer.parseInt(lists[h])));
                                 
                                }
                                
                                 Sol s=new Sol(Integer.parseInt(lists[1]),listeCoinbis,listeRevetement);
                                 
                                 for (int q=6;q<lists.length;q++){
                                 montant=montant+s.montant(Lecture2(Integer.parseInt(lists[q])));
                                     }
                                 
                                 
                                 
                              return montant;   
                            }
                            
                    }
            
                } 
       
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
            return 0;
         }
           public static double MontantPLAFOND (int id){ 
                    ArrayList listeRevetement;
                    listeRevetement = new ArrayList<>();
                     ArrayList <Coin> listeCointer;
                     listeCointer = new ArrayList<>();
                     double montant=0;
            try
                 {
                     
                    BufferedReader Pla = new BufferedReader(new FileReader("Devisbat.txt"));
                    String ligne = Pla.readLine();
                   
                    while (ligne != null) {
                           
                           String[] listp = ligne.split(";");
                           ligne = Pla.readLine();
                            if ((listp[0].equals("Plafond"))&&(listp[1].equals(String.valueOf(id)))) {
                                for (int h=7;h<listp.length;h++){
                                    listeRevetement.add(listp[h]);
                                
                                }
                                 for (int h=2;h<6;h++){
                                 listeCointer.add(Coins(Integer.parseInt(listp[h])));
                                }
                                
                                 Plafond p=new Plafond(Integer.parseInt(listp[1]),listeCointer,Integer.parseInt(listp[6]),listeRevetement);
                                 
                                 for (int q=7;q<listp.length;q++){
                                 montant=montant+p.montant(Lecture2(Integer.parseInt(listp[q])));
                                     }
                                 
                              return montant;   
                            }
                            
                    }
            
                } 
       
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
            return 0;
         }
            /*try
                {
                BufferedWriter Devisbat=new BufferedWriter(new FileWriter("Devisbat.txt",false));
                 Devisbat.write("Coins :");
                 Devisbat.newLine();
                for (int t=0;t<A.size(); t++){
                      Devisbat.write(A.get(t).idCoin+";"+A.get(t).cx+";"+A.get(t).cy);
                      Devisbat.newLine();
                  }
                Devisbat.write("Murs :");
                 Devisbat.newLine();
                for (int t=0;t<listeMurtot.size(); t++){
                      Devisbat.write(listeMurtot.get(t).idMur+";"+listeMurtot.get(t).d.idCoin+";"+listeMurtot.get(t).f.idCoin+";"+listeMurtot.get(t).nbrfenetres+";"+listeMurtot.get(t).nbrportes);
                      Devisbat.newLine();
                  }
                Devisbat.write("Sols :");
                 Devisbat.newLine();
                int b=0;
                  for (int t=0;t<listesol.size(); t++){
                      
                      Devisbat.write(listesol.get(t).idSol+";"+listeMurtot.get(b).idMur+";"+listeMurtot.get(b+1).idMur+";"+listeMurtot.get(b+2).idMur+";"+listeMurtot.get(b+3).idMur);
                      Devisbat.newLine();
                      b=b+4;
                  }
                  Devisbat.write("Plafonds :");
                 Devisbat.newLine();
                  b=0;
                  for (int t=0;t<listepla.size(); t++){
                      Devisbat.write(listepla.get(t).idPlafond+";"+listeMurtot.get(b).idMur+";"+listeMurtot.get(b+1).idMur+";"+listeMurtot.get(b+2).idMur+";"+listeMurtot.get(b+3).idMur);
                      Devisbat.newLine();
                      b=b+4;
                  }
                  
                
                    Devisbat.close();
                }
                catch (IOException err)
                {System.out.println("Erreur :\n"+err);}*/

    public static Coin Coins (int id){
        
        try
                 {
                     
                    BufferedReader Coins = new BufferedReader(new FileReader("Devisbat.txt"));
                    String ligne = Coins.readLine();
                   
                    while (ligne != null) {
                           
                           String[] listC = ligne.split(";");
                           ligne = Coins.readLine();
                            if ((listC[0].equals("Coin"))&&(listC[1].equals(String.valueOf(id)))) {
                                Coin c=new Coin(Integer.parseInt(listC[1]),Double.parseDouble(listC[2]),Double.parseDouble(listC[3]));
                             return c;
                            }
                            
                    }
            
                } 
       
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
        Coin C = new Coin(0,0,0);
        return C;
    }
    
    
    
    
    


}
