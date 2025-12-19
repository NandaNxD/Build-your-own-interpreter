import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokenizer {

    static public List<Token> tokenize(String fileContent) throws Exception {
        int i=0;
        int size=fileContent.length();
        List<Token> tokenList=new ArrayList<>();

        Map<Integer,Integer> newLineIndexMap=new HashMap<>();

        StringBuilder temp=null;

        int lineNumber=1;

        boolean commentStarted=false;

        while(i<size){
            char currentChar=fileContent.charAt(i);

            if(currentChar=='\n'){
                commentStarted=false;
            }

            if(commentStarted){
                i++;
                continue;
            }

            switch (currentChar){
                case ' ', '\t':
                    break;
                case '(':
                    tokenList.add(new Token(TokenType.LEFT_PAREN,String.valueOf(currentChar),null,lineNumber));
                    break;
                case ')':
                    tokenList.add(new Token(TokenType.RIGHT_PAREN,String.valueOf(currentChar),null,lineNumber));
                    break;
                case '{':
                    tokenList.add(new Token(TokenType.LEFT_BRACE,String.valueOf(currentChar),null,lineNumber));
                    break;
                case '}':
                    tokenList.add(new Token(TokenType.RIGHT_BRACE,String.valueOf(currentChar),null,lineNumber));
                    break;
                case ',':
                    tokenList.add(new Token(TokenType.COMMA,String.valueOf(currentChar),null,lineNumber));
                    break;
                case '.':
                    tokenList.add(new Token(TokenType.DOT,String.valueOf(currentChar),null,lineNumber));
                    break;
                case '-':
                    tokenList.add(new Token(TokenType.MINUS,String.valueOf(currentChar),null,lineNumber));
                    break;
                case '+':
                    tokenList.add(new Token(TokenType.PLUS,String.valueOf(currentChar),null,lineNumber));
                    break;
                case ';':
                    tokenList.add(new Token(TokenType.SEMICOLON,String.valueOf(currentChar),null,lineNumber));
                    break;
                case '/':
                    if(fileContent.length()>i+1 && fileContent.charAt(i+1)=='/'){
                        commentStarted=true;
                    }
                    else{
                        tokenList.add(new Token(TokenType.SLASH,String.valueOf(currentChar),null,lineNumber));
                    }
                    break;
                case '*':
                    tokenList.add(new Token(TokenType.STAR,String.valueOf(currentChar),null,lineNumber));
                    break;
                case '<':
                    if(fileContent.length()>i+1 && fileContent.charAt(i+1)=='='){
                        tokenList.add(new Token(TokenType.LESS_EQUAL,"<=", null,lineNumber));
                        i++;
                    }
                    else{
                        tokenList.add(new Token(TokenType.LESS,String.valueOf(currentChar),null,lineNumber));
                    }
                    break;
                case '>':
                    if(fileContent.length()>i+1 && fileContent.charAt(i+1)=='='){
                        tokenList.add(new Token(TokenType.GREATER_EQUAL,">=", null,lineNumber));
                        i++;
                    }
                    else{
                        tokenList.add(new Token(TokenType.GREATER,String.valueOf(currentChar),null,lineNumber));
                    }
                    break;
                case '!':
                    if(fileContent.length()>i+1 && fileContent.charAt(i+1)=='='){
                        tokenList.add(new Token(TokenType.BANG_EQUAL,"!=", null,lineNumber));
                        i++;
                    }
                    else{
                        tokenList.add(new Token(TokenType.BANG,String.valueOf(currentChar),null,lineNumber));
                    }
                    break;
                case '=':
                    if(fileContent.length()>i+1 && fileContent.charAt(i+1)=='='){
                        tokenList.add(new Token(TokenType.EQUAL_EQUAL,"==", null,lineNumber));
                        i++;
                    }
                    else{
                        tokenList.add(new Token(TokenType.EQUAL,String.valueOf(currentChar),null,lineNumber));
                    }
                    break;
                case '\n':
                    lineNumber++;
                    break;
                default:
                    tokenList.add(new Token(TokenType.ERROR,String.valueOf(currentChar),null,lineNumber));
            }

            i++;

        }

        return tokenList;
    }

}
