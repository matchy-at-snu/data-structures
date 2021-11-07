import java.util.*;

class AdjMatrixGraph<T> implements Graph<T> {

    ArrayList<ArrayList<Boolean>> adjacentMatrix;
    ArrayList<T> vertices;

    public AdjMatrixGraph(int numOfVertices) {
        vertices = new ArrayList<>(numOfVertices);
        adjacentMatrix = new ArrayList<>(numOfVertices);
        for (int i = 0; i < numOfVertices; i++) {
            adjacentMatrix.add(new ArrayList<>(Collections.nCopies(numOfVertices, false)));
        }
    }

    @Override
    public void addEdge(T startVertex, T endVertex) {
        if (!vertices.contains(startVertex)) {
            vertices.add(startVertex);
        }
        if (!vertices.contains(endVertex)) {
            vertices.add(endVertex);
        }
        int sIndex = vertices.indexOf(startVertex);
        int eIndex = vertices.indexOf(endVertex);
        adjacentMatrix.get(sIndex).set(eIndex, true);
        adjacentMatrix.get(eIndex).set(sIndex, true);
    }

    @Override
    public Iterable<T> DFS(T startVertex) {
        ArrayList<T> visited = new ArrayList<>();
        _dfs(startVertex, visited);
        return visited;
    }

    private void _dfs(T startVertex, List<T> visited) {
        visited.add(startVertex);
        ArrayList<Boolean> adjList = adjacentMatrix.get(vertices.indexOf(startVertex));
        for (int i = 0; i < adjList.size(); i++) {
            if (adjList.get(i)) { // edge exists
                T adjVertex = vertices.get(i);
                if (!visited.contains(adjVertex)) {
                    _dfs(adjVertex, visited);
                }
            }
        }
    }

    @Override
    public Iterable<T> BFS(T startVertex) {
        ArrayList<T> visited = new ArrayList<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(startVertex);
        visited.add(startVertex);
        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            ArrayList<Boolean> adjList = adjacentMatrix.get(vertices.indexOf(vertex));
            for (int i = 0; i < adjList.size(); i++) {
                if (adjList.get(i)) { // edge exists
                    T adjVertex = vertices.get(i);
                    if (!visited.contains(adjVertex)) {
                        queue.add(adjVertex);
                        visited.add(adjVertex);
                    }
                }
            }
        }
        return visited;
    }
}