import java.util.regex.*;

public  class Lexer {
    private static final Pattern token_Pat = Pattern.compile(
            "\\s*(\\d+|[a-zA-Z_][a-zA-Z_0-9]*|\\+|\\-|\\*|=|;|\\(|\\))\\s*");
    private Matcher match; //The matcher obj matches
    private String input;
    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    Lexer(String input) {
        this.input = input;
        this.match = token_Pat.matcher(input);
        this.pos = 0;
    }

    Token nextToken() {
        if (pos >= input.length()) {
            return new Token(Token.Type.EOF, "");
        }
        if (match.find(pos) && match.start() == pos) {
            String token = match.group(1);
            pos = match.end();

            switch (token) {
                case "+":
                    return new Token(Token.Type.PLUS, token);
                case "-":
                    return new Token(Token.Type.MINUS, token);
                case "*":
                    return new Token(Token.Type.MULT, token);
                case "=":
                    return new Token(Token.Type.ASSIGN, token);
                case ";":
                    return new Token(Token.Type.SEMICOLON, token);
                case "(":
                    return new Token(Token.Type.LPAREN, token);
                case ")":
                    return new Token(Token.Type.RPAREN, token);
                default:
                    if (token.matches("\\d+")) {
                        if (token.matches("0\\d+")) {
                            throw new RuntimeException("Error: Leading zeros are not allowed in literals");
                        }
                        return new Token(Token.Type.LITERAL, token);
                    } else if (token.matches("[_a-zA-Z][_a-zA-Z0-9]*")) {
                        return new Token(Token.Type.IDENTIFIER, token);
                    } else {
                        throw new RuntimeException("Unexpected token: " + token);
                    }
            }
        }
        throw new RuntimeException("Unexpected character: " + input.charAt(pos));
    }



}

