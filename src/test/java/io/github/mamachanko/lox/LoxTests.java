package io.github.mamachanko.lox;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

class LoxTests {

    @Test
    void expression() throws IOException {
        Lox.main(new String[]{getResourcePath("expression-test.lox")});
    }

    private String getResourcePath(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        return new File(resource.getPath()).getAbsolutePath();
    }

}
