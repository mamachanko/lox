package io.github.mamachanko.lox;

import java.util.List;

import static io.github.mamachanko.lox.TokenType.BANG;
import static io.github.mamachanko.lox.TokenType.BANG_EQUAL;
import static io.github.mamachanko.lox.TokenType.EOF;
import static io.github.mamachanko.lox.TokenType.EQUAL_EQUAL;
import static io.github.mamachanko.lox.TokenType.FALSE;
import static io.github.mamachanko.lox.TokenType.GREATER;
import static io.github.mamachanko.lox.TokenType.LEFT_PAREN;
import static io.github.mamachanko.lox.TokenType.LESS;
import static io.github.mamachanko.lox.TokenType.LESS_EQUAL;
import static io.github.mamachanko.lox.TokenType.MINUS;
import static io.github.mamachanko.lox.TokenType.NIL;
import static io.github.mamachanko.lox.TokenType.NUMBER;
import static io.github.mamachanko.lox.TokenType.PLUS;
import static io.github.mamachanko.lox.TokenType.RIGHT_PAREN;
import static io.github.mamachanko.lox.TokenType.SEMICOLON;
import static io.github.mamachanko.lox.TokenType.SLASH;
import static io.github.mamachanko.lox.TokenType.STAR;
import static io.github.mamachanko.lox.TokenType.STRING;
import static io.github.mamachanko.lox.TokenType.TRUE;

public class Parser {
    private static class ParseError extends RuntimeException { }

    private List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Expr parse() {
        try {
            return expression();
        } catch (ParseError error) {
            return null;
        }
    }

    private Expr expression() {
        return equality();
    }

    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr comparison() {
        Expr expr = addition();

        while (match(GREATER, TokenType.GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = addition();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr addition() {
        Expr expr = multiplication();

        while (match(PLUS, MINUS)) {
            Token operator = previous();
            Expr right = multiplication();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr multiplication() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = multiplication();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }

    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }

        throw error(peek(), "Expect expression.");
    }

    private boolean match(TokenType... types) {
        for (TokenType tokenType : types) {
            if (check(tokenType)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(TokenType type, String errorMessage) {
        if (check(type)) return advance();

        throw error(peek(), errorMessage);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private ParseError error(Token token, String errorMessage) {
        Lox.error(token, errorMessage);
        return new ParseError();
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            advance();
        }
    }

    private Token peek() {
        return tokens.get(current);
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }
}
