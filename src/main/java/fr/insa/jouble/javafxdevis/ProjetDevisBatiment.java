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
       
       
        
        int k = 1, nbrpieces;
        do {
        System.out.println("Combien y a-t-il de pièces dans l'appartement n°"+k);
        nbrpieces = Lire.i();
        } while (nbrpieces<1);
        
        for(k=1;k<=nbrpieces;k++) {
               
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

            double[] prixmurs = new double[250];
            int i = 0; 
            double prixtotal;
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

            for (Mur m : ListeMur) {
                bufferedWriter.write(m.idMur + ";" + m.debut.idCoin + ";" + m.fin.idCoin + ";");
                bufferedWriter.newLine(); // Écrit une nouvelle ligne dans le fichier
            }

            Piece p = DemandePiece(ListeMur);


            // Boucle qui écrit les murs dans le fichier txt

            bufferedWriter.write(p.idPiece + ";" + p.listesMur);
            bufferedWriter.newLine();
        
        }
            
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
    
    public static Piece DemandePiece(ArrayList ListeMur) {
        Piece p;
        p = new Piece(idPiece, idSol, idPlafond, ListeMur);
        return p;
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
    
    

    public static void RevetementDispo (int m, int s, int p){//m=1 si mur, s=1 si sol, p=1 si plafond
        
        System.out.println("Voici les revetements disponibles pour cette surface :");
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\sjoub\\Documents\\NetBeansProjects\\DevisBatiment\\src\\main\\java\\fr\\insa\\jouble\\projetdevisbatiment\\CatalogueRevetement.txt"));
            String ligne = bufferedReader.readLine();
            
            while (ligne != null) {
                String[] morceaux = ligne.split(";");
                ligne = bufferedReader.readLine();
                if (morceaux[2].equals(String.valueOf(m)) && (morceaux[2].equals("1"))) {
                    System.out.println(morceaux[0] + ";" + morceaux[1]+ ";" + morceaux[5]);
                }
                if (morceaux[3].equals(String.valueOf(s))&& (morceaux[3].equals("1"))) {
                   System.out.println(morceaux[0] + ";" + morceaux[1]+ ";" + morceaux[5]);
                }
                if (morceaux[4].equals(String.valueOf(p))&& (morceaux[4].equals("1"))) {
                    System.out.println(morceaux[0] + ";" + morceaux[1]+ ";" + morceaux[5]);
                }
                
            }
            
        } 
       
        catch(FileNotFoundException err){
               System.out.println( "Erreur : le fichier n’existe pas!\n "+err);}
        catch (IOException err){
            System.out.println(" Erreur :\n "+err);}
    }


}
