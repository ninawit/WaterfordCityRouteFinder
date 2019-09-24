import java.util.ArrayList;

public class Landmark {
    public String landmarkId, landmarkName;

    //Store list of roads connected to landmarks
    public  ArrayList<Road> roads = new ArrayList<>();

    public void setLandmarkName(String name){
        this.landmarkName = name;
    }

    public Landmark(String landmarkId, String name){
        this.landmarkId = landmarkId;
        setLandmarkName(name);
    }

    public void connectToNodeDirected(Landmark destNode, int cost, String roadId, String roadName){
        roads.add(new Road(destNode, cost, roadId, roadName)); //Add new edge object to source adjacency list.
    }

    public void connectedToNodeUndirected(Landmark destNode, int cost, String roadId, String roadName){
        roads.add(new Road(destNode, cost, roadId, roadName)); // Add new edge object to source adjacency list.
        destNode.roads.add(new Road(this, cost, roadId, roadName )); //Add new edge object to destination adjacency list
    }

    @Override
    public String toString(){
        return landmarkId + "\t" + landmarkName;
    }
}

