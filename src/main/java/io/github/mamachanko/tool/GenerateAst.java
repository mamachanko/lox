package io.github.mamachanko.tool;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal : Object value",
                "Unary : Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputDir, String baseClassName, List<String> types) throws FileNotFoundException, UnsupportedEncodingException {
        String path = outputDir + "/" + baseClassName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package io.github.mamachanko.lox;");
        writer.println("");
        writer.println("import java.util.List;");
        writer.println("");
        writer.println("abstract class " + baseClassName + " {");

        defineVisitor(writer, baseClassName, types);

        writer.println("    abstract <R> R accept(Visitor<R> visitor);");
        writer.println();

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseClassName, typeName, fields);
        }

        writer.println("}");
        writer.close();
    }

    private static void defineVisitor(PrintWriter writer, String baseClassName, List<String> types) {
        writer.println("    interface Visitor<R> {");

        for (String type: types) {
            String typeName = type.split(": ")[0].trim();
            writer.println("        R visit" + typeName + baseClassName + "(" + typeName + " " + baseClassName.toLowerCase() + ");");
            writer.println();
        }

        writer.println("    }");
        writer.println();
    }

    private static void defineType(PrintWriter writer, String baseClassName, String typeName, String fieldList) {
        // class
        writer.println("    static class " + typeName + " extends " + baseClassName + " {");

        // constructor
        writer.println("        " + typeName + "(" + fieldList + ") {");
        String[] fields = fieldList.split(", ");
        for(String field : fields) {
            String name = field.split(" ")[1];
            writer.println("            this." + name + " = " + name + ";");
        }
        writer.println("        }");

        // visitor pattern
        writer.println();
        writer.println("        @Override");
        writer.println("        <R> R accept(Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + typeName + baseClassName + "(this);");
        writer.println("        }");

        // fields
        writer.println();
        for (String field : fields) {
            writer.println("        final " + field + ";");
        }

        writer.println("    }");
        writer.println();
    }
}
