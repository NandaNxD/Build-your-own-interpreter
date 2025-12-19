import java.util.HashMap;
import java.util.Map;

public class ReservedWords {
    static Map<String,TokenType> map=new HashMap();

    static {
        map.put("and",TokenType.AND);
        map.put("class",TokenType.CLASS);
        map.put("else",TokenType.ELSE);
        map.put("false",TokenType.FALSE);
        map.put("for",TokenType.FOR);
        map.put("fun",TokenType.FUN);
        map.put("if",TokenType.IF);
        map.put("nil",TokenType.NIL);
        map.put("or",TokenType.OR);
        map.put("print",TokenType.PRINT);
        map.put("return",TokenType.RETURN);
        map.put("super",TokenType.SUPER);
        map.put("this",TokenType.THIS);
        map.put("true",TokenType.TRUE);
        map.put("var",TokenType.VAR);
        map.put("while",TokenType.WHILE);
    }
    
    

}
