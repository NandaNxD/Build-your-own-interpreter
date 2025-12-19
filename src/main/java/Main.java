import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.System.err;
import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Usage: ./your_program.sh tokenize <filename>");
            System.exit(1);
        }

        String command = args[0];
        String filename = args[1];

        if (!command.equals("tokenize")) {
            System.err.println("Unknown command: " + command);
            System.exit(1);
        }

        String fileContents = "";
        try {
            fileContents = Files.readString(Path.of(filename));
            List<Token> tokenList = Tokenizer.tokenize(fileContents);
            boolean error=false;

            for (Token token:tokenList){
                if(token.getType()==TokenType.UNEXPECTED_CHARACTER_ERROR){
                    error=true;
                    err.printf("[line %d] Error: Unexpected character: %s\n",token.getLine(),token.getLexeme());
                }
                else if(token.getType()==TokenType.UNTERMINATED_STRING_ERROR){
                    error=true;
                    err.printf("[line %d] Error: Unterminated string.\n",token.getLine());
                }
                else{
                    out.println(token);
                }

            }
            out.println("EOF  null");

            if(error){
                System.exit(65);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }

    }
}
