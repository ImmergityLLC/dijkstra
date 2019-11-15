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
    graph.AddVertex("1");
    graph.AddVertex("2");
    graph.AddVertex("3");
    graph.AddVertex("4");
    graph.AddVertex("5");
    graph.AddVertex("6");

    graph.AddEdge("1", "6", 14.0f);
    graph.AddEdge("1", "3", 9.0f);
    graph.AddEdge("1", "2", 7.0f);

    graph.AddEdge("6", "5", 9.0f);
    graph.AddEdge("6", "3", 2.0f);
    graph.AddEdge("6", "1", 14.0f);

    graph.AddEdge("3", "4", 11.0f);
    graph.AddEdge("3", "2", 10.0f);
    graph.AddEdge("3", "1", 9.0f);
    graph.AddEdge("3", "6", 2.0f);

    graph.AddEdge("2", "4", 15.0f);
    graph.AddEdge("2", "3", 10.0f);
    graph.AddEdge("2", "1", 7.0f);

    graph.AddEdge("5", "4", 6.0f);
    graph.AddEdge("5", "6", 9.0f);

    graph.AddEdge("4", "5", 6.0f);
    graph.AddEdge("4", "3", 11.0f);
    graph.AddEdge("4", "2", 15.0f);

    graph.Build("1");

    float distance = graph.GetDistanceTo("5");
    System.out.println("distance: " + distance);
  }
}
