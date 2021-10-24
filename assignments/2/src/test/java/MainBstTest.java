import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MainBstTest {
    static private final PrintStream stdout = System.out;
    static private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    static private final String[][] testCases = {
            {"10words.txt", "10words.txt"},
            {"1000words.txt", "2000words.txt"},
            {"1000words.txt", "2000words2.txt"},
            {"sawyer.txt", "sawyer.txt"},
            {"sawyer.txt", "mohicans.txt"},
            {"mohicans.txt", "mohicans.txt"},
            {"mohicans.txt", "sawyer.txt"}
    };
    static private final String[] output = {
            "10words.out", "1000words.out", "1000words2.out",
            "sawyer.out", "sawyer-mohicans.out", "mohicans.out", "mohicans-sawyer.out"
    };

    @BeforeAll
    static void redirectOutput() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void mainTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Iterator<String[]> itTestCase = Arrays.stream(testCases).iterator();
        Iterator<String> itOutput = Arrays.stream(output).iterator();

        while (itTestCase.hasNext() && itOutput.hasNext()) {
            String[] testCase = itTestCase.next();

            String trainFile = Objects.requireNonNull(classLoader.getResource(testCase[0])).getPath();
            String probeFile = Objects.requireNonNull(classLoader.getResource(testCase[1])).getPath();
            String outfile = Objects.requireNonNull(classLoader.getResource(itOutput.next())).getPath();
            List<String> expectedOutput = Files.readAllLines(Path.of(outfile), StandardCharsets.UTF_8);

            MainBst.main(new String[]{ trainFile, probeFile });
            String[] actualOutput = outputStream.toString().split("\\n");

            Iterator<String> itExpected = expectedOutput.iterator();
            Iterator<String> itActual = Arrays.stream(actualOutput).iterator();

            while (itExpected.hasNext() && itActual.hasNext()) {
                String expectedLine = itExpected.next();
                String actualLine = itActual.next();
                if (expectedLine.startsWith("CPU") || expectedLine.startsWith("Memory")) {
                    stdout.println(expectedLine + "\t" + actualLine);
                } else {
                    stdout.println(expectedLine + "\t" + actualLine);
                    assertEquals(expectedLine, actualLine);
                }
            }

            outputStream.reset();
        }
    }

    @AfterAll
    static void recoverOutput() {
        System.setOut(stdout);
    }
}