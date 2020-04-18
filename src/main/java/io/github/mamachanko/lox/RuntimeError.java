package io.github.mamachanko.lox;

class RuntimeError extends RuntimeException {
    final Token token;

    public RuntimeError(Token token, String errorMessage) {
        super(errorMessage);
        this.token = token;
    }
}
