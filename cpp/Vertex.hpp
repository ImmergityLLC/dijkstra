// ---------------------------------------------------------------------------
// Implementation of Dijkstra's shortest path algorithm
//
// Vertex.hpp
// Vertex of graph
//
// Author: Vyacheslav Borisenko
//
// License: MIT
// ---------------------------------------------------------------------------

#pragma once

#ifndef HPP_VERTEX
#define HPP_VERTEX

#include <string>
#include <vector>
#include <algorithm>
#include "Edge.hpp"

namespace dijkstra
{
	class Vertex
	{
	public:
		Vertex(std::string id) : _id(id), _label(-1) {};

		std::string GetId() const
		{
			return _id;
		};

		void SetLabel(int label)
		{
			_label = label;
		};

		int GetLabel() const
		{
			return _label;
		};

		bool IsVisited() const
		{
			return _visited;
		};

		const std::vector<Edge*>& GetEdges() const
		{
			return _edges;
		};

		void Done() { _visited = true; };

		Edge* GetEdgeWith(const std::string &VertexID)
		{
			for (auto edge : _edges) {
				if (edge->GetPartner(this)->GetId() == VertexID) {
					return edge;
				}
			}
			return nullptr;
		};

		void AddEdge(Edge *edge)
		{
			_edges.push_back(edge);
		};

		void RemoveEdge(const Edge* const edge)
		{
			_edges.erase(std::remove(_edges.begin(), _edges.end(), edge), _edges.end());
		};

		void Reset()
		{
			_label = -1;
			_visited = false;
		};
	private:
		std::string _id;
		int _label;
		bool _visited;
		std::vector<Edge*> _edges;
	};
}

#endif
