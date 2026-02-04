package parserexemple;

public class UniteLexicale {
    private Categorie categorie;
    private Object lexeme;

    public UniteLexicale(Categorie categorie, String lexeme) {
        this.categorie=categorie;
        this.lexeme=lexeme;
    }

    public Categorie getCategorie() {
        return categorie;
    }
    public String getLexeme() {
        return (String) lexeme;  
    }
    public String toString() {
        return "<"+categorie.toString()+","+lexeme+">";
    }
}