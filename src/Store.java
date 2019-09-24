import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Store {

    //Finds the file path for the saved csv and saves it again.
    private File save = new File("save.txt");

    public ArrayList<Landmark> load (){
        ArrayList<Landmark> loads = new ArrayList<>();
        try {
            File f = new File("save.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line;
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(",");

                //Searches through each type in the CSV for types called Landmark and adds them
                if (tokens[0].equalsIgnoreCase("Landmark")) {
                    String id = tokens[1].trim();
                    String name = tokens[2].trim();

                    Landmark newLandmark = new Landmark(id, name);
                    loads.add(newLandmark);
                }
                //Searches through each type in the CSV for types called Road and adds them
                else if (tokens[0].equalsIgnoreCase("Road")) {
                    String id = tokens[1].trim();
                    String src = tokens[2].trim();
                    String dest = tokens[3].trim();
                    int cost = Integer.parseInt(tokens[4].trim());
                    String roadName = tokens[5].trim();

                    Landmark srcLand = null,destLand = null;
                    //int counter = 0;

                    for (int i = 0; i < loads.size(); i++) {
                        if (loads.get(i).landmarkName.equalsIgnoreCase(src)) {
                            srcLand = loads.get(i);
                            // counter +=1;
                        }
                        if (loads.get(i).landmarkName.equalsIgnoreCase(dest)) {
                            destLand = loads.get(i);
                            //counter +=1;
                        }
                    }
                    if (srcLand != null && destLand != null) {
                        srcLand.connectToNodeDirected(destLand, cost, id, roadName);
                    }
                }
            }
            } catch(Exception e){
                System.out.println(e);
        }
        return loads;
    }

    public void save(ArrayList<Landmark> links){
        try{
            //Calls the new FileWriter false that the file will be overwritten each time
            FileWriter fw = new FileWriter(save.getAbsoluteFile(), false);
            String str = "";

            HashSet<String> landmarksForFile = new HashSet();
            HashSet<String> roadsForFile = new HashSet();

            //Go through each element in ArrayList
            for(Landmark locs : links){
                landmarksForFile.add("Landmark," + locs.landmarkId+ "," + locs.landmarkName + "\n");
                for (int i = 0; i < locs.roads.size(); i++){

                    String road = "Road," + locs.roads.get(i).getRoadId() + "," + locs.landmarkName + ","
                            + locs.roads.get(i).nameAndCost() + "," + locs.roads.get(i).roadName;
                    road = road + "\n";
                    roadsForFile.add(road);
                }
            }
            //List of all the landmarks
            for(String landmarkStr : landmarksForFile) {
                str = str + landmarkStr;
            }
            for(String roadStr : roadsForFile) {
                str = str + roadStr;
            }
            fw.write(str);
            fw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
