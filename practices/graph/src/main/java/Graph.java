
abstract class Graph {
    abstract void addEdge(int v, int w);
    abstract Iterable<Integer> adj(int v);
    abstract int V();
    abstract int E();
    abstract void show();
}