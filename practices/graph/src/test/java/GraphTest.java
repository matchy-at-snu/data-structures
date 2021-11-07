import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    @Test
    public void DFSTest() {
        Graph<String> graph1 = new AdjListGraph<>(6);
        graph1.addEdge("A", "C");
        graph1.addEdge("A", "E");
        graph1.addEdge("B", "C");
        graph1.addEdge("B", "F");
        graph1.addEdge("C", "D");
        graph1.addEdge("C", "F");
        graph1.addEdge("D", "F");
        graph1.addEdge("E", "F");

        Graph<String> graph2 = new AdjMatrixGraph<>(6);
        graph2.addEdge("A", "C");
        graph2.addEdge("A", "E");
        graph2.addEdge("B", "C");
        graph2.addEdge("B", "F");
        graph2.addEdge("C", "D");
        graph2.addEdge("C", "F");
        graph2.addEdge("D", "F");
        graph2.addEdge("E", "F");

        ArrayList<String> visit1 = (ArrayList<String>) graph1.DFS("A");
        ArrayList<String> visit2 = (ArrayList<String>) graph2.DFS("A");
        System.out.println(visit1);
        System.out.println(visit2);

//        assertEquals(visit1, visit2);
    }
}
