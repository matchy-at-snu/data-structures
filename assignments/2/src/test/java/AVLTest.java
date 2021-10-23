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

    }

    @Test
    void insert() {
    }
}