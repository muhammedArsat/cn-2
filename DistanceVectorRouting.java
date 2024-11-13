import java.util.Arrays;

class DistanceVectorRouting {
    
    public static void bellmanFord(int[][] graph, int V, int E, int source) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        
        for (int i = 0; i < V - 1; i++) {
            for (int j = 0; j < E; j++) {
                int u = graph[j][0];
                int v = graph[j][1];
                int weight = graph[j][2];

                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }


        System.out.println("Distance from source " + source + " to all vertices:");
        for (int i = 0; i < V; i++) {
            System.out.println("To vertex " + i + " distance: " + dist[i]);
        }
    }

    public static void main(String[] args) {
        int V = 5; // Number of vertices
        int E = 7; // Number of edges

        // Graph represented as {source, destination, weight}
        int[][] graph = {
            {0, 1, 10},
            {0, 2, 3},
            {1, 3, 1},
            {2, 1, 4},
            {2, 3, 8},
            {3, 4, 2},
            {4, 0, 7}
        };

        int source = 0;
        bellmanFord(graph, V, E, source);
    }
}
