/*
 * Implementation of Dijkstra's shortest path algorithm
 *
 * Author: Vyacheslav Borisenko
 *
 * License: MIT
 */
package com.spectrobyte.dijkstra;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Graph
{
  private String next;
  private Map<String, Vertex> vertices;
  private int finished;

  public Graph()
  {
    next = "";
    finished = 0;
    vertices = new HashMap<>();
  }

  /*
  Build graph with start position
  @startVertexID - start position
*/
  public void build(String startVertexID) throws Exception
  {
    finished = 0;
    Vertex vertex = vertices.get(startVertexID);
    vertex.SetLabel(0);
    next = vertex.GetId();
    boolean start = true;
    while(start) {
      if(finished == vertices.size()) {
        start = false;
        continue;
      }
      Vertex nextVertex = vertices.get(next);
      exploreVertex(nextVertex);
    }
  }

  /*
      Remove all vertices and edges.
  */
  public void Reset()
  {
    vertices.forEach((id, vertex) -> vertex.Reset());
  }

  /*
  Return distance from start position to @destination
*/
  public float getDistanceTo(String destination)
  {
    return vertices.get(destination).GetLabel();
  }

  public void addVertex(String id)
  {
    vertices.put(id, new Vertex(id));
  }

  /*
    Remove vertex and edges which have been joined to it
*/
  public void removeVertex(String id)
  {
    Vertex vertex = vertices.get(id);
    for(Edge edge : vertex.GetEdges()) {
      try {
        edge.GetPartner(vertex).RemoveEdge(edge);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    vertices.remove(id);
  }

  public void addEdge(String from, String to, float label)
  {
    Vertex _from = vertices.get(from);
    Vertex _to = vertices.get(to);
    Edge edge = new Edge(_from, _to, label);
    _from.AddEdge(edge);
    _to.AddEdge(edge);
  }

  private void exploreVertex(Vertex vertex) throws Exception
  {
    Vector<Edge> edges = vertex.GetEdges();

    float minDistance = 0.0f;
    Vertex nextVertex = null;
    for(Edge edge : edges) {
      Vertex partner = edge.GetPartner(vertex);
      if(partner.IsVisited()) {
        continue;
      }

      float distanceToPartner = vertex.GetLabel() + edge.GetLabel();
      float distanceFromPartner = partner.GetLabel() + edge.GetLabel();
      if(partner.GetLabel() == -1) {
        partner.SetLabel(distanceToPartner);
      } else {
        if(distanceToPartner < partner.GetLabel()) {
          partner.SetLabel(distanceToPartner);
        } else if(distanceFromPartner < vertex.GetLabel()) {
          vertex.SetLabel(distanceFromPartner);
        }
      }

      if(minDistance == 0.0f) {
        minDistance = distanceToPartner;
        nextVertex = partner;
      } else {
        if(distanceToPartner < minDistance) {
          minDistance = distanceToPartner;
          nextVertex = partner;
        }
      }
    }

    if(nextVertex != null) {
      next = nextVertex.GetId();
    }

    vertex.Done();
    finished++;
  }
}
