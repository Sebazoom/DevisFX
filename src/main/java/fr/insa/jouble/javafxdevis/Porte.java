/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.jouble.javafxdevis;


/**
 *
 * @author killi
 */
public class Porte extends Ouverture{
    int idPorte;
    double dimPorteX, dimPorteY; 

    public Porte(int idPorte, double dimPorteX, double dimPorteY, int idOuverture, double dimX, double dimY) {
        super(idOuverture, dimX, dimY);
        this.idPorte = idPorte;
        this.dimPorteX = dimPorteX;
        this.dimPorteY = dimPorteY;
    }

  

    
    }
