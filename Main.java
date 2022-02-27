//==========================================
//       Title:  Joc del cargòl
//       Author: Oriol Julià
//       Date:   27/02/2022
//==========================================

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Introdueix el número de files i el número de columnes");

        int files = Integer.parseInt(myObj.nextLine());
        int columnes = Integer.parseInt(myObj.nextLine());


        System.out.println("Introdueix el número de cargols en el tauler");
        int cargols = Integer.parseInt(myObj.nextLine());

        Tauler tauler = new Tauler(files, columnes, cargols);

        tauler.generarCargols();
        tauler.generarMenjar();
        boolean end = false;
        while (!end){
            if(!tauler.jocAcabat()) {
                tauler.torn();
            }
            else{
                end = true;
            }
        }
    }
}
