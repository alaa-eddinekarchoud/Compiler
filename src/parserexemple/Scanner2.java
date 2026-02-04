package parserexemple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Scanner2 {
    private ArrayList<Character> fluxCaracteres;
    private int indiceCourant;
    private char caractereCourant;
    private boolean eof;

    public Scanner2() {
        this("");
    }

    public Scanner2(String nomFich) {
        BufferedReader f = null;
        int car = 0;
        fluxCaracteres = new ArrayList<Character>();
        indiceCourant = 0;
        eof = false;
        try {
            f = new BufferedReader(new FileReader(nomFich));
        } catch (IOException e) {
            System.out.println("Taper votre texte ci-dessous (ctrl+z pour finir)");
            f = new BufferedReader(new InputStreamReader(System.in));
        }

        try {
            while ((car = f.read()) != -1)
                fluxCaracteres.add((char) car);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void caractereSuivant() {
        if (indiceCourant < fluxCaracteres.size())
            caractereCourant = fluxCaracteres.get(indiceCourant++);
        else
            eof = true;
    }

    public void reculer() {
        if (indiceCourant > 0)
            indiceCourant--;
    }

    public UniteLexicale lexemeSuivant() {
        caractereSuivant();

        while (eof || Character.isWhitespace(caractereCourant)) {
            if (eof)
                return new UniteLexicale(Categorie.EOF, "0");
            caractereSuivant();
        }

        if (Character.isLetter(caractereCourant))
            return getID();

        if (Character.isDigit(caractereCourant))
            return getNombre();

        if (caractereCourant == ';')
            return new UniteLexicale(Categorie.PV, ";");

        if (caractereCourant == '<')
            return new UniteLexicale(Categorie.OPPRel, "<");

        if (caractereCourant == '>')
            return new UniteLexicale(Categorie.OPPRel, ">");

        if (caractereCourant == '=')
            return new UniteLexicale(Categorie.OPPAff, "=");

        if (caractereCourant == '+')
            return new UniteLexicale(Categorie.PLUS, "+");

        if (caractereCourant == '-')
            return new UniteLexicale(Categorie.MINUS, "-");

        if (caractereCourant == '*')
            return new UniteLexicale(Categorie.MULT, "*");

        if (caractereCourant == '/')
            return new UniteLexicale(Categorie.DIV, "/");

        if (caractereCourant == '{')
            return new UniteLexicale(Categorie.LBRACE, "{");

        if (caractereCourant == '}')
            return new UniteLexicale(Categorie.RBRACE, "}");

        if (caractereCourant == '(')
            return new UniteLexicale(Categorie.LPAREN, "(");

        if (caractereCourant == ')')
            return new UniteLexicale(Categorie.RPAREN, ")");

        return null;
    }

    public UniteLexicale getID() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        while (true) {
            switch (etat) {
                case 0:
                    etat = 1;
                    sb.append(caractereCourant);  
                    break;
                case 1:
                    caractereSuivant();
                    if (eof) {
                        etat = 3;
                    } else if (Character.isLetterOrDigit(caractereCourant)) {
                        sb.append(caractereCourant);  
                    } else {
                        etat = 2;  
                    }
                    break;
                case 2:
                    reculer();  
                    String identifier = sb.toString();

                    switch (identifier) {
                        case "if":
                            return new UniteLexicale(Categorie.IF, identifier); 
                        case "then":
                            return new UniteLexicale(Categorie.THEN, identifier);  
                        case "else":
                            return new UniteLexicale(Categorie.ELSE, identifier);  
                        case "while":
                            return new UniteLexicale(Categorie.WHILE, identifier);  
                        case "do":
                            return new UniteLexicale(Categorie.DO, identifier); 
                        default:
                            return new UniteLexicale(Categorie.ID, identifier);  
                    }
                case 3:
                    return new UniteLexicale(Categorie.ID, sb.toString());  
            }
        }
    }



    public UniteLexicale getNombre() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        while (true) {
            switch (etat) {
                case 0:
                    etat = 1;
                    sb.append(caractereCourant);
                    break;
                case 1:
                    caractereSuivant();
                    if (eof)
                        etat = 3;
                    else if (Character.isDigit(caractereCourant))
                        sb.append(caractereCourant);
                    else
                        etat = 2;
                    break;
                case 2:
                    reculer();
                    return new UniteLexicale(Categorie.NUM, sb.toString());
                case 3:
                    return new UniteLexicale(Categorie.NUM, sb.toString());
            }
        }
    }


    @Override
    public String toString() {
        return fluxCaracteres.toString();
    }
}
