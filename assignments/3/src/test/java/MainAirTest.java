import org.junit.jupiter.api.*;

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
    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";

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

    @BeforeEach
    void redirectOutput() {
        System.setOut(new PrintStream(outputStream));
    }

    void runTestCase(String[] testCase, String tickets) throws IOException {
        outputStream.reset();
        ClassLoader classLoader = getClass().getClassLoader();

        String airports = Objects.requireNonNull(classLoader.getResource(testCase[0])).getPath();
        String flights = Objects.requireNonNull(classLoader.getResource(testCase[1])).getPath();
        String schedules = Objects.requireNonNull(classLoader.getResource(testCase[2])).getPath();
        String outfile = Objects.requireNonNull(classLoader.getResource(tickets)).getPath();
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
                stdout.println(ANSI_GREEN + expectedLine + ANSI_RESET);
                stdout.println(ANSI_PURPLE + actualLine + ANSI_RESET);
            } else {
                assertEquals(expectedLine, actualLine);
            }
        }
        outputStream.reset();
    }

    @Test
    void testHawaiianCustomers10() throws IOException {
        String[] testCase = testCases[0];
        String tickets = output.get(0);

        runTestCase(testCase, tickets);

        outputStream.reset();
    }

    @Test
    void testHawaiianCustomers100() throws IOException {
        String[] testCase = testCases[1];
        String tickets = output.get(1);

        runTestCase(testCase, tickets);
        outputStream.reset();
    }

    @Test
    void testHawaiianCustomersBogus() throws IOException {
        String[] testCase = testCases[2];
        String tickets = output.get(2);

        runTestCase(testCase, tickets);
        outputStream.reset();
    }

    @Test
    void testHawaiianFlightSingleCustomers100() throws IOException {
        String[] testCase = testCases[3];
        String tickets = output.get(3);

        runTestCase(testCase, tickets);
        outputStream.reset();
    }

    @Test
    void testAlaskaCustomers200() throws IOException {
        String[] testCase = testCases[4];
        String tickets = output.get(4);

        runTestCase(testCase, tickets);
        outputStream.reset();
    }

    @Test
    void testAlaskaCustomers2000() throws IOException {
        String[] testCase = testCases[5];
        String tickets = output.get(5);

        runTestCase(testCase, tickets);
        outputStream.reset();
    }

    @Test
    void testAlaskaCustomers20000() throws IOException {
        String[] testCase = testCases[6];
        String tickets = output.get(6);

        runTestCase(testCase, tickets);
        outputStream.reset();
    }

    @AfterEach
    void recoverOutput() {
        System.setOut(stdout);
    }
}