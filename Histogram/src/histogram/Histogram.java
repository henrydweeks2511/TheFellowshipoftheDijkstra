/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package histogram;

import edu.princeton.cs.In;
import edu.princeton.cs.StdIn;
import edu.princeton.cs.StdOut;

/**
 *
 * @author Henry
 */

public class Histogram {
    public static void main(String[] args) {

        // read in data and initialize graph
        In data = new In(args[0]);
        Graph G = new Graph(data, "/");
        StdOut.println("Done reading movies and building graph");

        // run breadth first search
        String s = "Bacon, Kevin";
        PathFinder finder = new PathFinder(G, s);
        StdOut.println("Done BFS");

        // compute histogram of Kevin Bacon numbers - 100 for infinity
        int MAX_BACON = 100;
        int[] hist = new int[MAX_BACON + 1];
        for (String actor : G.vertices()) {
            if (finder.distanceTo(actor) % 2 != 0) continue;  // it's a movie vertex

            int bacon = Math.min(MAX_BACON, finder.distanceTo(actor) / 2);
            hist[bacon]++;
            if (bacon >= 8 && bacon < MAX_BACON)
                StdOut.printf("%d %s\n", bacon, actor);
        }

        // print out histogram
        for (int i = 0; i < MAX_BACON; i++) {
            if (hist[i] == 0) break;
            StdOut.printf("%3d %8d\n", i, hist[i]);
        }
        // StdOut.printf("Inf %8d\n", hist[MAX_BACON]);
    }
}
