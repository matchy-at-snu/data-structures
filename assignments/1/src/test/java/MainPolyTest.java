import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;

public class MainPolyTest {
    static private final PrintStream stdout = System.out;
    static private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    static private List<String> testCases;


    @BeforeAll
    static void init() {
        System.setOut(new PrintStream(outputStream));
        testCases = IntStream.rangeClosed(1, 9)
                .mapToObj(num -> ("test0" + num))
                .collect(Collectors.toList());
        Assertions.assertArrayEquals(
                new String[]{"test01", "test02", "test03", "test04", "test05", "test06", "test07", "test08", "test09"},
                testCases.toArray()
                );
    }

    @Test
    public void testOnTestCases() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        for (var testcase : testCases) {
            String infile = classLoader.getResource(testcase).getPath();
            String outfile = classLoader.getResource(testcase + ".out").getPath();
            String correct = Files.readString(Path.of(outfile), StandardCharsets.UTF_8).trim();

            MainPoly.main(new String[]{infile});

            Assertions.assertEquals(correct, outputStream.toString().trim());
            outputStream.reset();
        }

    }

    @AfterAll
    static void reset() {
        System.setOut(stdout);
    }
}
