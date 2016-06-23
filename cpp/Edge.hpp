// ---------------------------------------------------------------------------
// Implementation of Dijkstra's shortest path algorithm
//
// Edge.hpp
// This class is used to link two vertices
//
// Author: Vyacheslav Borisenko
//
// License: MIT
// ---------------------------------------------------------------------------

#pragma once

#ifndef HPP_EDGE
#define HPP_EDGE

#include "Vertex.hpp"

namespace dijkstra
{
	class Vertex;

	class Edge
	{
	public:
		Edge(Vertex * const from, Vertex* const to, int label) : _from(from), _to(to), _label(label) {};

		Vertex* GetFromVertex() const
		{
			return _from;
		};

		Vertex* GetToVertex() const
		{
			return _to;
		};

		void SetFromVertex(Vertex* const vertex)
		{
			_from = vertex;
		}

		void SetToVertex(Vertex* const vertex)
		{
			_to = vertex;
		}

		Vertex* GetPartner(Vertex *vertex) const
		{
			if (_from == vertex) {
				return _to;
			} else {
				return _from;
			}
		}

		void SetLabel(int label)
		{
			_label = label;
		};

		int GetLabel() const
		{
			return _label;
		};

	private:
		Vertex *_from;
		Vertex *_to;
		int _label;
	};

}

#endif