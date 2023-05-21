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
        String fileName = "C:\\Users\\sjoub\\Documents\\NetBeansProjects\\DevisBatiment\\src\\main\\java\\fr\\insa\\jouble\\projetdevisbatiment\\CatalogueRevetement.txt";
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
    
    
    
    public static Mur DemandeMur(int id, List ListeCoin) {
        System.out.println("Quel est l'id du coin de début du mur ?");
        int idd = Lire.i();
        int idf;
        Coin coindebut, coinfin;
        do {
            System.out.println("Quel est l'id du coin de fin du mur ?");
            idf = Lire.i();
        } while (idd == idf);
        coindebut = (Coin) ListeCoin.get(idd-1);
        coinfin = (Coin) ListeCoin.get(idf-1);
        Mur m = new Mur(idMur, coindebut, coinfin);
        idMur++;

        return m;
    }
    
    

    public static ArrayList RevetementDispo(boolean mur, boolean sol, boolean plafond) { // m=1 si mur, s=1 si sol, p=1 si plafond
        ArrayList<String> listeMur = new ArrayList<>();
        ArrayList<String> listeSol = new ArrayList<>();
        ArrayList<String> listePlafond = new ArrayList<>();
        
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\sjoub\\Documents\\NetBeansProjects\\DevisFX\\src\\main\\java\\fr\\insa\\jouble\\javafxdevis\\CatalogueRevetement.txt"));
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


}
