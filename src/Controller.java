import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Controller {
    public static ArrayList<Landmark> links = new ArrayList<>();

    public Store storeObj = new Store();

    //Add a landmark to the list.
    public void addLandmark(Landmark newLandmark) {
        this.links.add(newLandmark);
    }

    //Display list of all the landmarks.
    public String displayNames() {
        String result = "";
        for (int i = 0; i < links.size(); i++) {
            result += links.get(i).toString() + "\n";
        }
        return result;
    }

    //Add a road to the list.
    public void addRoad(Landmark src, Landmark dest, int cost, String roadId, String roadName){
        src.connectedToNodeUndirected(dest, cost, roadId, roadName);
    }

    //Display list of all the roads
    public String displayRoads() {
        String result = "";
        try {
            String str;
            HashSet<String> road = new HashSet();

            for (Landmark rds : links) {
                    for (int i = 0; i < rds.roads.size(); i++) {
                        str = rds.roads.get(i).getRoadId() + " " + rds.roads.get(i).roadName + "\n";
                        road.add(str) ;
                    }
                }
               for (String roadStr : road) {
                   result = result + roadStr;
               }
        }
        catch(Exception e) {
        System.out.println(e);
    }
        return result;
    }

    public String displayNamesAndCost(){
        System.out.println();
        String result = "";
            for (int i = 0; i < links.size(); i++){
                result += "From " + links.get(i).landmarkName + " -> \n";
                for(int j = 0; j < links.get(i).roads.size(); j++){
                    result += "\tto " + links.get(i).roads.get(j).destNode.landmarkName;
                    result += ": " + links.get(i).roads.get(j).cost + " minutes via "
                            + links.get(i).roads.get(j).roadName + ".\n";
                }
            result +=  "\n";
        }
        return result;
    }

    public Landmark findLandmark(String landmarkName){
        for(int i = 0; i < links.size(); i++){
            if(links.get(i).landmarkId.equalsIgnoreCase(landmarkName)){
                return links.get(i);
            }
        }
        return null;
    }

    //Remove source landmark with attached edge to it from the list.
    public void remove(String landmark){
        int index = -1;
        for(int i = 0; i < links.size(); i++){
            if(links.get(i).landmarkId.equalsIgnoreCase(landmark)){
                index = i;
            }
        }
        if (index >= 0)
            this.links.remove(index);
    }

    public void store(){
        storeObj.save(links);
    }

    public void load(){
        // going to create array list and populate links array
        links = storeObj.load();
    }

    //Retrieve cheapest path by expanding all paths recursively depth-first
    public static <T> CostedPath searchGraphDepthFirstPath(Landmark from, ArrayList<Landmark> encountered,
                                                                   int totalCost, T lookingfor, String minmax) {
        if (from.landmarkId.equals(lookingfor)) { //Found it - end of path
            CostedPath cp = new CostedPath(); //Create a new CostedPath object
            cp.pathList.add(from); //Add the current node to it - only (end of path) element
            cp.pathCost = totalCost; //Use the current total cost

            return cp; //Return the CostedPath object
        }
        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from);
        List<CostedPath> allPaths = new ArrayList<>(); //Collection for all candidate costed paths from this node

        for (Road adjLink : from.roads) //For every adjacent node
            if (!encountered.contains(adjLink.destNode)) //That has not yet been encountered
            {
                //Create a new CostedPath from this node to the searched for item (if a valid path exists)
                CostedPath temp = searchGraphDepthFirstPath(adjLink.destNode, encountered, totalCost + adjLink.cost, lookingfor, minmax);
                if (temp == null) continue; //No path was found, so continue to the next iteration
                temp.pathList.add(0, from); //Add the current node to the front of the path list
                temp.roadList.add(0, adjLink);
                allPaths.add(temp); //Add the new candidate path to the list of all costed paths
            }
        //If no paths were found then return null. Otherwise, return the cheapest path (i.e. the one with min pathCost)
        if (minmax.equals("min")) {
            return allPaths.isEmpty() ? null : Collections.min(allPaths, (p1, p2) -> p1.pathCost - p2.pathCost);
        } else {
            return allPaths.isEmpty() ? null : Collections.max(allPaths, (p1, p2) -> p1.pathCost - p2.pathCost);
        }
    }
}
