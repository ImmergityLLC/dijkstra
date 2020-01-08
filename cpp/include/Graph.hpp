// ---------------------------------------------------------------------------
// Implementation of Dijkstra's shortest path algorithm
//
// Graph.hpp
// This class is responsible for the calculation and construction of graph
//
// Author: Vyacheslav Borisenko
//
// License: MIT
// ---------------------------------------------------------------------------

#ifndef HPP_GRAPH
#define HPP_GRAPH

#include <map>
#include <vector>
#include <string>
#include <cassert>
#include "Vertex.hpp"
#include "Edge.hpp"

namespace dijkstra {
using namespace std;

class Graph
{
public:
  Graph() : _next(""), _finished(0) {};

  ~Graph()
  {
    Clear();
  };

  /*
      Remove all vertices and edges.
  */
  void Clear()
  {
    for(auto& edge : _edges) {
      delete edge;
      edge = nullptr;
    }
    _edges.clear();
    for(auto& entry : _vertices) {
      auto& vertex = entry.second;
      delete vertex;
      vertex = nullptr;
    }
    _vertices.clear();
  };

  /*
      Build graph with start position
      @startVertexID - start position
  */
  void Build(const std::string& startVertexID)
  {
    assert(!_vertices.empty());
    _finished = 0;
    auto startVertex = _vertices.at(startVertexID);
    startVertex->SetLabel(0);
    _next = startVertex->GetId();
    bool start = true;
    while(start) {
      if(_finished == _vertices.size()) {
        start = false;
        continue;
      }

      auto vertex = _vertices.at(_next);
      if(vertex->IsVisited()) {
        vertex = getNextUnvisitedVertex();
        if(vertex == nullptr) {
          start = false;
          continue;
        }
      }

      exploreVertex(vertex);
    }
  };

  /*
      Reset all labels from vertices. Use this method if you want to change start position and save current graph
  */
  void Reset()
  {
    for(auto& entry : _vertices) {
      auto vertex = entry.second;
      vertex->Reset();
    }
  };

  /*
      Return distance from start position to @destination
  */
  float GetDistanceTo(const std::string& destination)
  {
    return _vertices.at(destination)->GetLabel();
  };

  void AddVertex(const std::string& id)
  {
    _vertices.insert(std::pair<std::string, Vertex*>(id, new Vertex(id)));
  };

  /*
    Remove vertex and edges which have been joined to it
  */
  void RemoveVertex(const std::string& id)
  {
    auto it = _vertices.find(id);
    if(it != _vertices.end()) {
      auto vertex = it->second;
      for(auto edge : vertex->GetEdges()) {
        edge->GetPartner(vertex)->RemoveEdge(edge);
        _edges.erase(remove(_edges.begin(), _edges.end(), edge), _edges.end());
      }
      delete vertex;
      vertex = nullptr;
      _vertices.erase(it);
    }
  };

  void AddEdge(const std::string& from, const std::string& to, float label)
  {
    auto _from = _vertices.at(from);
    auto _to = _vertices.at(to);
    auto edge = new Edge(_from, _to, label);
    _edges.push_back(edge);
    _from->AddEdge(edge);
    _to->AddEdge(edge);
  };

  void RemoveEdge(const std::string& from, const std::string& to)
  {
    auto _from = _vertices.at(from);
    auto _to = _vertices.at(to);
    auto edge = _from->GetEdgeWith(to);
    if(edge != nullptr) {
      _edges.erase(std::remove(_edges.begin(), _edges.end(), edge), _edges.end());
    }
    _from->RemoveEdge(edge);
    _to->RemoveEdge(edge);
  };

private:
  std::string _next;
  std::size_t _finished;
  std::map<std::string, Vertex*> _vertices;
  std::vector<Edge*> _edges;

  Vertex* getNextUnvisitedVertex()
  {
    for(auto& entry : _vertices) {
      if(!entry.second->IsVisited()) {
        return entry.second;
      }
    }

    return nullptr;
  }

  void exploreVertex(Vertex* vertex)
  {
    std::vector<Edge*> edges = vertex->GetEdges();

    float minDistance = 0.0f;
    Vertex* next = nullptr;
    for(auto& edge : edges) {
      Vertex* partner = edge->GetPartner(vertex);
      if(partner->IsVisited()) {
        continue;
      }

      auto distanceToPartner = vertex->GetLabel() + edge->GetLabel();
      auto distanceFromPartner = partner->GetLabel() + edge->GetLabel();
      if(partner->GetLabel() >= 99990.0f) {
        partner->SetLabel(distanceToPartner);
      } else {
        if(distanceToPartner < partner->GetLabel()) {
          partner->SetLabel(distanceToPartner);
        } else if(distanceFromPartner < vertex->GetLabel()) {
          vertex->SetLabel(distanceFromPartner);
        }
      }

      if(minDistance == 0.0f) {
        minDistance = distanceToPartner;
        next = partner;
      } else {
        if(distanceToPartner < minDistance) {
          minDistance = distanceToPartner;
          next = partner;
        }
      }
    }

    if(next != nullptr) {
      _next = next->GetId();
    }

    vertex->Done();
    _finished++;
  }
};
}

#endif
