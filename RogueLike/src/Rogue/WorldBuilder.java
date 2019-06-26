package Rogue;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;


public class WorldBuilder {

	private static final int min = 8;
	private static final int max = 12;

	private int width;
	private int height;
	private int depth;
	private Tile[][][] tiles;
	private ArrayList<ArrayList<Room>> room_lists;
	private ArrayList<ArrayList<Corridor>> corridors_list;
	private ArrayList<Staircase> stairs_list;
	private Position exit;
	
	
	public WorldBuilder(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.tiles = new Tile[width][height][depth];
		this.room_lists = new ArrayList<ArrayList<Room>>();
		this.corridors_list = new ArrayList<ArrayList<Corridor>>();
		this.stairs_list = new ArrayList<Staircase>();
	}

	public World build() {
		return new World(tiles, stairs_list, exit);
	}

	public WorldBuilder generateRooms()  {
		int old_size, new_size;
		ArrayList<Node> graph;
		ArrayList<Corridor> corridors = new ArrayList<>();
		ArrayList<Room> room_list;
		ArrayList<Position> centersList= new ArrayList<>();
		
		//TODO Comentar con java doc
		
		for (int depth = 0; depth< this.depth; depth++){
			//Generamos las habitaciones y obtenemos una lista de sus centros
			room_list = roomGenerator(depth);
			old_size=room_list.size();
			centersList.clear();
			for (Room room : room_list) {
				centersList.add(room.getCenter());
			}
			// Pasamos la lista de habitaciones a Delaunay para obtener un grafo
			graph = delaunay(room_list, centersList);
			//Eliminamos las habitaciones que sobren y volvemos a obtener la lista de centros
			room_list = removeExpendableRooms(graph, room_list, centersList);
			new_size = room_list.size();
			if (new_size<old_size){
				centersList.clear();
				for (Room room : room_list) {
					centersList.add(room.getCenter());
				}
			}
			// Pasamos el grafo a Prim para obtener el arbol míninmo, obtenemos los
			// pasillos
			corridors = minSpanningTreePrim(graph);
			// Pasamos lista de pasillos y de habitaciones funcion de dibujado
			this.corridors_list.add(corridors);
			this.room_lists.add(room_list);
			printDungeon(room_list, corridors);
		}
		
		return this;
	}
	
	private ArrayList<Room> roomGenerator(int depth){
		boolean first = true;
		boolean cond;
		int x, y;
		int z = depth;
		int max_incrx, max_incry;
		int min_incrx, min_incry;
		int incrx = 1, incry = 1;
		Position bottomLeft;
		Position topRight;
		Room room;
		int randumRoomNum = ThreadLocalRandom.current().nextInt(min, max);
		ArrayList<Room> result = new ArrayList<>();
		
		for (int i = 0; i < randumRoomNum; i++) {

			x = ThreadLocalRandom.current().nextInt(0, width - 1);
			y = ThreadLocalRandom.current().nextInt(0, height - 1);
			bottomLeft = new Position(x, y, z);
			max_incrx = Math.min(15, (width - 1) - x);
			max_incry = Math.min(15, (height - 1) - y);

			if (max_incrx == 15) {
				min_incrx = 10;
			} else {
				min_incrx = 1;
			}
			if (max_incry == 15) {
				min_incry = 10;
			} else {
				min_incry = 1;
			}

			// Se controlan los problemas con la funcion usada para generar
			// numeros random
			if (max_incrx == min_incrx) {
				incrx = x + max_incrx;
			} else {
				incrx = x + (ThreadLocalRandom.current().nextInt(min_incrx, max_incrx));
			}
			if (max_incry == min_incry) {
				incry = y + max_incry;  
			} else {
				incry = y + (ThreadLocalRandom.current().nextInt(min_incry, max_incry));
			}

			topRight = new Position(incrx, incry,z);
			room = new Room(bottomLeft, topRight, i);
			
			if (first) {
				result.add(room);
				first = !first;
			} else {
				cond = false;
				for (Room roomItem : result) {
					if (room.disjointRooms(roomItem)) {
						cond = true;
					} else {
						cond = false;
						i--;
						break;
					}

					
				}
				if (cond) {
					result.add(room);
				}
			}
		}
		
		return result;
	}

	private ArrayList<Node> delaunay(ArrayList<Room> room_list, ArrayList<Position> centers) {
		ArrayList<Node> nodeResults = new ArrayList<>();
		ArrayList<Position> adj;
		ArrayList<Position> centersList = centers;
		int centersSize = centersList.size();
		Double dist, radio;
		boolean checkA, checkB, checkC, checkL, cont;
		Position pa, pb, pc, pd, center;
		String[] elementos = new String[centersSize];
		char[] digitos = new char[3];
		
		for (int i = 0; i < centersSize; i++) {
			elementos[i] = Integer.toString(i);
		}
		ArrayList<String> lista_posiciones = new ArrayList<>();

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

	private ArrayList<Room> removeExpendableRooms(ArrayList<Node> graph, ArrayList<Room> room_list, ArrayList<Position> centers) {
		boolean delete;
		
		for (Position p : centers) {
			delete = true;
			for (Node n : graph) {
				if ((n.getPosition().equals(p))) {
					delete = false;
				}
			}
			if (delete) {
				for(Room r : room_list){ 
					if (r.getCenter().equals(p)){
						room_list.remove(r);
						break;
					}
				}
				
			}
		}
		return room_list;
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
	
	static private boolean isNaN(double n) {
	    return (n != n);
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

	
	private ArrayList<Corridor> minSpanningTreePrim(ArrayList<Node> graph) {
		ArrayList<Corridor> result = new ArrayList<>();
		double inf = Double.POSITIVE_INFINITY;
		Node aux;
		
		for (Node node : graph) {
			node.setParent(null);
			node.setKey(inf);
		}
		int size = graph.size();
		if (size==1){
			boolean caca = true;
		}
		int i = (ThreadLocalRandom.current().nextInt(1, size)) - 1;
		graph.get(i).setKey(0);

		PriorityQueue<Node> nodeQueue = new PriorityQueue<>(nodeKeyComparator);
		for (Node node : graph) {
			nodeQueue.add(node);
		}
		while (!nodeQueue.isEmpty()) {
			Node nodeq = nodeQueue.remove();
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

	
	//Pequeña funcion que busque habitacion segun su centro
	private Room getRoomByCenter(ArrayList<Room> list, Position center){
		for (Room room :  list){
			if (room.getCenter().equals(center)){
				return room;
			}
		}
		return null; 
	}
	
	private void printDungeon(ArrayList<Room> roomList, ArrayList<Corridor> corridorList) { 
		ArrayList<Position> positions;
		Position beginning, ending, middle;
		Room room1, room2;
		int drawcase;
		int z;
	
		z = roomList.get(0).getCenter().getZ();
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y][z] = Tile.WALL;
			}
		}

		for (Room room : roomList) {
			positions = room.getFloor();
			for (Position p : positions) {
				int x = p.getIntX();
				int y = (height - 1) - p.getIntY();
				int z_i = p.getZ(); 
				tiles[x][y][z_i] = Tile.FLOOR; 
			}
		}
		
		
		for (Corridor corridor : corridorList) {
			beginning = corridor.getBeginning();
			ending = corridor.getEnd();
			middle = ending.getMiddlePoint(beginning);
			
			room1 = getRoomByCenter(roomList, beginning);
			room2 = getRoomByCenter(roomList, ending);
			
			drawcase = isPointInRange(room1, room2, middle);
			
			switch (drawcase) {
			case 0:
				drawOneLine(drawcase, room1, room2);
				break;
			case 1:
				drawOneLine(drawcase, room1, room2);
				break;
			case 2:
				drawComplex(beginning, ending, room1, room2);
				break;

			}
		}
	}

	private int  isPointInRange(Room room1, Room room2, Position middle) {
		Position left1 = room1.getRoomUpperLeft();
		Position left2 = room2.getRoomUpperLeft(); 
		Position right1 = room1.getRoomBottomRight();
		Position right2 = room2.getRoomBottomRight(); 
		
		double maxy = Math.min(left1.getIntY(), left2.getIntY()); 
		double miny = Math.max(right1.getIntY(), right2.getIntY());
		
		double minx = Math.max(left1.getIntX(), left2.getIntX());
		double maxx = Math.min(right1.getIntX(), right2.getIntX());
		
		
		if (minx<=maxx){
			return 0;
		} else if ((miny<=maxy)){
			return 1;
		} else {
			return 2;
		}
	}
	
	
	
	private void drawOneLine(int cond, Room room1, Room room2) {
		int beginningx, beginningy, endingx, endingy;
		int i, stop;
		Position left1 = room1.getRoomUpperLeft(); 
		Position left2 = room2.getRoomUpperLeft();
		Position right1 = room1.getRoomBottomRight();
		Position right2 = room2.getRoomBottomRight();
		Position center1 = room1.getCenter();
		Position center2 = room2.getCenter();
		int z = left1.getZ();
		int maxy = Math.min(left1.getIntY(), left2.getIntY()); 
		int miny = Math.max(right1.getIntY(), right2.getIntY());
		
		int minx = Math.max(left1.getIntX(), left2.getIntX());
		int maxx = Math.min(right1.getIntX(), right2.getIntX());
		
		switch (cond) {
		case 0: // en caso de que coincidan en x
			beginningx = (int) (minx+maxx)/2; //
			beginningy = (height - 1) - Math.min(center1.getIntY(), center2.getIntY());
			endingy = (height -1 ) - Math.max(center1.getIntY(), center2.getIntY());
			stop = Math.max(beginningy, endingy);
			for (i = Math.min(beginningy, endingy); i < stop; i++) {
				tiles[beginningx][i][z] = Tile.FLOOR;
			}
			break;
		case 1: // en caso de que coincidan en y
			beginningy = (height -1 )- ((miny+maxy)/2);
			beginningx = Math.min(center1.getIntX(), center2.getIntX());
			endingx = Math.max(center1.getIntX(), center2.getIntX());
			stop = Math.max(beginningx, endingx);
			for (i = Math.min(beginningx, endingx); i < stop; i++) {
				tiles[i][beginningy][z] = Tile.FLOOR;
			}
			break;
		}

	}
	

	
	
	private void drawComplex(Position beginning, Position ending, Room room1, Room room2) {
		Position middle;
		int middlex, middley, beginningx, beginningy, endingx, endingy;
		ArrayList<Position> corners1 = new ArrayList<>();
		ArrayList<Position> corners2 = new ArrayList<>();
		//double min_distance = 1000000000;
		double min_distance = Double.POSITIVE_INFINITY ;
		double corner_distance;
		int z = beginning.getZ();
		
		// Esquinas de room1
		corners1.add(room1.getRoomUpperLeft());
		corners1.add(room1.getRoomBottomLeft());
		corners1.add(room1.getRoomUpperRight());
		corners1.add(room1.getRoomBottomRight());

		// Esquinas de room2
		corners2.add(room2.getRoomUpperLeft());
		corners2.add(room2.getRoomBottomLeft());
		corners2.add(room2.getRoomUpperRight());
		corners2.add(room2.getRoomBottomRight());
		
		middle = ending.getMiddlePoint(beginning);
		middlex = middle.getIntX();
		middley = middle.getIntY();
		
		beginningx = 0;
		beginningy = 0;
		endingx = 0;
		endingy = 0;
				
		// Esquina mas cercana de la room1
		for (Position p1 : corners1) {
			corner_distance = p1.distance(middle);
			if (corner_distance <= min_distance) {
				beginningx = p1.getIntX();
				beginningy = (height - 1) - p1.getIntY();
				min_distance = corner_distance;
			}
		}

		min_distance =  Double.POSITIVE_INFINITY;
		// Esquina mas cercana de la room2
		for (Position p2 : corners2) {
			corner_distance = p2.distance(middle);
			if (corner_distance <= min_distance) {
				endingx = p2.getIntX();
				endingy = (height - 1) - p2.getIntY();
				min_distance = corner_distance;
			}
		}
		
		// Incrementamos primero en Y y vamos dibujando las posiciones
		// Incrementamos en X, vamos dibujando las posiciones (desde la
		// ultima x del paso anterior)
		
		middley = (height-1)-middle.getIntY();
		tiles[middlex][middley][z] = Tile.FLOOR;
		
		if (beginningy < middley) {
			while (beginningy < middley) {
				tiles[beginningx][beginningy][z] = Tile.FLOOR;
				beginningy++;
			}
		} else {
			while (beginningy > middley) {
				tiles[beginningx][beginningy][z] = Tile.FLOOR;
				beginningy--;
			}
		}
		
		if (beginningx < middlex) {
			while (beginningx < middlex) {
				tiles[beginningx][beginningy][z] = Tile.FLOOR;
				beginningx++;
			}
		} else {
			while (beginningx > middlex) {
				tiles[beginningx][beginningy][z]= Tile.FLOOR;
				beginningx--;
			}
		}


		// Incrementamos primero en Y y vamos dibujando las posiciones
		// Incrementamos en X, vamos dibujando las posiciones (desde la
		// ultima x del paso anterior)

		if (endingy < middley) {
			while (endingy < middley) {
				tiles[endingx][endingy][z] = Tile.FLOOR;
				endingy++;
			}
		} else {
			while (endingy > middley) {
				tiles[endingx][endingy][z]= Tile.FLOOR;
				endingy--;
			}
		}
		if (endingx < middlex) {
			while (endingx < middlex) {
				tiles[endingx][endingy][z] = Tile.FLOOR;
				endingx++;
			}
		} else {
			while (endingx > middlex) {
				tiles[endingx][endingy][z]= Tile.FLOOR;
				endingx--;
			}
		}
	}
	
	
	public WorldBuilder connectRooms(){
		for (int z = 0; z < this.depth-1; z++){
			connectRoomsDown(z);
		}
		return this;
	}
	
	private void connectRoomsDown(int z){
		int len =  this.room_lists.size();
		int i = ThreadLocalRandom.current().nextInt(0, len - 1);
		int j = ThreadLocalRandom.current().nextInt(0, len - 1);
		Position beginning = this.room_lists.get(z).get(i).getRandom();
		Position ending = this.room_lists.get(z+1).get(j).getRandom();
		Staircase stair = new Staircase(beginning, ending);
		this.stairs_list.add(stair);
		tiles[beginning.getIntX()][(height - 1)-beginning.getIntY()][beginning.getZ()] = Tile.STAIRS_DOWN;
		tiles[ending.getIntX()][(height -1 )-ending.getIntY()][ending.getZ()] = Tile.STAIRS_UP;
	}


	private WorldBuilder addExitStairs() {
        int len = this.room_lists.size();
        while(true){
        	int i = ThreadLocalRandom.current().nextInt(0, len - 1);
    		Position exit = this.room_lists.get(0).get(i).getRandom();
    		tiles[exit.getIntX()][exit.getIntY()][0].equals(Tile.STAIRS_DOWN);
    		if (!tiles[exit.getIntX()][(height - 1)-exit.getIntY()][0].equals(Tile.STAIRS_DOWN)){
    			this.exit = exit;
    			tiles[exit.getIntX()][(height - 1)-exit.getIntY()][0]= Tile.STAIRS_UP;
    			break;
    		}
        }
        return this;
    }
	
	public WorldBuilder makeCaves()  {
		return generateRooms().connectRooms().addExitStairs();
	}

}
