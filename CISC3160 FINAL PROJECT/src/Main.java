import java.util.*;
public class Main {
    public static void main(String[] args) {
        //test cases//
        String[] inputs = {
                "x = 001;",
                "x_2 = 0;",
                "x = 0\ny = x;\nz = ---(x+y);",
                "x = 1;\ny = 2;\nz = ---(x+y)*(x+-y);"
        };
            /* Advance for loop that takes in test cases and begins the process
            * interpreting the language */
        for (String input : inputs) {
            System.out.println("Input:\n" + input);
            try {
                Lexer lex = new Lexer(input);
                Parser par = new Parser(lex);
                par.parseProgram();
                System.out.println("Output:");
               Map<String, Integer> variables = par.getVariable();
               variables.forEach((key, value) -> System.out.println(key + " = " + value));

            } catch (RuntimeException e) {
                System.out.println("Output:\nerror");

            }
            System.out.println();
        }
    }
}
