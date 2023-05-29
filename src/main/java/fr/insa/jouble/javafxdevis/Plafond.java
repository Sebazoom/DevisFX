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
   int IdRevetement;
    public Plafond(int idPlafond, ArrayList<Coin> listeCoin, int idRevetement) {
        super(idPlafond, listeCoin, idRevetement);
        this.IdRevetement = idRevetement;
    }  
    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
        sb.append("Plafond;");
        sb.append(idSol);
        sb.append(";");
        for(int i=0; i<=listeCoin.size()-1; i++){
            sb.append(listeCoin.get(i).getID());
            sb.append(";");
        }
        sb.append(IdRevetement);
        return sb.toString(); 
    } 
}
