import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    static public List<Token> tokenize(String fileContent) throws Exception {
        int i=0;
        int size=fileContent.length();
        List<Token> tokenList=new ArrayList<>();

        StringBuilder tempToken;

        while(i<size){
            char currentChar=fileContent.charAt(i);

            switch (currentChar){
                case '(':
                    tokenList.add(new Token(TokenType.LEFT_PAREN,String.valueOf(currentChar),null));
                    break;
                case ')':
                    tokenList.add(new Token(TokenType.RIGHT_PAREN,String.valueOf(currentChar),null));
                    break;
                case '{':
                    tokenList.add(new Token(TokenType.LEFT_BRACE,String.valueOf(currentChar),null));
                    break;
                case '}':
                    tokenList.add(new Token(TokenType.RIGHT_BRACE,String.valueOf(currentChar),null));
                    break;
                case ',':
                    tokenList.add(new Token(TokenType.COMMA,String.valueOf(currentChar),null));
                    break;
                case '.':
                    tokenList.add(new Token(TokenType.DOT,String.valueOf(currentChar),null));
                    break;
                case '-':
                    tokenList.add(new Token(TokenType.MINUS,String.valueOf(currentChar),null));
                    break;
                case '+':
                    tokenList.add(new Token(TokenType.PLUS,String.valueOf(currentChar),null));
                    break;
                case ';':
                    tokenList.add(new Token(TokenType.SEMICOLON,String.valueOf(currentChar),null));
                    break;
                case '/':
                    tokenList.add(new Token(TokenType.SLASH,String.valueOf(currentChar),null));
                    break;
                case '*':
                    tokenList.add(new Token(TokenType.STAR,String.valueOf(currentChar),null));
                    break;
                default:
                    throw new Exception("This character isnt supported in token");
            }

            i++;

        }

        return tokenList;
    }
}
