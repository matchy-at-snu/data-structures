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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class MainAirTest {
    static private final PrintStream stdout = System.out;
    static private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    static private final String[][] testCases = {
            {"Hawaiian-airports.txt", "Hawaiian-flights.txt", "Hawaiian-customers10.txt"},
            {"Hawaiian-airports.txt", "Hawaiian-flights.txt", "Hawaiian-customers100.txt"},
            {"Hawaiian-airports.txt", "Hawaiian-flights.txt", "Hawaiian-customers-bogus.txt"},
            {"Hawaiian-airports.txt", "Hawaiian-flights-singles.txt", "Hawaiian-customers100.txt"},
            {"Alaska-airports.txt", "Alaska-flights.txt", "Alaska-customers200.txt"},
            {"Alaska-airports.txt", "Alaska-flights.txt", "Alaska-customers2000.txt"},
            {"Alaska-airports.txt", "Alaska-flights.txt", "Alaska-customers20000.txt"}
    };
    static private final List<String> output = IntStream.rangeClosed(1, 9)
            .mapToObj(num -> ("GPU2/run1/tickets0" + num + ".out"))
            .collect(Collectors.toList());

    @BeforeAll
    static void redirectOutput() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void mainTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Iterator<String[]> itTestCase = Arrays.stream(testCases).iterator();
        Iterator<String> itOutput = output.iterator();

        while (itTestCase.hasNext() && itOutput.hasNext()) {
            String[] testCase = itTestCase.next();

            String airports = Objects.requireNonNull(classLoader.getResource(testCase[0])).getPath();
            String flights = Objects.requireNonNull(classLoader.getResource(testCase[1])).getPath();
            String schedules = Objects.requireNonNull(classLoader.getResource(testCase[2])).getPath();
            String outfile = Objects.requireNonNull(classLoader.getResource(itOutput.next())).getPath();
            List<String> expectedOutput = Files.readAllLines(Path.of(outfile), StandardCharsets.UTF_8);

            MainAir.main(new String[]{airports, flights, schedules});
            String[] actualOutput = outputStream.toString().split("\\n");

            Iterator<String> itExpected = expectedOutput.iterator();
            Iterator<String> itActual = Arrays.stream(actualOutput).iterator();

            while (itExpected.hasNext() && itActual.hasNext()) {
                String expectedLine = itExpected.next();
                String actualLine = itActual.next();
                if (expectedLine.startsWith("Elapsed") ||
                    expectedLine.startsWith("Memory") ||
                    expectedLine.startsWith("Total CPU time")
                ) {
                    stdout.println(expectedLine + "\t" + actualLine);
                } else {
//                    stdout.println(expectedLine + "\t" + actualLine);
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