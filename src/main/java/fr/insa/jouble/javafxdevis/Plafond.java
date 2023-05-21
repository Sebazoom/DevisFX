/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.jouble.javafxdevis;
import java.util.ArrayList;

/**
 *
 * @author killi
 */
public class Plafond extends Sol {
   
    public Plafond(int idPlafond, ArrayList<Coin> listeCoin, ArrayList<Mur> listeMur) {
        super(idPlafond, listeCoin, listeMur);
    
    }  
    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
        sb.append("Plafond{");
        sb.append("idPlafond=").append(idSol);
        sb.append(", id des Coins= {");
        for(int i=0; i<=listeCoin.size()-1; i++){
            sb.append(listeCoin.get(i).getID());
            sb.append(";");
        }
        sb.append('}');
        sb.append(", surface=");
        sb.append(this.surface());
        sb.append('}');
        return sb.toString(); 
    } 
}
