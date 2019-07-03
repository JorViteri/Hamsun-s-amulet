package Rogue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

import DungeonComponents.Corridor;
import DungeonComponents.Node;
import DungeonComponents.Room;
import Utils.Position;

public class WorldBuilderMathOperations {

	public WorldBuilderMathOperations(){
		
	}
	
	static private boolean isNaN(double n) {
		return (n != n);
	}
	
	public ArrayList<Node> delaunay(ArrayList<Room> room_list, ArrayList<Position> centers) {
		ArrayList<Node> nodeResults = new ArrayList<>();
		ArrayList<Position> adj;
		ArrayList<Position> centersList = centers;
		ArrayList<String> lista_posiciones = new ArrayList<>();
		int centersSize = centersList.size();
		Double dist, radio;
		boolean checkA, checkB, checkC, checkL, cont;
		Position pa, pb, pc, pd, center;
		String[] elementos = new String[centersSize];
		char[] digitos = new char[3];
		
		for (int i = 0; i < centersSize; i++) {
			elementos[i] = Integer.toString(i);
		}

		Perm2(elementos, "", 3, centersSize, lista_posiciones);

		for (String permut : lista_posiciones) {
			digitos = permut.toCharArray();

			pa = centersList.get(Character.getNumericValue(digitos[0]));
			pb = centersList.get(Character.getNumericValue(digitos[1]));
			pc = centersList.get(Character.getNumericValue(digitos[2]));

			center = getCirclCenter(pa, pb, pc);
			if(isNaN(center.getX())&&(isNaN(center.getY()))){
				cont = false;
			} else{
				cont = true;
			} 	
			
			if (cont){
				radio = center.distance(pa);
				for (int d = 0; d < centersSize; d++) {
					if (!((d == digitos[0]) || (d == digitos[1]) || (d == digitos[2]))) {
						pd = centersList.get(d);
						dist = center.distance(pd);
						if (dist < radio) {
							cont = false;
							break;
						}
					}

				}
			}
			

			if (cont) {
				checkA = false;
				checkB = false;
				checkC = false;

				if (!nodeResults.isEmpty()) {
					for (Node node : nodeResults) {
						if (node.getPosition().equals(pa)) {
							adj = new ArrayList<>();
							adj.add(pb);
							adj.add(pc);
							node.setAdjacent(adj);
							checkA = true;
						} else if (node.getPosition().equals(pb)) {
							adj = new ArrayList<>();
							adj.add(pa);
							adj.add(pc);
							node.setAdjacent(adj);
							checkB = true;
						} else if (node.getPosition().equals(pc)) {
							adj = new ArrayList<>();
							adj.add(pa);
							adj.add(pb);
							node.setAdjacent(adj);
							checkC = true;
						}
					}
				}
				checkL = (checkA && checkB && checkC);
				if (!checkL) { // Si alguno de los nodos no existia previamente (o la lista estaba vacia)
					if (!checkA) {
						adj = new ArrayList<>();
						adj.add(pb);
						adj.add(pc);
						Node nodeA = new Node(pa, null, adj);
						nodeResults.add(nodeA);
					}
					if (!checkB) {
						adj = new ArrayList<>();
						adj.add(pa);
						adj.add(pc);
						Node nodeB = new Node(pb, null, adj);
						nodeResults.add(nodeB);
					}
					if (!checkC) {
						adj = new ArrayList<>();
						adj.add(pa);
						adj.add(pb);
						Node nodeC = new Node(pc, null, adj);
						nodeResults.add(nodeC);
					}

				}
			}

		}

		return nodeResults;
	}

	
	private static void Perm2(String[] elem, String act, int n, int r, ArrayList<String> list) {
		if (n == 0) {
			list.add(act);
		} else {
			for (int i = 0; i < r; i++) {
				if (!act.contains(elem[i])) { // Controla que no haya  repeticiones
					Perm2(elem, act + elem[i], n - 1, r, list);
				}
			}
		}
	}
	
	
	private static Position getCirclCenter(Position a, Position b, Position c) {
		double x1,x2,x3;
		double y1,y2,y3;
		double mr, mt;
		double centx, centy;
		boolean first_vert, second_vert, collinear_or_coincident, collinear, idmr0;
		collinear=false;
		
		//Cogemos los valores de las x e y
		x1= a.getX();
		y1= a.getY();
		x2= b.getX();
		y2= b.getY();
		x3= c.getX();
		y3= c.getY();
		
		//Obtenemos las pendientes
		mr= (y2-y1)/(x2-x1); 
		mt= (y3-y2)/(x3-x2);
		
		//Comprobamos las pendientes
		first_vert= Double.isInfinite(mr);
		second_vert= Double.isInfinite(mt);
		if (isNaN(mr)&(isNaN(mt))){
			collinear=true;
		}
		collinear_or_coincident = (mr==mt|collinear|isNaN(mr)|isNaN(mt));
		 
		//Calculamos el x del centro 
		if (first_vert) {
			 centx = (mt*(y3-y1)+(x2+x3))/2; // Failure mode (1) ==> use limit case of mr==Inf

		} else if(second_vert){
			 centx = ((x1+x2)-mr*(y3-y1))/2; //Failure mode (2) ==> use limit case of mt==Inf

		}else if (collinear_or_coincident){
			 centx = Float.NaN; //Failure mode (3) or (4) ==> cannot determine center point, return NaN

		}else{
			centx = (mr*mt*(y3-y1)+mr*(x2+x3)-mt*(x1+x2))/(2*(mr-mt));
		}
		
		//Calculamos el y del centro
		centy = -1/mr*(centx-(x1+x2)/2)+(y1+y2)/2;
	    idmr0 = mr==0;
	    if (idmr0){
	    	centy= -1./mt*(centx-(x2+x3)/2)+(y2+y3)/2;
	    }
	    if (collinear_or_coincident){
	    	centy= Float.NaN;
	    }
		
		return new Position(centx, centy, a.getZ());
	}
	
	Comparator<Node> nodeKeyComparator = new Comparator<Node>() {
		@Override
		public int compare(Node n1, Node n2) {
			if (n1.getKey() < n2.getKey())
				return -1;
			if (n1.getKey() > n2.getKey())
				return 1;
			return 0;
		}
	};

	
	public ArrayList<Corridor> minSpanningTreePrim(ArrayList<Node> graph) {
		ArrayList<Corridor> result = new ArrayList<>();
		double inf = Double.POSITIVE_INFINITY;
		Node aux, nodeq;
		PriorityQueue<Node> nodeQueue = new PriorityQueue<>(nodeKeyComparator);
		
		for (Node node : graph) {
			node.setParent(null);
			node.setKey(inf);
		}
		int size = graph.size();
		int i = (ThreadLocalRandom.current().nextInt(1, size)) - 1;
		graph.get(i).setKey(0);

		for (Node node : graph) {
			nodeQueue.add(node);
		}
		while (!nodeQueue.isEmpty()) {
			nodeq = nodeQueue.remove();
			if (nodeq.getParent() != null) {
				result.add(new Corridor(nodeq.getPosition(), nodeq.getParent()));
			}

			for (Position p : nodeq.getAdjacent()) {
				for (Node n : graph) {
					if (n.getPosition().equals(p)) {
						if ((nodeQueue.contains(n)) && (nodeq.getDistance(n) < n.getKey())) {
							n.setParent(nodeq.getPosition());
							n.setKey(nodeq.getDistance(n));
							aux = n;
							nodeQueue.remove(n);
							nodeQueue.add(aux);
						}
					}
				}
			}
		}
		return result;
	}

	

	
	
}
