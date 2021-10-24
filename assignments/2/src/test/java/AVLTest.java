import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import edu.princeton.cs.algs4.AVLTreeST;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTest {

    private static final Lorem lorem = LoremIpsum.getInstance();

    static final AVLTreeST<String, String> princetonAVL = new AVLTreeST<>();
    static final AVL myAVL = new AVL();

    @BeforeAll
    static void initialize() {
        String[] words = lorem.getParagraphs(5, 10).split("\\s+");
        for (var word : words) {
            princetonAVL.put(word, word);
            myAVL.insert(word);
        }
    }

    @Test
    void size() {
        assertEquals(princetonAVL.size(), myAVL.size());
    }

    @Test
    void insert() {
        assertEquals(princetonAVL.keys().toString().trim(),
                String.join(" ", myAVL.keys()));
        assertEquals(princetonAVL.keysLevelOrder().toString().trim(),
                String.join(" ", myAVL.keysLevelOrder()));
    }
}