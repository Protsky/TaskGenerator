package com.example;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {


        long startTime = System.nanoTime();
        Scanner scanner = new Scanner (System.in);

        System.out.println("Inserisci il percorso del file.cup oppure file.csv");
        String inputFile = scanner.nextLine();
        System.out.println("Cosa vorresti calcolare?");
        System.out.println("1. Tutti i triangoli possibili di km < 500");
        System.out.println("2. Tutti i triangoli possibili di km > 500 ");
        int risposta = scanner.nextInt();

        scanner.close();
        
    

        Utils utils = new Utils();
        utils.importCupFile(inputFile); 
        utils.calculateCombinationForGroupOf3(); //Users/gionatadonati/Programmazione/Java/Privato/GenerateTask/generatetask/src/main/java/com/example/csv.csv
        if(risposta == 1){

            utils.calculateTriangleLees500();
            utils.printFinale();

        }
        else if(risposta == 2){

            utils.calculateTriangleMore500();
            utils.printFinale();

        }
        else{
            System.out.println("Risposta non valida");
        }
        
        
        System.out.println("Terminato");

        
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Temmpo impiegato " + totalTime + " milliseconds");
      
        
    }

}
