package mordorfinder;

import edu.princeton.cs.In;
import edu.princeton.cs.StdIn;
import edu.princeton.cs.StdOut;
import java.util.Stack;

/******************************************************************************
 *  Compilation:  javac PathFinder.java
 *  Execution:    java Pathfinder input.txt delimiter source
 *  Dependencies: Queue.java Stack.java Graph.java
 *  
 *  Runs breadth first search algorithm from source s on a graph G.
 *  After preprocessing the graph, can process shortest path queries
 *  from s to any vertex t.
 *
 *  % java PathFinder routes.txt " " JFK
 *  LAX
 *     JFK
 *     ORD
 *     PHX
 *     LAX
 *  distance 3
 *  MCO
 *     JFK
 *     MCO
 *  distance 1
 *  DFW
 *     JFK
 *     ORD
 *     DFW
 *  distance 2
 *
 ******************************************************************************/

public class PathFinder1 {

    // prev[v] = previous vertex on shortest path from s to v
    // dist[v] = length of shortest path from s to v
    private ST<String, String>  prev = new ST<String, String>();
    private ST<String, Integer> dist = new ST<String, Integer>();

    // run BFS in graph G from given source vertex s
    public PathFinder1(Graph G, String s) {

        // put source on the queue
        Queue<String> q = new Queue<String>();
        q.enqueue(s);
        dist.put(s, 0);
        
        // repeated remove next vertex v from queue and insert
        // all its neighbors, provided they haven't yet been visited
        while (!q.isEmpty()) {
            String v = q.dequeue();
            for (String w : G.adjacentTo(v)) {
                if (!dist.contains(w)) {
                    q.enqueue(w);
                    dist.put(w, 1 + dist.get(v));
                    prev.put(w, v);
                }
            }
        }
    }

    // is v reachable from the source s?
    public boolean hasPathTo(String v) {
        return dist.contains(v);
    }

    // return the length of the shortest path from v to s
    public int distanceTo(String v) {
        if (!hasPathTo(v)) return Integer.MAX_VALUE;
        return dist.get(v);
    }

    // return the shortest path from v to s as an Iterable
    public Iterable<String> pathTo(String v) {
        Stack<String> path = new Stack<String>();
        while (v != null && dist.contains(v)) {
            path.push(v);
            v = prev.get(v);
        }
        return path;
    }


    public static void main(String[] args) {
        String filename  = args[0];
        String delimiter = args[1];
        
        System.out.println( filename );
        System.out.println( ">" + delimiter + "<" );
        In in = new In(filename); 
        Graph G = GraphGenerator.read(in, delimiter);
        String s = args[2];
        PathFinder1 pf = new PathFinder1(G, s);
        
        
        pf.report( "JFK" );
        pf.report( "MCO" );
        
    } // main( String [] )


    private void report( String airport ) {

            for (String v : this.pathTo(airport)) {
                StdOut.println("   " + v);
            }
            StdOut.println("distance " + this.distanceTo(airport));
    } // report( PathFinder, String )
    
} // PathFinder
