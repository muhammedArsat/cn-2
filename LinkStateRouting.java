import java.util.*;

class LinkStateRouting {
    private static final int NO_PARENT = -1;

    // Function to implement Dijkstra's Algorithm
    private static void dijkstra(int[][] graph, int source) {
        int V = graph.length;
        int[] shortestDistances = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        shortestDistances[source] = 0;

        int[] parents = new int[V];
        parents[source] = NO_PARENT;

        // Find shortest path for all vertices
        for (int i = 0; i < V - 1; i++) {
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;

            // Select the unvisited vertex with the smallest distance
            for (int vertexIndex = 0; vertexIndex < V; vertexIndex++) {
                if (!visited[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            visited[nearestVertex] = true;

            // Update the distances of the adjacent vertices
            for (int vertexIndex = 0; vertexIndex < V; vertexIndex++) {
                int edgeDistance = graph[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }

        printSolution(source, shortestDistances, parents);
    }

    // Print the results
    private static void printSolution(int source, int[] distances, int[] parents) {
        int V = distances.length;
        System.out.println("Vertex\t Distance\tPath");

        for (int vertexIndex = 0; vertexIndex < V; vertexIndex++) {
            if (vertexIndex != source) {
                System.out.print("\n" + source + " -> ");
                System.out.print(vertexIndex + " \t\t ");
                System.out.print(distances[vertexIndex] + "\t\t");
                printPath(vertexIndex, parents);
            }
        }
    }

    // Utility function to print the path
    private static void printPath(int currentVertex, int[] parents) {
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print(currentVertex + " ");
    }

    public static void main(String[] args) {
        int[][] graph = {
            {0, 10, 3, 0, 0},
            {10, 0, 1, 2, 0},
            {3, 1, 0, 8, 0},
            {0, 2, 8, 0, 7},
            {0, 0, 0, 7, 0}
        };

        int source = 0;
        dijkstra(graph, source);
    }
}
