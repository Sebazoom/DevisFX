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
    
    public static int NbrMur(String FileName) {
        int nombre = 0;
        try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
                    String ligne = bufferedReader.readLine();
                    while (ligne != null) {
                           String[] morceaux = ligne.split(";");
                           ligne = bufferedReader.readLine();
                            if (morceaux[0].equals("Mur")) {
                                nombre = nombre + 1;
                            }
                    }
            } 
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);}             
        return nombre;    
    } 
    
    public static int NbrSol(String FileName) {
        int nombre = 0;
        try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
                    String ligne = bufferedReader.readLine();
                    while (ligne != null) {
                           String[] morceaux = ligne.split(";");
                           ligne = bufferedReader.readLine();
                            if (morceaux[0].equals("Sol")) {
                                nombre = nombre + 1;
                            }
                    }
            } 
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);}             
        return nombre;    
    }
    
    public static int NbrPlafond(String FileName) {
        int nombre = 0;
        try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
                    String ligne = bufferedReader.readLine();
                    while (ligne != null) {
                           String[] morceaux = ligne.split(";");
                           ligne = bufferedReader.readLine();
                            if (morceaux[0].equals("Plafond")) {
                                nombre = nombre + 1;
                            }
                    }
            } 
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);}             
        return nombre;    
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
    
    
    
     
    public static Coin CreaCoin (int idCoin, String fileName){
        try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                    String ligne = bufferedReader.readLine();

                    while (ligne != null) {

                           String[] morceaux = ligne.split(";");
                           


                            if ((morceaux[0].equals("Coin"))&&(morceaux[1].equals(idCoin))) {
                                Coin c=new Coin(idCoin,Double.parseDouble(morceaux[2]),Double.parseDouble(morceaux[3]));
                                return c;
                            }
                            ligne = bufferedReader.readLine();

                    }

            } 

                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
                Coin C = new Coin(0,0,0);
                System.out.println("Coin" + C.toString());
                return C;
    }

    public static double MontantMurs (int idMur, String FileName) {

        double montant;
        double hsp = 0, surface = 0;
        int idPiece = 0, idAppart = 0, idNiveau = 0, idRevetement = 1;

        // On trouve dans quelle piece est située le mur 
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=3; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(idMur)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Piece") && verif == true) {
                    idPiece = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        // On trouve dans quel appartement est située la pièce
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=2; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(idPiece)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Appartement") && verif == true) {
                    idAppart = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        // On trouve dans quel niveau est situé l'appartement
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=2; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(idAppart)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Niveau") && verif == true) {
                    idNiveau = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");
                ligne = ReaderPiece.readLine();
                if (morceaux[0].equals("Niveau") && morceaux[1].equals(idNiveau)) {
                    hsp = Double.parseDouble(morceaux[2]);
                }
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}



        try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
                String ligne = bufferedReader.readLine();

                while (ligne != null) {
                    String[] morceaux = ligne.split(";");
                    ligne = bufferedReader.readLine();
                    if(morceaux[0].equals("Mur") && morceaux[1].equals(idMur)) {
                        Coin debut = CreaCoin(Integer.parseInt(morceaux[1]), FileName);
                        Coin fin = CreaCoin(Integer.parseInt(morceaux[2]), FileName);
                        Mur mur = new Mur(idMur, debut, fin, Integer.parseInt(morceaux[3]), Integer.parseInt(morceaux[4]), Integer.parseInt(morceaux[5]));
                        idRevetement = Integer.parseInt(morceaux[5]);
                        surface = mur.surface(hsp);
                        System.out.println(surface);
                    }
                }
                montant = surface*LectureRevetement(idRevetement);
                System.out.println(montant);
                return montant;
        }

        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);} 
        return 0;                    
    }


    public static double MontantSol (int idSol, String FileName) {
        double montant = 0;
        double surface = 0;
        int idRevetement = 1;
        ArrayList <Coin> listeCoin = new ArrayList<>();

        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");
                ligne = ReaderPiece.readLine();
                if (morceaux[0].equals("Sol") && morceaux[1].equals(idSol)) {
                    idRevetement = Integer.parseInt(morceaux[morceaux.length-1]);
                    for(int i=0; i<=morceaux.length-2; i++){
                        listeCoin.add(CreaCoin(i,FileName));
                    }
                    Sol sol = new Sol(idSol, listeCoin, idRevetement);
                    surface = sol.surface();
                }
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}

        montant = surface * LectureRevetement(idRevetement);

        return montant;
    }

    public static double MontantPlafond (int idPlafond, String FileName) {
        double montant = 0;
        double surface = 0;
        int idRevetement = 1;
        ArrayList <Coin> listeCoin = new ArrayList<>();

        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");
                ligne = ReaderPiece.readLine();
                if (morceaux[0].equals("Sol") && morceaux[1].equals(idPlafond)) {
                    idRevetement = Integer.parseInt(morceaux[morceaux.length-1]);
                    for(int i=0; i<=morceaux.length-2; i++){
                        listeCoin.add(CreaCoin(i,FileName));
                    }
                    Sol sol = new Sol(idPlafond, listeCoin, idRevetement);
                    surface = sol.surface();
                }
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}

        montant = surface * LectureRevetement(idRevetement);

        return montant;
    }
    
    public static int Fonctiontest(int id, String FileName) {
        double montant;
        double hsp = 0, surface = 0;

        int idPiece = 0;
        int idAppart = 0;
        int idNiveau = 0;
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=3; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(id)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Piece") && verif == true) {
                    idPiece = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        // On trouve dans quel appartement est située la pièce
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=2; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(idPiece)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Appartement") && verif == true) {
                    idAppart = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        // On trouve dans quel niveau est situé l'appartement
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=2; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(idAppart)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Niveau") && verif == true) {
                    idNiveau = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}
        return idNiveau;
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

        
    
    
    
    


}
