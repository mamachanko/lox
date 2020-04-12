package io.github.mamachanko.lox;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class LoxTests {

    private File testSourceFile = new File(getClass().getClassLoader().getResource("test.lox").getPath());

    @Test
    void itLox() throws IOException {
        Lox.main(new String[]{testSourceFile.getAbsolutePath()});
    }

}
