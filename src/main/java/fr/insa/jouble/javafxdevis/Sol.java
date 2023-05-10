/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.jouble.javafxdevis;

import java.util.ArrayList;

/**
 *
 * @author sjoub
 */
public class Sol {
    int idSol;
    ArrayList<Coin> listeCoins;
    ArrayList<Revetements> listeRevetements; 

    public Sol(int idSol, ArrayList<Coin> listeCoins, ArrayList<Revetements> listeRevetements) {
        this.idSol = idSol;
        this.listeCoins = listeCoins;
        this.listeRevetements = listeRevetements;
    }

}
