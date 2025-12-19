public class Token {
    /**
     * <token_type> <lexeme> <literal>
     */

    final TokenType type;
    final String lexeme;
    final Object literal;

    public Token(TokenType type, String lexeme, Object literal) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
