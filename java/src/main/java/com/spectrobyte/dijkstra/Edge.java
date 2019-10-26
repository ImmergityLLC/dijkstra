/*
 * Implementation of Dijkstra's shortest path algorithm
 *
 * Author: Vyacheslav Borisenko
 *
 * License: MIT
 */
package com.spectrobyte.dijkstra;

public class Edge
{
  private float label;
  private Vertex from, to;

  public Edge(Vertex from, Vertex to, float label)
  {
    this.from = from;
    this.to = to;
    this.label = label;
  }

  public float GetLabel()
  {
    return label;
  }

  public void SetLabel(float label)
  {
    this.label = label;
  }

  public Vertex GetFrom()
  {
    return from;
  }

  public void SetFrom(Vertex from)
  {
    this.from = from;
  }

  public Vertex GetTo()
  {
    return to;
  }

  public void SetTo(Vertex to)
  {
    this.to = to;
  }

  public Vertex GetPartner(Vertex vertex) throws Exception
  {
    if(from == vertex) {
      return to;
    } else {
      return from;
    }
  }
}
