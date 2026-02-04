package compilateurnewversion;

import java.io.File;
import java.util.*;

import parserexemple.Categorie;
import parserexemple.Scanner2;
import parserexemple.UniteLexicale;

public class Main {
    public static void main(String[] args) {
        System.out.println("*********************** Analyse Lexicale *************************");

        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Entrez le chemin du fichier (ou appuyez sur Entrée pour saisir manuellement) : ");
        String filePath = inputScanner.nextLine().trim();

        Scanner2 scanner;
        
    
        if (filePath.isEmpty()) {
            scanner = new Scanner2(); 
        } else {
            File file = new File(filePath);
            if (file.exists()) {
                scanner = new Scanner2(filePath); 
            } else {
                System.out.println("Chemin de fichier invalide. Passage à la saisie manuelle.");
                scanner = new Scanner2(); 
            }
        }

        List<String> tokensList = new ArrayList<>();
        UniteLexicale token;
        do {
            token = scanner.lexemeSuivant();

            String tokenCategory = token.getCategorie().toString();
            
            if (tokenCategory.equals("opprel")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("oppaff")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("lbrace")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("rbrace")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("lparen")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("rparen")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("pv")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("plus")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("minus")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("mult")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("div")) {
                tokensList.add(token.getLexeme());
            } else if (tokenCategory.equals("eof")) {
                tokensList.add("$");
            } else {
                tokensList.add(tokenCategory);
            }

            System.out.println(token.toString());
        } while (token.getCategorie() != Categorie.EOF);

        inputScanner.close();

        String[] ch = tokensList.toArray(new String[0]);

        System.out.println(tokensList);

        System.out.println("*********************** Analyse Syntaxique *************************");
        parsernew parser = new parsernew();
        parser.ch = ch;  
        parser.analyzeSL();  
    }
}
