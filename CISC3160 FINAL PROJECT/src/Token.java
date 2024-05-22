
/*The Token class defines the types of tokens and stores the token's type and value.*/
public class Token {
    // using enumeration to define different types
        enum Type {
        IDENTIFIER, //Ex. A-Z a-z
        LITERAL, //Ex.
        PLUS, //Ex. +
        MINUS, //Ex. -
        MULT,  //Ex.*
        DIV,  //Ex. /
        ASSIGN, //Ex. =
        SEMICOLON, //Ex. ;
        LPAREN, //Ex. (
        RPAREN, //Ex. )
        EOF //Ex. end of file
        }

        Type type;
        String value;
        //Token takes in two variables Type and its value
        Token(Type type, String value) {
            this.type = type;
            this.value = value;
        }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
//Error problem fixed, output produces value /
    public String toString() {
            return getValue();
        }
}

