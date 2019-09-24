import java.util.Scanner;


public class Main {
    private Scanner scanner;
    private Controller contr;

    public static void main(String[] args){
        new Main();
    }

    public Main(){
        scanner = new Scanner(System.in);
        contr = new Controller();
        contr.load();
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            // Clear screen
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
            System.out.println(" ======= WATERFORD CITY =======\n");
            System.out.println(" 1) Add location");
            System.out.println(" 2) Add road/street");
            System.out.println("----------");
            System.out.println(" 3) List all locations");
            System.out.println(" 4) List all roads/streets");
            System.out.println(" 5) List of all paths");
            System.out.println("----------");
            System.out.println(" 6) Find path");
            System.out.println(" 7) Remove location");
            System.out.println(" 8) Save");
            System.out.println(" 0) Exit");
            System.out.println(" ==>> ");

            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 0: //Exit
                    System.exit(0);
                    break;

                case 1: //Add location
                    System.out.println("Please enter the following location details...");
                    System.out.println("\tEnter landmark ID (000): ");
                    System.out.print("> ");
                    String landmarkId = scanner.nextLine();
                    System.out.println("\tEnter landmark name: ");
                    System.out.print("> ");
                    String landmarkName = scanner.nextLine();
                    contr.addLandmark(new Landmark(landmarkId, landmarkName));
                    break;

                case 2: //Add road
                    System.out.println("Please enter the following road details...");
                    System.out.println("\tEnter road ID (R000): ");
                    System.out.print("> ");
                    String roadId = scanner.nextLine();
                    System.out.println("\tEnter street name: ");
                    System.out.print("> ");
                    String roadName = scanner.nextLine();

                    System.out.println(contr.displayNames());
                    System.out.println("\tEnter source landmark ID: ");
                    System.out.print("> ");
                    String srcLandmark = scanner.nextLine();
                    //call a method in controller to find src obj
                    Landmark source = contr.findLandmark(srcLandmark);

                    System.out.println(contr.displayNames());
                    System.out.println("\tEnter destination landmark ID: ");
                    System.out.print("> ");
                    String destLandmark = scanner.nextLine();
                    Landmark dest = contr.findLandmark(destLandmark);

                    System.out.println("\tEnter cost: ");
                    System.out.print("> ");
                    int cost = Integer.parseInt(scanner.nextLine());
                    contr.addRoad(source, dest, cost, roadId, roadName);
                    break;

                case 3: //List locations
                    System.out.println("\n\n\nLIST OF ALL LOCATIONS =>>");
                    System.out.println(contr.displayNames());
                    break;

                case 4: //List roads
                    System.out.println("\n\n\nLIST OF ALL ROADS/STREETS =>>");
                    System.out.println(contr.displayRoads());
                    break;

                case 5: //List of routes
                    System.out.println("\n\n\nLIST OF ALL PATHS =>>");
                    System.out.println(contr.displayNamesAndCost());
                    break;

                case 6: //Find both shortest & longest paths
                    System.out.println("FIND PATH =>>");
                    System.out.println(contr.displayNames());
                    System.out.println("\tEnter start point: ");
                    System.out.print("> ");
                    String landmarkStart = scanner.nextLine();
                    Landmark landStartObj = contr.findLandmark(landmarkStart);

                    System.out.println(contr.displayNames());
                    System.out.println("\tEnter end point: ");
                    System.out.print("> ");
                    String landmarkEnd = scanner.nextLine();

                    System.out.print("\nPlease select: 1 for shortest timed route \n\t\t\t   2 for longest timed route  ");
                    System.out.print("\n> ");
                    int sl = Integer.parseInt(scanner.nextLine());
                    CostedPath temp ;
                    try {
                        if (sl == 1) {
                            temp = Controller.searchGraphDepthFirstPath(landStartObj, null, 0, landmarkEnd, "min");
                        } else {
                            temp = Controller.searchGraphDepthFirstPath(landStartObj, null, 0, landmarkEnd, "max");
                        }
                        int i = 0;
                        int fin = temp.roadList.size() - 1;
                        System.out.print("\nFrom " + temp.pathList.get(0).landmarkName + " to " + temp.pathList.get(temp.pathList.size() - 1).landmarkName + ": " + temp.pathCost + " minutes"
//                    System.out.print("\nFrom " + landmarkStart + " to " + landmarkEnd  + ": " + temp.pathCost + " minutes"
                                + " via ");
                        for (Road road : temp.roadList) {
                            if (i <= fin) {
                            System.out.print(road.roadDetail());
//                                System.out.print(temp.roadList.get(i).roadDetail());
                                if (i <= fin - 1) {
                                    System.out.print(" and ");
                                }
                            }
                            i++;
                        }
                    }
                     catch(Exception e){
                            System.out.print("\n\nThis path does not exist or incorrect ID entered!!! \nPlease start again and choose different location or use correct ID!!!");
//                            continue;
                        }
                    break;

                case 7: //Remove
                    System.out.println(contr.displayNames());
                    System.out.print("Enter location ID to delete: ");
                    System.out.print("> ");
                    String landmarkToRemove =  scanner.nextLine();
                    contr.remove(landmarkToRemove);
                    System.out.println("Landmark deleted.");
                    break;

                case 8:
                    contr.store();
                    break;

                default: // Invalid option.
                    System.out.println("Invalid option entered: " + input);
                    break;
            }
            System.out.print("\n\nPress any key to continue... ");
            scanner.nextLine();
        }
    }
}

