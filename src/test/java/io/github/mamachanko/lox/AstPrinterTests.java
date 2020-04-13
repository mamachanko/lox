package io.github.mamachanko.lox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstPrinterTests {

    @Test
    void prettyPrints() {
        Expr.Binary expr = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123.45)
                ),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(
                        new Expr.Literal(3.1415)
                )
        );

        assertEquals(new AstPrinter().print(expr), "(* (- 123.45) (group 3.1415))");
    }
}
