package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class GenerateAST {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }

        /**
         *  It can be {./src/main/java/ + package name}
         */
        String outputDir = args[0];


        defineAST(outputDir, "Expr", Arrays.asList(
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right"
        ));

    }

    private static void defineAST(String outputDir, String className, List<String> types) throws IOException {
        String path = outputDir + className + ".java";
        PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);

        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + className + "{");

        defineVisitor(writer, className, types);
        writer.println();
        writer.println("    abstract <R> R accept(Visitor<R> visitor);");
        writer.println();

        for (String type : types) {
            String innerClassName = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            String[] fieldsSplitList = fields.split(",");
            writer.println("    static class " + innerClassName+" extends "+className + "{");
            writer.println();

            for (String field : fieldsSplitList) {
                writer.println("        final " + field.trim() + ";");
            }

            writer.println();
            writer.println("        @Override");
            writer.println("        <R> R accept(Visitor<R> visitor) {");
            writer.println("            return visitor.visit" +
                    innerClassName + className + "(this);");
            writer.println("        }");
            writer.println();

            writer.println("        public " + innerClassName + "(" + fields + ")" + "{");

            for (String field : fieldsSplitList) {
                String fieldVariableName = field.trim().split(" ")[1];
                writer.println("            this." + fieldVariableName + "=" + fieldVariableName + ";");
            }
            writer.println("        }");
            writer.println("    }");
            writer.println();
        }

        writer.println("}");
        writer.close();


    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("    interface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("        R visit" + typeName + baseName + "(" +
                    typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println("    }");
    }
}
