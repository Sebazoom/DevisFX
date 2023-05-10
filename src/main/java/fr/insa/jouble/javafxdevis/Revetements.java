/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.jouble.javafxdevis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


 /**
 *
 * @author killi
 */
public class Revetements{
    int idRevetement;
    String designation;
    int pourMur,pourSol,pourPlafond;
    double prixUnitaire;
    
    

    public Revetements(int idRevetement, String designation, int pourMur, int pourSol, int pourPlafond, double prixUnitaire) {
        this.idRevetement = idRevetement;
        this.designation = designation;
        this.pourMur = pourMur;
        this.pourSol = pourSol;
        this.pourPlafond = pourPlafond;
        this.prixUnitaire = prixUnitaire;
    }
    
    double montantRevetement(double surface, double prixunitaire){
        return(surface*prixunitaire);
    }
    







    
        
    
}
