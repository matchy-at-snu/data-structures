import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import util.PrincetonBST;

class BSTTest {

    private static final Lorem lorem = LoremIpsum.getInstance();

    private static final PrincetonBST princetonBST = new PrincetonBST();
    private static final BST myBST = new BST();

    @BeforeAll
    static void initialize() {
        String[] words = lorem.getWords(10000).split("\\s+");
        for (var word : words) {
            princetonBST.put(word, word);
            myBST.insert(word);
        }
    }

    @Test
    void size() {
        assertEquals(princetonBST.size(), myBST.size());
    }

    @Test
    void insert() {
        assertEquals(princetonBST.keys().toString().trim(),
                String.join(" ", myBST.keys()));
    }

    @Test
    void find() {
        String[] probes = lorem.getWords(100).split(" ");
        for (var probe : probes) {
            assertEquals(princetonBST.contains(probe), myBST.find(probe));
        }
    }

}