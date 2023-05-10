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
public class Plafond {
   int idPlafond;
   ArrayList<Coin> listeCoins;
   ArrayList<Revetements> listeRevetements; 

   Plafond(int idPlafond, ArrayList<Coin> listeCoins, ArrayList<Revetements> listeRevetements) {
        this.idPlafond = idPlafond;
        this.listeCoins = listeCoins;
        this.listeRevetements = listeRevetements;
    }
   
   
   
}
