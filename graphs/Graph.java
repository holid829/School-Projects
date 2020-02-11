package graphs;

import java.nio.file.Paths;
import java.util.*;

/**
 * Implements a graph. We use two maps: one map for adjacency properties 
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated 
 * with a vertex. 
 * 
 * @author cmsc132
 * 
 * @param <E>
 */
public class Graph<E> {
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;
	
	public Graph(){
		adjacencyMap = new HashMap();
		dataMap = new HashMap();
		
	}
	public void	addDirectedEdge(String startVertexName, String endVertexName, int cost) {
		if(adjacencyMap.get(startVertexName) == null) {
			adjacencyMap.put(startVertexName,new HashMap());
		}
		adjacencyMap.get(startVertexName).put(endVertexName,cost);
	}

	public void	addVertex(String vertexName, E data) {
		dataMap.put(vertexName,data);
	}

	public void	doBreadthFirstSearch(String startVertexName, CallBack<E> callback) {
		ArrayList<String> Visited  = new ArrayList<String>();
		PriorityQueue<String> Discovered  = new PriorityQueue();
		Discovered.add(startVertexName);
		
		
		while (!Discovered.isEmpty()) {
			String current = Discovered.remove();
			if(!Visited.contains(current)) {
				Visited.add(current);
			
				
				TreeMap data = new TreeMap(adjacencyMap.get(current));
				Object[] adjacents = data.keySet().toArray();
			
				
				for(int i = 0; i < adjacents.length; i++) {
					
					String next = (String) adjacents[i];
					if (!Visited.contains(next)) {
						Discovered.add(next); 
					}
				}
			}
		}
		
		for(int i = 0; i < Visited.size(); i++) {
			callback.processVertex(Visited.get(i),dataMap.get(Visited.get(i)));
		}
		
	}

	public void	doDepthFirstSearch(String startVertexName, CallBack<E> callback) {
		ArrayList<String> Visited  = new ArrayList<String>();
		Stack<String> Discovered  = new Stack();
		Discovered.push(startVertexName);
		
		
		while (!Discovered.isEmpty()) {
			String current = Discovered.pop();
			if(!Visited.contains(current)) {
				Visited.add(current);
			
				Object[] adjacents = adjacencyMap.get(current).keySet().toArray();
			
				for(int i = 0; i < adjacents.length; i++) {
					
					String next = (String) adjacents[i];
					if (!Visited.contains(next)) {
						Discovered.push(next); 
					}
				}
			}
		}
		
		for(int i = 0; i < Visited.size(); i++) {
			callback.processVertex(Visited.get(i),dataMap.get(Visited.get(i)));
		}
	}

	public int doDijkstras(String startVertexName, String endVertexName, ArrayList<String> shortestPath) {

		ArrayList<String> visited = new ArrayList<String>(); 
		ArrayList<String> processors = new ArrayList<String>(); 
		ArrayList<Integer> costs = new ArrayList<Integer>(); 
		Object[] keys = dataMap.keySet().toArray();
		
		
		
		for(int i = 0; i < keys.length; i ++) {
			
			if(keys[i].equals(startVertexName)) {
				costs.add(i, 0);
			}
			
			else {
				costs.add(i, Integer.MAX_VALUE);
			}
			
			processors.add(i, "None");
		}
		
		while (visited.size()!=dataMap.size()) {
			String current = "";
			int min = Integer.MAX_VALUE;
			int curInt = 0;
			
		
			
			for(int i = 0; i < keys.length; i ++) {
				if(!visited.contains(keys[i])) {
					curInt = costs.get(i);
					
					if(min > curInt) {
						min = curInt;
						current = (String) keys[i];
					}
				}
			}
	
			
			if(current == "") {
				
				
				int index = 0;
				
				String path = endVertexName;
				while(!path.equals(startVertexName)) {					
					for(int i = 0; i < keys.length; i++)
						if(keys[i].equals(path))
							index = i;
					shortestPath.add(0,path);
					path = processors.get(index);
					
					if(path == "None") {
						shortestPath.clear();
						shortestPath.add("None");
						return -1;
					}
					
				}
				
				for(int i = 0; i < keys.length; i++)
					if(keys[i].equals(endVertexName))
						index = i;
				
				shortestPath.add(0, startVertexName);

				return costs.get(index);
			}
	
			
			visited.add(current);
	
			HashMap<String,Integer> adjacents = adjacencyMap.get(current);
		
			if(adjacents!=null) {
				for(int i = 0; i < keys.length; i++) {
					
					if(adjacents.containsKey(keys[i])&&!visited.contains(keys[i])) {
						if (adjacents.get(keys[i]) + min < costs.get(i)) {
							costs.set(i,adjacents.get(keys[i]) + min);
							processors.set(i,current);
						}
					}
				}
			}
		}
		
		int index = 0;
		
		String path = endVertexName;
		while(!path.equals(startVertexName)) {
			for(int i = 0; i < keys.length; i++)
				if(keys[i].equals(path))
					index = i;
			shortestPath.add(0,path);
			path = processors.get(index);
			
			if(path == "None") {
				shortestPath.clear();
				shortestPath.add("None");
				return -1;
			}
		}
		
		for(int i = 0; i < keys.length; i++)
			if(keys[i].equals(endVertexName))
				index = i;
		
		shortestPath.add(0, startVertexName);

		return costs.get(index);
	}

	
	public Map<String,Integer> getAdjacentVertices(String vertexName){
		return adjacencyMap.get(vertexName);
		
	}

	public int getCost(String startVertexName, String endVertexName) {
		return adjacencyMap.get(startVertexName).get(endVertexName);
		
	}

	public E getData(String vertex) {
		return dataMap.get(vertex);
	}

	public Set<String> getVertices(){
		return dataMap.keySet();
		
	}

	public String toString(){
		TreeMap data= new TreeMap(dataMap);
		String toRet ="Vertices: " + data.keySet().toString() + "\n";
		toRet += "Edges:";
		
		
		Object[] keys = data.keySet().toArray();
		
		for(int i = 0; i < keys.length; i++) {
			if(!adjacencyMap.containsKey(keys[i]))
				toRet += "\nVertex("+keys[i]+")--->"+"{}";
			else
				toRet += "\nVertex("+keys[i]+")--->"+adjacencyMap.get(keys[i]).toString();
		}
		
		return toRet;
	}
}