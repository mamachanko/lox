package io.github.mamachanko;

enum TokenType {
    // Single-character tokens
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    COMMA,
    DOT,
    MINUS,
    PLUS,
    SEMICOLON,
    SLASH,
    STAR,

    // Single- or two-character tokens
    BANG,
    BANG_EQUAL,
    EQUAL,
    EQUAL_EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,

    // Literals
    IDENTIFIER,
    STRING,
    NUMBER,

    // Keywords
    PRINT,
    VAR,
    AND,
    OR,
    TRUE,
    FALSE,
    IF,
    ELSE,
    CLASS,
    SUPER,
    THIS,
    FUN,
    RETURN,
    FOR,
    WHILE,
    NIL,

    EOF
}
