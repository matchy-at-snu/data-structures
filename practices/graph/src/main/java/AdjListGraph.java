import java.util.*;

class AdjListGraph<T> implements Graph<T> {

    private HashMap<T, LinkedList<T>> adjacentList;

    public AdjListGraph() {
       adjacentList = new HashMap<>();
    }

    @Override
    public void addEdge(T startVertex, T endVertex) {
        try {
            adjacentList.get(startVertex).add(endVertex);
        } catch (NullPointerException e) {
            adjacentList.put(startVertex, new LinkedList<>(Arrays.asList(endVertex)));
        }

        try {
            adjacentList.get(endVertex).add(startVertex);
        } catch (NullPointerException e) {
            adjacentList.put(endVertex, new LinkedList<>(Arrays.asList(startVertex)));
        }
    }

    @Override
    public Iterable<T> DFS(T startVertex) {
        ArrayList<T> visited = new ArrayList<>(adjacentList.size());
        _dfs(startVertex, visited);
        return visited;
    }

    private void _dfs(T startVertex, List<T> visited) {
        visited.add(startVertex);
        for (T adjacentVertex : adjacentList.get(startVertex)) {
            if (!visited.contains(adjacentVertex)) {
                _dfs(adjacentVertex, visited);
            }
        }
    }

    @Override
    public Iterable<T> BFS(T startVertex) {
        ArrayList<T> visited = new ArrayList<>(adjacentList.size());
        Queue<T> queue = new LinkedList<>();
        queue.add(startVertex);
        visited.add(startVertex);
        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            visited.add(vertex);
            for (T adjacentVertex : adjacentList.get(vertex)) {
                if (!visited.contains(adjacentVertex)) {
                    queue.add(adjacentVertex);
                    visited.add(adjacentVertex);
                }
            }
        }
        return visited;
    }
}