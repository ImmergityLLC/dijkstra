#include  "Graph.hpp"
#include  <string>

extern "C" {

#include  <postgres.h>
#include  <fmgr.h>
#include  <utils/array.h>
#include  <utils/lsyscache.h>
#include  <executor/executor.h>

PG_MODULE_MAGIC;

PG_FUNCTION_INFO_V1(topo_dist);
Datum topo_dist(PG_FUNCTION_ARGS)
{
	ArrayType *va = PG_GETARG_ARRAYTYPE_P(0); // vertex []
	ArrayType *ea = PG_GETARG_ARRAYTYPE_P(1); // edge []
	int32 start = PG_GETARG_INT32(2); // start node
	ArrayType *targets = PG_GETARG_ARRAYTYPE_P(3); // target nodes

	Oid eetOid = ARR_ELEMTYPE(ea);
	int16 eetw;
	bool eetbv;
	char eetac;
	bool *eenull;
	int i, vn, en, ntargets;

	int32 *dtargets;
	int32 *dva;
	Datum *dea;
	HeapTupleHeader cth;
	Datum ca, cb, cl;
	bool cna, cnb, cnl;

	float8 distance, disum;

	dijkstra::Graph *graph;


	if (ARR_NDIM(va) != 1 || ARR_NDIM(ea) != 1 || ARR_NDIM(targets) != 1)
		ereport(ERROR, (errmsg("One-dimesional arrays are required")));
	dva = (int32 *)ARR_DATA_PTR(va);
	vn = (ARR_DIMS(va))[0];

	try {
		graph = new dijkstra::Graph();

		get_typlenbyvalalign(eetOid, &eetw, &eetbv, &eetac);
		deconstruct_array(ea, eetOid, eetw, eetbv, eetac, &dea, &eenull, &en);

		for(i = 0; i < vn; i++) {
			graph->AddVertex(std::to_string(dva[i]));
		}

		for(i = 0; i < en; i++) {
			cth = (HeapTupleHeader)PG_DETOAST_DATUM(dea[i]);
			ca = GetAttributeByName(cth, "a", &cna);
			cb = GetAttributeByName(cth, "b", &cnb);
			cl = GetAttributeByName(cth, "l", &cnl);
			if (cna || cnb || cnl)
				ereport(ERROR, (errmsg("null value in edge")));
			graph->AddEdge(std::to_string(DatumGetInt32(ca)), std::to_string(DatumGetInt32(cb)), DatumGetFloat8(cl));
		}
		graph->Build(std::to_string(start));

		dtargets = (int32 *)ARR_DATA_PTR(targets);
		ntargets = (ARR_DIMS(targets))[0];
		disum = 0;
		for(i = 0; i < ntargets; i++) {
			distance = graph->GetDistanceTo(std::to_string(dtargets[i]));
			if(distance > 99990) break;
			disum += distance;
		}
		delete graph;
		if(i < ntargets)
			PG_RETURN_NULL();
		else
			PG_RETURN_FLOAT8(disum / (float8)ntargets);
	}
	catch(...) {
		std::exception_ptr p = std::current_exception();
		ereport(ERROR, (errmsg(p ? p.__cxa_exception_type()->name() : "unidentified exception in dijkstra code")));
	}
}

}
