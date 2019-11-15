/*
 * Implementation of Dijkstra's shortest path algorithm
 *
 * Author: Vyacheslav Borisenko
 *
 * License: MIT
 */
package com.spectrobyte.dijkstra;

import java.util.Vector;

public class Vertex
{
  private String id;
  private float label;
  private boolean visited;
  private Vector<Edge> edges;

  public Vertex(String id)
  {
    this.id = id;
    this.label = -1.0f;
    this.visited = false;
    edges = new Vector<>();
  }

  public String GetId()
  {
    return id;
  }

  public void SetId(String id)
  {
    this.id = id;
  }

  public float GetLabel()
  {
    return label;
  }

  public void SetLabel(float label)
  {
    this.label = label;
  }

  public Vector<Edge> GetEdges()
  {
    return edges;
  }

  public void AddEdge(Edge edge)
  {
    edges.add(edge);
  }

  public void RemoveEdge(Edge edge)
  {
    edges.remove(edge);
  }

  public void Done()
  {
    visited = true;
  }

  public boolean IsVisited()
  {
    return visited;
  }

  public void Reset()
  {
    label = -1.0f;
    visited = false;
  }

  public Edge GetEdgeWith(String vertexID)
  {
    for(Edge edge : edges) {
      try {
        if(edge.GetPartner(this).GetId().equals(vertexID)) {
          return edge;
        }
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
