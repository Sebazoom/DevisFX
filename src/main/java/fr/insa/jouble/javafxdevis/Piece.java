package fr.insa.jouble.javafxdevis;
import java.util.ArrayList;
/**
 *
 * @author sjoub
 */
public class Piece {
    int idPiece, idSol, idPlafond;
    ArrayList<Coin> listeCoin;
    ArrayList<Mur> listeMur;

    Piece(int id, ArrayList<Mur> listeMur,int idSol, int idPlafond) {
        this.idPiece = id;
        this.listeMur = listeMur; 
        this.idSol = idSol;
        this.idPlafond = idPlafond;
        
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Piece;");
        sb.append(idPiece);
        sb.append(";");
        sb.append(idSol);
        sb.append(";");
        sb.append(idPlafond);
        sb.append(";");
        for(int i=0; i<=listeMur.size()-1; i++){
            sb.append(listeMur.get(i).getID());
            sb.append(";");
        }
        return sb.toString();
    }
   
}
