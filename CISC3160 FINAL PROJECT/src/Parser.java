import java.util.*;

public class Parser {
        private Lexer lexer;
        private Token currentToken;
        private Map<String, Integer> variable;

    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    public Lexer getLexer() {
        return lexer;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    Parser(Lexer lexer) {
            this.lexer = lexer;
            this.currentToken = lexer.nextToken();
            this.variable = new HashMap<>();
        }

        private void take(Token.Type type) {
            if (currentToken.type == type) {
                currentToken = lexer.nextToken();
            } else {
                error("Expected token " + type + " but got " + currentToken.type);
            }
        }

        private void error(String msg) {
            throw new RuntimeException(msg);
        }

        private void expect(Token.Type type) {
            if (currentToken.type != type) {
                error("Expected token " + type + " but got " + currentToken.type);
            }
        }

        void parseProgram() {
            while (currentToken.type != Token.Type.EOF) {
                parseAssignment();
            }
        }

        private void parseAssignment() {
            expect(Token.Type.IDENTIFIER);
            Token id = currentToken;
            take(Token.Type.IDENTIFIER);
            take(Token.Type.ASSIGN);
            int val =parseExp();
            take(Token.Type.SEMICOLON);
            variable.put(String.valueOf(id), val);
        }

        private int parseExp() {
            int val = parseTerm();
            Token.Type op = currentToken.type;
            while (currentToken.type == Token.Type.PLUS || currentToken.type == Token.Type.MINUS) {
                take(currentToken.type);
                int pt = parseTerm();
                if (op == Token.Type.PLUS) {
                    val += pt;
                } else if(op == Token.Type.MINUS){
                    val -= pt;
                }
            }
           // System.out.println("Print val in parseEXP");
            //System.out.println(val);
            return val;
        }

        private int parseTerm() {
           int val =  parseFact();
           Token.Type op = currentToken.type;
            while (currentToken.type == Token.Type.MULT ||currentToken.type == Token.Type.DIV) {
                take(Token.Type.MULT);
                int pf = parseFact();
                if (op == Token.Type.MULT) {
                    val *= pf;
                } else if(op == Token.Type.DIV) {
                    val -= pf;
                }
            }
            return val;
        }


    private int parseFact() {
        int value = 0;
        switch (currentToken.type) {
            case LPAREN:
                take(Token.Type.LPAREN);
                value = parseExp();
                take(Token.Type.RPAREN);
                break;
            case PLUS:
                take(Token.Type.PLUS);
                value = parseFact();  // Evaluate the fact after the unary plus
                break;
            case MINUS:
                take(Token.Type.MINUS);
                value = -parseFact();  // Negate the result of the fact after the unary minus
                break;
            case LITERAL:
                value = Integer.parseInt(currentToken.value);
                take(Token.Type.LITERAL);
                break;
            case IDENTIFIER:
                String identifier = currentToken.value;
                if (!variable.containsKey(identifier)) {
                    error("Uninitialized variable: " + identifier);
                }
                value = variable.get(identifier);
                take(Token.Type.IDENTIFIER);
                break;
            default:
                error("Unexpected token: " + currentToken.type);
                break;
        }
        return value;
    }

    public Map<String, Integer> getVariable() {
            return variable;
    }
}


