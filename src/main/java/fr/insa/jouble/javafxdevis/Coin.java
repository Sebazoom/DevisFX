/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.jouble.javafxdevis;

/**
 *
 * @author Amadou Coulibaly
 */
public class Coin {

    @Override
     public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Coin;");
        sb.append(idCoin);
        sb.append(";");
        sb.append(cx);
        sb.append(";");
        sb.append(cy);
        
        return sb.toString();
    }
    // Attributs
    int idCoin;
    double cx;
    double cy;
    // Constructeur
    Coin(int id, double x, double y)
    {
      this.idCoin=id;
      this.cx=x;
      this.cy=y;
    }
    
    void afficher()
    {
        System.out.println(" Coin : id ="+this.idCoin+" abscisse = "+this.cx+ " Ordonnée ="+this.cy);
    }
    
    double getX() {
        return cx;
    }

    double getY() {
        return cy;
    }

    int getID() {
        return idCoin;
    }
    
            
}
