/*
* Implementation of Dijkstra's shortest path algorithm
*
* Author: Vyacheslav Borisenko
*
* License: MIT
*/
package name.bvv.dijkstra;

import java.util.Vector;

public class Vertex
{
    private String id;
    private int label;
    private boolean visited;
    private Vector<Edge> edges;
    
    public Vertex(String id)
    {
        this.id = id;
        this.label = -1;
        this.visited = false;
        edges = new Vector<>();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getLabel()
    {
        return label;
    }

    public void setLabel(int label)
    {
        this.label = label;
    }

    public Vector<Edge> getEdges()
    {
        return edges;
    }

    public void addEdge(Edge edge)
    {
        edges.add(edge);
    }

    public void removeEdge(Edge edge)
    {
        edges.remove(edge);
    }

    public void done() { visited = true; }

    public boolean isVisited() { return visited; }

    public void reset()
    {
        label = -1;
        visited = false;
    }

    public Edge getEdgeWith(String vertexID)
    {
        for (Edge edge : edges) {
            try {
                if (edge.getPartner(this).getId() == vertexID) {
                    return edge;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
}
