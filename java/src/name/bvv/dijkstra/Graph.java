/*
* Implementation of Dijkstra's shortest path algorithm
*
* Author: Vyacheslav Borisenko
*
* License: MIT
*/
package name.bvv.dijkstra;

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
        vertex.setLabel(0);
        next = vertex.getId();
        boolean start = true;
        while (start) {
            if (finished == vertices.size()) {
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
        vertices.forEach((id, vertex) -> vertex.reset());
    }

    /*
		Return distance from start position to @destination
	*/
    public int getDistanceTo(String destination)
    {
        return vertices.get(destination).getLabel();
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
        for(Edge edge : vertex.getEdges()){
            try {
                edge.getPartner(vertex).removeEdge(edge);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        vertices.remove(id);
    }

    public void addEdge(String from, String to, int label)
    {
        Vertex _from = vertices.get(from);
        Vertex _to = vertices.get(to);
        Edge edge = new Edge(_from, _to, label);
        _from.addEdge(edge);
        _to.addEdge(edge);
    }
    
    private void exploreVertex(Vertex vertex) throws Exception
    {
        Vector<Edge> edges = vertex.getEdges();
        for (Edge edge : edges) {
            Vertex partner = edge.getPartner(vertex);
            if (partner.isVisited()) {
                continue;
            }

            int distanceToPartner = vertex.getLabel() + edge.getLabel();
            int distanceFromPartner = edge.getLabel() + partner.getLabel();
            if (partner.getLabel() == -1) {
                partner.setLabel(distanceToPartner);
            } else if (distanceToPartner < partner.getLabel()) {
                partner.setLabel(distanceToPartner);
            } else if (distanceToPartner > distanceFromPartner) {
                vertex.setLabel(distanceFromPartner);
            }

            if (next == vertex.getId()) {
                next = partner.getId();
            } else {
                Vertex nextVertex = vertices.get(next);
                if (partner.getEdgeWith(vertex.getId()).getLabel() < nextVertex.getEdgeWith(vertex.getId()).getLabel()) {
                    next = partner.getId();
                }
            }
        }
        vertex.done();
        finished++;
    }
}
