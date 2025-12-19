import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

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

            /**
             * Handle number separately
             */

            if(currentChar>='0' && currentChar<='9'){
                StringBuilder number=new StringBuilder();

                int j=i;

                char currentNumber=fileContent.charAt(j);

                boolean numberContainsDecimalDot=false;
                boolean decimalValueExists=false;

                StringBuilder numbersBeforeDecimalDot=new StringBuilder();

                while(j<fileContent.length() && ((fileContent.charAt(j)>='0' && fileContent.charAt(j)<='9') || fileContent.charAt(j)=='.')){
                    currentNumber=fileContent.charAt(j);

                    if(currentNumber=='.'){
                        numberContainsDecimalDot=true;
                    }

                    if(!numberContainsDecimalDot){
                        numbersBeforeDecimalDot.append(currentNumber);
                    }

                    if(numberContainsDecimalDot && currentNumber>'0'){
                        decimalValueExists=true;
                    }
                    number.append(currentNumber);
                    j++;
                }

                String lexeme=number.toString();

                if(decimalValueExists){
                    int index=number.length()-1;
                    while(number.charAt(index)=='0'){
                        number.deleteCharAt(index);
                        index--;
                    }
                }

                tokenList.add(new Token(TokenType.NUMBER,lexeme,(numberContainsDecimalDot && decimalValueExists)?number.toString():numbersBeforeDecimalDot +".0",lineNumber));

                i=j;
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
                case'"':
                    int j=i+1;
                    StringBuilder stringLiteral=new StringBuilder();

                    boolean stringLiteralEndFlag=false;

                    while(j<fileContent.length()){
                        if(fileContent.charAt(j)=='"'){
                            stringLiteralEndFlag=true;
                            break;
                        }
                        stringLiteral.append(fileContent.charAt(j));
                        j++;
                    }

                    if(stringLiteralEndFlag){
                        tokenList.add(new Token(TokenType.STRING,String.format("\"%s\"", stringLiteral),stringLiteral.toString(),lineNumber));
                    }
                    else{
                        tokenList.add(new Token(TokenType.UNTERMINATED_STRING_ERROR,String.format("\"%s", stringLiteral),stringLiteral.toString(),lineNumber));
                    }

                    i=j+1;
                    continue;
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
                    tokenList.add(new Token(TokenType.UNEXPECTED_CHARACTER_ERROR,String.valueOf(currentChar),null,lineNumber));
            }

            i++;

        }

        return tokenList;
    }

}
