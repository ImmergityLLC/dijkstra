CXX = g++
DIJKSTRA_INC = -I../cpp/include
POSTGRES_INC = -I$(PSQL_SERVER_INC)
CXXFLAGS = -fexceptions -fPIC -Wall
LXXFLAGS = -static-libstdc++ -static-libgcc -Wl,-s

all: topo9.so

topo9.so: topo9.o
	$(CXX) --shared $(LXXFLAGS) -o $@ $^

topo9.o: topo9.cpp
	$(CXX) -c $(CXXFLAGS) $(DIJKSTRA_INC) $(POSTGRES_INC) $^
