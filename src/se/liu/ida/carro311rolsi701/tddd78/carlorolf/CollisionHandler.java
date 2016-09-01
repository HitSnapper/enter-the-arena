package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The CollisionHandler decides what's going to happen when objects collide.
 */
public class CollisionHandler {
    private List<ArenaObject> objects;
    private List<Weapon> weapons;
    private List<ArenaObject> removeObjectsList;
    private Arena arena;
    private Graph standardEnemyGraph;
    private Graph collisionGraph;

    public CollisionHandler() {
        weapons = new ArrayList<>();
        removeObjectsList = new ArrayList<>();
        objects = new ArrayList<>();
    }

    public void addArena(Arena arena) {
        this.arena = arena;
        objects = arena.getObjects();
    }

    public void update() {
        // Collision between ArenaObjects and Weapons
        List<ArenaObject> tempObjects = new ArrayList<>(objects);
        List<Weapon> tempWeapons = new ArrayList<>(weapons);
        for (ArenaObject arenaObject : tempObjects) {
            for (Weapon weapon : tempWeapons) {
                if (!weapon.getOwner().equals(arenaObject)) {
                    handleWeaponCollision(weapon, arenaObject);
                }
            }
        }
        weapons.clear();

        // Checking collision between ArenaObjects
        for (ArenaObject obj1 : tempObjects) {
            for (ArenaObject obj2 : tempObjects) {
                if (!obj1.equals(obj2)) {
                    // Checking that the distance between the objects isn't to great.
                    if (obj1.getWidth()/2 + obj2.getWidth()/2 >= Math.abs(obj1.getX() - obj2.getX()) && obj1.getHeight()/2 + obj2.getHeight()/2 >= Math.abs(obj1.getY() - obj2.getY())) {
                        handleCollision(obj1.getBody(), obj2.getBody());
                    }
                }
            }
        }
    }

    public void removeObject(ArenaObject object) {
        removeObjectsList.add(object);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    private void handleWeaponCollision(Weapon weapon, ArenaObject arenaObject) {
        if (collisionWeaponObject(weapon, arenaObject)) {
            arenaObject.weaponCollision(weapon);
        }
    }

    private boolean collisionWeaponObject(Weapon weapon, ArenaObject arenaObject) {
        Body wBody = new Body(new Vector(weapon.getX(), weapon.getY()), weapon.getShape(), false);
        return hasCollision(wBody, arenaObject.getBody());
    }

    private Vector getLeftMost(List<Vector> vectors){
        Vector leftmost = vectors.get(0);
        for (Vector node : vectors) {
            if (node.getX() < leftmost.getX()) {
                leftmost = node;
            }
            else if (node.getX() == leftmost.getX() && node.getY() >= leftmost.getY()){
                leftmost = node;
            }
        }
        return leftmost;
    }

    private boolean oneSideEmpty(Vector v1, Vector v2, List<Vector> vectors){
        boolean onLeft = false;
        boolean onRight = true;
        for (Vector vector : vectors) {
            if (((v2.getX() - v1.getX())*(vector.getY() - v1.getY()) - (v2.getY() - v1.getY())*(vector.getX() - v1.getX())) > 0){
                onLeft = true;
            }
            else if(((v2.getX() - v1.getX())*(vector.getY() - v1.getY()) - (v2.getY() - v1.getY())*(vector.getX() - v1.getX())) < 0){
                onRight = true;
            }
        }
        return onLeft != onRight;
    }

    private List<Vector> giftWrap(List<Vector> vectors){
        List<Vector> copy = new ArrayList<>(vectors);
        List<Vector> res = new ArrayList<>();
        Vector node = getLeftMost(vectors);
        res.add(node);
        Vector leftMost = getLeftMost(vectors);
        while (true){
            Vector temp = null;
            for (Vector vector : copy) {
                if (!res.contains(vector) && oneSideEmpty(node, vector, copy)){
                    if (temp == null) {
                        temp = vector;
                    }
                    else if(Math.abs(node.getDistance(vector)) > Math.abs(node.getDistance(temp))){
                        temp = vector;
                    }
                }
                else if (vector == leftMost && res.size() > 2 && oneSideEmpty(node, vector, copy)){
                    return res;
                }
            }
            if (temp == null){
                res.remove(node);
                copy.remove(node);
                node = res.get(res.size() - 1);
            }
            else {
                res.add(temp);
                node = temp;
            }
        }
    }

    private Shape minkowskiSum(Body a, Body b) {
        List<Vector> sum = new ArrayList<>();
        double aX = a.getX();
        double aY = a.getY();
        double bX = b.getX();
        double bY = b.getY();
        for (Vector vector : a.getShape().getNodes()) {
            for (Vector vector1 : b.getShape().getNodes()) {
                sum.add(new Vector(aX + vector.getX() - bX - vector1.getX(), aY + vector.getY() - bY - vector1.getY()));
            }
        }
        return new Shape(giftWrap(sum));
    }

    private Vector getOriginDistance(Shape poly) {
        Vector res = null;
        for (int i = 0; i < poly.getNodes().size(); i++){
            Vector a = poly.getNodes().get(i);
            Vector b = poly.getNodes().get((i + 1) % poly.getNodes().size());
            Vector ab = b.addition(new Vector(-a.getX(), -a.getY()));
            Vector a0 = new Vector(-a.getX(), -a.getY());
            double projection = a0.dot(ab);
            double lengthSquared = ab.dot(ab);
            double distance = projection/lengthSquared;
            Vector temp = ab.times(distance).addition(a);
            if (res == null){
                res = temp;
            }
            else if(res.getDistanceToOrigin() > temp.getDistanceToOrigin()){
                res = temp;
            }
        }
        return res;
    }

    private boolean originInPolygon(List<Vector> vectors) {
        int i, j, nvert = vectors.size();
        boolean c = false;

        for (i = 0, j = nvert - 1; i < nvert; j = i++) {
            if (((vectors.get(i).getY() >= 0) != (vectors.get(j).getY() >= 0)) &&
                    (0 <= (vectors.get(j).getX() - vectors.get(i).getX()) * (0 - vectors.get(i).getY()) / (vectors.get(j).getY() - vectors.get(i).getY()) + vectors.get(i).getX())
                    ) {
                c = !c;
            }
        }

        return c;
    }

    private boolean hasCollision(Body b1, Body b2){
        Shape poly = minkowskiSum(b1, b2);
        return originInPolygon(poly.getNodes());
    }

    private List<ArenaObject> collidesWith(Body poly){
        List<ArenaObject> res = new ArrayList<>();
        List<ArenaObject> tempObjects = new ArrayList<>(objects);
        // Checking collision between ArenaObjects
        for (ArenaObject obj : tempObjects) {
            if (hasCollision(poly, obj.getBody())){
                res.add(obj);
            }
        }
        return res;
    }

    private void handleCollision(Body a1, Body a2) {
        if (a1.isMovable() || a2.isMovable()) {
            Shape poly = minkowskiSum(a1, a2);
            boolean collision = originInPolygon(poly.getNodes());
            if (collision) {
                Vector distance = getOriginDistance(poly);
                int a1movable = (a1.isMovable()) ? 1 : 0;
                int a2movable = (a2.isMovable()) ? 1 : 0;
                boolean bothMovable = a1.isMovable() && a2.isMovable();
                if (bothMovable) {
                    Vector half = distance.times(0.5);
                    a1.addCoords(half.times(-a1movable));
                    a2.addCoords(half.times(a2movable));
                } else {
                    a1.addCoords(distance.times(-a1movable));
                    a2.addCoords(distance.times(a2movable));
                }
            }
        }
    }

    public Path getPath(Vector VStart, Vector VGoal){
        Node start = new Node(VStart);
        Node goal = new Node(VGoal);
        if (standardEnemyGraph.lineOfSight(goal, start, 1) && collisionGraph.lineOfSight(goal, start)){
            Path path = new Path();
            path.add(goal.getCoords());
            return path;
        }
        //Connect start to visible nodes
        for (Node node : standardEnemyGraph.getNodes()) {
            if (standardEnemyGraph.lineOfSight(start, node, 1) && collisionGraph.lineOfSight(start, node)){
                start.addConnection(node);
            }
        }
        //Connect end to visible nodes
        for (Node node : standardEnemyGraph.getNodes()) {
            if (standardEnemyGraph.lineOfSight(goal, node, 1) && collisionGraph.lineOfSight(goal, node)){
                goal.addConnection(node);
            }
        }

        PriorityQueue<Node> frontier = new PriorityQueue();
        frontier.put(start, 0);
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> costSoFar = new HashMap<>();
        cameFrom.put(start, null);
        costSoFar.put(start, 0.0);

        while (!frontier.isEmpty()){
            Node current = frontier.get();
            if (current == goal){
                //System.out.println("FOUND IT!");
                break;
            }

            for (Node next : current.neighbours()) {
                if (next == goal) {
                    System.out.println("HEJ");
                }
                double newCost = costSoFar.get(current) + next.getCoords().getDistance(goal.getCoords());
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)){
                    System.out.println("NOT OK");
                    costSoFar.put(next, newCost);
                    double priority = newCost;// + Math.abs(goal.getX() - next.getX()) + Math.abs(goal.getY() - next.getY());
                    frontier.put(next, priority);
                    cameFrom.put(next, current);
                } else {
                    System.out.println("OK");
                }
            }
        }
        start.removeAllConnections();
        goal.removeAllConnections();
        return new Path(frontier.getObjects());

        /*
        frontier = PriorityQueue()
        frontier.put(start, 0)
        came_from = {}
        cost_so_far = {}
        came_from[start] = None
        cost_so_far[start] = 0

        while not frontier.empty():
            current = frontier.get()

            if current == goal:
                break

            for next in graph.neighbors(current):
                new_cost = cost_so_far[current] + graph.cost(current, next)
                if next not in cost_so_far or new_cost < cost_so_far[next]:
                cost_so_far[next] = new_cost
                priority = new_cost + heuristic(goal, next)
                frontier.put(next, priority)
                came_from[next] = current
        */
    }

    public void createCollisionGraph(){
        List<Node> res = new ArrayList<>();
        for (ArenaObject object : objects) {
            if (!(object instanceof Character)) {
                List<Node> bodyNodes = object.getBody().getNodes(0);
                for (Node node : bodyNodes) {
                    res.add(node);
                }
            }
        }
        collisionGraph = new Graph(res, arena);
    }

    public void createGraph(){
        createCollisionGraph();
        List<ArenaObject> temp = new ArrayList<>(objects);
        //Creating list to remove internal edges of objects
        List<Line> removeList = new ArrayList<>();
        //Drawing grid
        List<Node> res = new ArrayList<>();
        for (ArenaObject object : temp) {
            if (object instanceof Character) {
                //res.add(new Node(object.getCoords()));
            }
            else {
                List<Node> bodyNodes = object.getBody().getNodes(arena.getPlayer(0).getBody().getWidth()/2);
                for (Node node : bodyNodes) {
                    res.add(node);
                    for (Node node1 : bodyNodes) {
                        if (!node.equals(node1) && !node.neighbours().contains(node1)){
                            removeList.add(new Line(node, node1));
                        }
                    }
                }
            }
        }
        this.standardEnemyGraph = new Graph(res, arena);
        standardEnemyGraph.addLineOfSightConnections();
        standardEnemyGraph.removeCrossingEdges(removeList);
    }

    public void drawGraph(Graphics2D screen, Vector target, Dimension tileSize, double screenWidth, double screenHeight){
        if (standardEnemyGraph != null) {
            screen.setColor(Color.CYAN);
            standardEnemyGraph.draw(screen, target, tileSize, screenWidth, screenHeight);
            screen.setColor(Color.RED);
            collisionGraph.draw(screen, target, tileSize, screenWidth, screenHeight);
        }
    }

    public void clearAll() {
        //objects.clear();
        //removeObjectsList.clear();
    }
}
