/*
 * Implementation of Dijkstra's shortest path algorithm
 *
 * Author: Vyacheslav Borisenko
 *
 * License: MIT
 */
package com.spectrobyte.dijkstra;

public class Main
{
  // use sample by dijkstra_graph.png
  public static void main(String[] args) throws Exception
  {
    Graph graph = new Graph();
    graph.addVertex("1");
    graph.addVertex("2");
    graph.addVertex("3");
    graph.addVertex("4");
    graph.addVertex("5");
    graph.addVertex("6");

    graph.addEdge("1", "6", 14.0f);
    graph.addEdge("1", "3", 9.0f);
    graph.addEdge("1", "2", 7.0f);

    graph.addEdge("6", "5", 9.0f);
    graph.addEdge("6", "3", 2.0f);
    graph.addEdge("6", "1", 14.0f);

    graph.addEdge("3", "4", 11.0f);
    graph.addEdge("3", "2", 10.0f);
    graph.addEdge("3", "1", 9.0f);
    graph.addEdge("3", "6", 2.0f);

    graph.addEdge("2", "4", 15.0f);
    graph.addEdge("2", "3", 10.0f);
    graph.addEdge("2", "1", 7.0f);

    graph.addEdge("5", "4", 6.0f);
    graph.addEdge("5", "6", 9.0f);

    graph.addEdge("4", "5", 6.0f);
    graph.addEdge("4", "3", 11.0f);
    graph.addEdge("4", "2", 15.0f);

    graph.build("1");

    float distance = graph.getDistanceTo("5");
    System.out.println("distance: " + distance);
  }
}
