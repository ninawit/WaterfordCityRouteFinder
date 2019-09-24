
public class Road {
    public Landmark destNode;
    public String roadId;
    public String roadName;
    public int cost;

    public Road(Landmark destNode, int cost, String roadId, String roadName){
        this.destNode = destNode;
        this.cost = cost;
        this.roadId = roadId;
        this.roadName = roadName;
    }

    public Road(){

    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public Road(int cost){
        setCost(cost);
    }

    public Road(String id, String name){
        this.roadId = id;
        this.roadName = name;
    }

    public String getRoadId(){
        return roadId;
    }

    public String nameAndCost(){
        return  destNode.landmarkName + ", " + cost;
    }

    @Override
    public String toString(){
        return roadId + "\t" + roadName;
    }

    public String roadDetail(){
        return roadName + " (" + cost + " mins)";
    }
}
