public interface WeightedGraph<T> {
    public void addEdge(T startVertex, T endVertex, int weight);
    public Iterable<T> Dijkstra();
}