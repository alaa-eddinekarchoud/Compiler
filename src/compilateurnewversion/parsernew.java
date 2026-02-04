package compilateurnewversion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class parsernew {

    public String[] LRGS = {
            "Program'->Program",
            "Program->StmtList",
            "StmtList->StmtList Stmt",
            "StmtList->Stmt",
            "Stmt->id = Expr ;",
            "Stmt->if Condition then Stmt else Stmt",
            "Stmt->while Condition do Stmt",
            "Stmt->{ StmtList }",
            "Expr->Expr + Term",
            "Expr->Expr - Term",
            "Expr->Term",
            "Term->Term * Factor",
            "Term->Term / Factor",
            "Term->Factor",
            "Factor->( Expr )",
            "Factor->id",
            "Factor->num",
            "Condition->Expr < Expr",
            "Condition->Expr > Expr"
    };

    public String[][] tableSLR = {
            { "etat/VT", "id", "=", ";", "if", "then", "else", "while", "do", "{", "}", "+", "-", "*", "/", "(", ")",
                    "num", "<", ">", "$", "Program'", "Program", "StmtList", "Stmt", "Expr", "Term", "Factor",
                    "Condition" },
            { "0", "s4", "err", "err", "s5", "err", "err", "s6", "err", "s7", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "1", "2", "3", "err", "err", "err", "err" },
            { "1", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "acc", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "2", "s4", "err", "err", "s5", "err", "err", "s6", "err", "s7", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "r1", "err", "err", "err", "8", "err", "err", "err", "err" },
            { "3", "r3", "err", "err", "r3", "err", "err", "r3", "err", "r3", "r3", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "r3", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "4", "err", "s9", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "5", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "11", "12", "13", "10" },
            { "6", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "11", "12", "13", "17" },
            { "7", "s4", "err", "err", "s5", "err", "err", "s6", "err", "s7", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "18", "3", "err", "err", "err", "err" },
            { "8", "r2", "err", "err", "r2", "err", "err", "r2", "err", "r2", "r2", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "r2", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "9", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "19", "12", "13", "err" },
            { "10", "err", "err", "err", "err", "s20", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "11", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "s23", "s24", "err", "err",
                    "err", "err", "err", "s21", "s22", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "12", "err", "err", "r10", "err", "r10", "err", "err", "r10", "err", "err", "r10", "r10", "s25", "s26",
                    "err", "r10", "err", "r10", "r10", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "13", "err", "err", "r13", "err", "r13", "err", "err", "r13", "err", "err", "r13", "r13", "r13", "r13",
                    "err", "r13", "err", "r13", "r13", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "14", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "27", "12", "13", "err" },
            { "15", "err", "err", "r15", "err", "r15", "err", "err", "r15", "err", "err", "r15", "r15", "r15", "r15",
                    "err", "r15", "err", "r15", "r15", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "16", "err", "err", "r16", "err", "r16", "err", "err", "r16", "err", "err", "r16", "r16", "r16", "r16",
                    "err", "r16", "err", "r16", "r16", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "17", "err", "err", "err", "err", "err", "err", "err", "s28", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "18", "s4", "err", "err", "s5", "err", "err", "s6", "err", "s7", "s29", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "8", "err", "err", "err", "err" },
            { "19", "err", "err", "s30", "err", "err", "err", "err", "err", "err", "err", "s23", "s24", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "20", "s4", "err", "err", "s5", "err", "err", "s6", "err", "s7", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "31", "err", "err", "err", "err" },
            { "21", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "32", "12", "13", "err" },
            { "22", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "33", "12", "13", "err" },
            { "23", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "13", "34", "13", "err" },
            { "24", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "13", "35", "13", "err" },
            { "25", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "err", "err", "36", "err" },
            { "26", "s15", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err",
                    "s14", "err", "s16", "err", "err", "err", "err", "err", "err", "err", "err", "err", "37", "err" },
            { "27", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "s23", "s24", "err", "err",
                    "err", "s38", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "28", "s4", "err", "err", "s5", "err", "err", "s6", "err", "s7", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "39", "err", "err", "err", "err" },
            { "29", "r7", "err", "err", "r7", "err", "r7", "r7", "err", "r7", "r7", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "r7", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "30", "r4", "err", "err", "r4", "err", "r4", "r4", "err", "r4", "r4", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "r4", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "31", "err", "err", "err", "err", "err", "s40", "err", "err", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "32", "err", "err", "err", "err", "r17", "err", "err", "r17", "err", "err", "s23", "s24", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "33", "err", "err", "err", "err", "r18", "err", "err", "r18", "err", "err", "s23", "s24", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "34", "err", "err", "r8", "err", "r8", "err", "err", "r8", "err", "err", "r8", "r8", "s25", "s26", "err",
                    "r8", "err", "r8", "r8", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "35", "err", "err", "r9", "err", "r9", "err", "err", "r9", "err", "err", "r9", "r9", "s25", "s26", "err",
                    "r9", "err", "r9", "r9", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "37", "err", "err", "r11", "err", "r11", "err", "err", "r12", "err", "err", "r11", "r11", "r11", "r11",
                    "err", "r11", "err", "r11", "r11", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "36", "err", "err", "r12", "err", "r12", "err", "err", "r11", "err", "err", "r12", "r12", "r12", "r12",
                    "err", "r12", "err", "r12", "r12", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "38", "err", "err", "r14", "err", "r14", "err", "err", "r14", "err", "err", "r14", "r14", "r14", "r14",
                    "err", "r14", "err", "r14", "r14", "err", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "39", "r6", "err", "err", "r6", "err", "r6", "r6", "err", "r6", "r6", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "r6", "err", "err", "err", "err", "err", "err", "err", "err" },
            { "40", "s4", "err", "err", "s5", "err", "err", "s6", "err", "s7", "err", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "err", "err", "err", "err", "41", "err", "err", "err", "err" },
            { "41", "r5", "err", "err", "r5", "err", "r5", "r5", "err", "r5", "r5", "err", "err", "err", "err", "err",
                    "err", "err", "err", "err", "r5", "err", "err", "err", "err", "err", "err", "err", "err" }
    };

    public Stack<String> analyse = new Stack<>();
    String[] ch = {};

    public String action = "";
    int index = 0;

    public void analyzeSL() {
        action = "";
        index = 0;
        analyse.push("0");

        System.out.println("********Pile     Entrée   Action********");
        this.displaySLR();

        while (index < ch.length) {
            String s = analyse.peek();
            String act = Action(s, ch[index]);

            if (act.charAt(0) == 's') {
                analyse.push(ch[index]);
                analyse.push(act.substring(1));
                index++;
                action = "shift";
                displaySLR();
            } else if (act.charAt(0) == 'r') {
                int ruleIndex = Integer.parseInt(act.substring(1));
                String[] ruleParts = LRGS[ruleIndex].split("->");
                String left = ruleParts[0].trim();
                String[] rightTokens = ruleParts[1].trim().split("\\s+");

                int tokensToPop = rightTokens.length;

                if (analyse.size() >= tokensToPop * 2) {

                    for (int i = 0; i < tokensToPop * 2; i++) {
                        analyse.pop();
                    }

                    String topState = analyse.peek();
                    analyse.push(left);

                    String nextState = Action(topState, left);
                    if (nextState.equals("err")) {
                        System.out.println("Analyse SLR échouée : Aucune transition valide pour" + topState + " -> " + left);
                        break;
                    }
                    analyse.push(nextState);

                    action = "reduce: " + LRGS[ruleIndex];
                    displaySLR();
                } else {
                    System.out.println("Analyse SLR échouée : Pas assez d'éléments dans la pile pour la réduction");
                    break;
                }
            }

            else if (act.equals("acc")) {
                System.out.println("Analyse SLR réussie");
                break;
            } else {
                System.out.println("Analyse SLR échouée");
                break;
            }
        }
    }

    public String Action(String s, String a) {
        for (int i = 0; i < 43; i++) {
            if (tableSLR[i][0].equals(s)) {
                for (int j = 1; j < 29; j++) {
                    if (tableSLR[0][j].equals(a)) {
                        return tableSLR[i][j];
                    }
                }
            }
        }
        return "err";
    }

    public void displaySLR() {
        String stackDisplay = "---";
        String inputDisplay = "---";
        int stackSize = analyse.size();
        int stackHalfSize = stackSize / 2;

        for (int i = 0; i < stackHalfSize; i++) {
            stackDisplay = stackDisplay + "----";
        }

        for (int i = 0; i < ch.length; i++) {
            inputDisplay = inputDisplay + "---";
        }

        String strInput = "";
        for (int i = index; i < ch.length; i++) {
            strInput = strInput + ch[i];
        }

        String output = analyse + inputDisplay + strInput + stackDisplay + action + "\n";

        System.out.print(output);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
            writer.write(output);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        parsernew parser = new parsernew();
        parser.analyzeSL();
    }
}