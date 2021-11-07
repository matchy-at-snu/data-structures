public interface Graph<T> {
    public void addEdge(T startVertex, T endVertex);
    public Iterable<T> DFS(T startVertex);
    public Iterable<T> BFS(T startVertex);
}
