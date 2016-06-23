/*
* Implementation of Dijkstra's shortest path algorithm
*
* Author: Vyacheslav Borisenko
*
* License: MIT
*/
package name.bvv.dijkstra;

public class Edge
{
    private int label;
    private Vertex from, to;

    public Edge(Vertex from, Vertex to, int label)
    {
        this.from = from;
        this.to = to;
        this.label = label;
    }

    public int getLabel()
    {
        return label;
    }

    public void setLabel(int label)
    {
        this.label = label;
    }

    public Vertex getFrom()
    {
        return from;
    }

    public void setFrom(Vertex from)
    {
        this.from = from;
    }

    public Vertex getTo()
    {
        return to;
    }

    public void setTo(Vertex to)
    {
        this.to = to;
    }

    public Vertex getPartner(Vertex vertex) throws Exception
    {
        if (from == vertex) {
            return to;
        } else {
            return from;
        }
    }
}
