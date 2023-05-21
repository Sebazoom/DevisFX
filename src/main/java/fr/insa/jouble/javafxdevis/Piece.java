package fr.insa.jouble.javafxdevis;
import java.util.ArrayList;
/**
 *
 * @author sjoub
 */
public class Piece {
    int idPiece;
    ArrayList<Coin> listeCoin;
    ArrayList<Mur> listeMur;
    ArrayList<Plafond> listePlafond;
    ArrayList<Sol> listeSol;

    Piece(int id, ArrayList<Coin> listeCoin, ArrayList<Mur> listeMur, ArrayList<Plafond> listePlafond,ArrayList<Sol> listeSol) {
        this.idPiece = id;
        this.listeCoin = listeCoin;
        this.listeMur = listeMur;
        this.listeSol = listeSol;
        this.listePlafond = listePlafond;
        
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Piece{");
        sb.append("idPiece=").append(idPiece);
        sb.append(", id des Coins= {");
        for(int i=0; i<=listeCoin.size()-1; i++){
            sb.append(listeCoin.get(i).getID());
            sb.append(";");
        }
        sb.append('}');
        sb.append(", id des Murs= {");
        for(int i=0; i<=listeMur.size()-1; i++){
            sb.append(listeMur.get(i).getID());
            sb.append(";");
        }
        sb.append('}');
        sb.append(", id du Sol=").append(idPiece);
        sb.append(", id du Plafond=").append(idPiece);
        sb.append('}');
        return sb.toString();
    }
   
}
