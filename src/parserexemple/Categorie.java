package parserexemple;

public enum Categorie {
    EOF,       // End of File
    PV,        // ; (semicolon)
    OPPAff,    // = (assignment operator)
    OPPRel,    // <, >,  (relational operators)
    ID,        // Identifier
    NUM,       // Numeric 
    IF,        // if 
    THEN,      // then 
    ELSE,      // else 
    WHILE,     // while 
    DO,        // do 
    LBRACE,    // { (left brace)
    RBRACE,    // } (right brace)
    LPAREN,    // ( (left parenthesis)
    RPAREN,    // ) (right parenthesis)
    PLUS,      // + (addition operator)
    MINUS,     // - (subtraction operator)
    MULT,      // * (multiplication operator)
    DIV        // / (division operator)
    ;

    public static final int MIN = ID.ordinal();  
    public static final int MAX = DIV.ordinal(); 

    public String toString() {
        return this.name().toLowerCase();
    }

    public static Categorie toCategorie(String s) {
        for (Categorie c : Categorie.values())
            if (c.toString().equalsIgnoreCase(s))
                return c;
        return null;
    }

    public boolean estTerminal() {
        return ordinal() >= MIN && ordinal() <= MAX;
    }

    public boolean estNonTerminal() {
        return ordinal() > MAX;
    }
}
