package com.swarnava.ipl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    // private static List<Match> matches;

    private static final String PATH_MATCHES = "csv/matches.csv", PATH_DELIVERY = "csv/deliveries.csv", PATH_DEMO = "csv/demo.csv";
    static String next="", winner="", bowler, name;
    static int year, id, countExtraRun2016 =0, extraRun, run, salary;
    static float over, economicRate;


    private static final HashMap<String, Integer> myScenario = new HashMap<String, Integer>();//for question 5
    private static final HashMap<String, Integer> runHm = new HashMap<String, Integer>();
    private static final HashMap<String, Float> overHm = new HashMap<String, Float>();


    static boolean scanDemo() throws FileNotFoundException{
        Scanner sc = new Scanner(new File(PATH_DEMO));
        sc.useDelimiter("\n");   //sets the delimiter pattern
        sc.next(); //to eliminate heading, otherwise NumberFormatException happen
        while (sc.hasNext()) {
            next = sc.next();
            String values[] = next.split(",");

            name = values[0];
            salary = Integer.parseInt(values[3].trim()+"");

            // 5.) My scenario: list name whose salary more than 29k.
            if(salary>29000){
                if(myScenario.containsKey(name)){
                    myScenario.put(name, myScenario.get(name)+salary);
                }else{
                    myScenario.put(name, salary);
                }
            }

        }
        sc.close();
        System.out.println("\n5.) My scenario: list name whose salary more than 29k. :\n"+myScenario);

        return true;
    }



    public static void main(String[] args)   {

        // getting matches data from matches.csv file
        List<Match> matches = null;
        try {
            matches = getMatchesData();
        } catch (FileNotFoundException e) {
            System.out.println("File not found : "+ PATH_MATCHES +"\nDetails: "+e);
            e.printStackTrace();
        }

        // getting deliveries data from deliveries.csv file
        List<Delivery> deliveries = null;
        try {
            deliveries = getDeliveriesData();
        } catch (FileNotFoundException e) {
            System.out.println("File not found : "+ PATH_DELIVERY +"\nDetails: "+e);
            e.printStackTrace();
        }

        NumberOfMatchesPlayedPerYear(matches);
        NumberOfMatchesWonOfAllTeam(matches);
        getTheExtraRunsConcededPerTeamForParticularYear(matches, deliveries, 2016);
        getTheTopEconomicalBowlersForParticularYear(matches, deliveries, 2015);




        // fetching 3rd csv file and process with required data : (My scenario: demo.csv)
        try{
            scanDemo();
        }catch(FileNotFoundException e){
            System.out.println("File not found : "+ PATH_DEMO +"\nDetails: "+e);
        }
        catch(Exception e){
            System.out.println("UNKNOWN exception handled while process file-3 : \nDetails: "+e+"\n\n");
        }

    }


    private static void NumberOfMatchesPlayedPerYear(List<Match> matches){
        HashMap<Integer, Integer> noOfMatch = new HashMap<Integer, Integer>();  //for question 1
        int i, year;

        //Match obj = matches.get(0);

        System.out.println("\n\nSize = "+matches.size()+"\n");

        for (i=0; i<matches.size(); i++){
            year = matches.get(i).getSeason();

            System.out.print(","+year);


            if (noOfMatch.containsKey(year)) {
                noOfMatch.put(year, noOfMatch.get(year)+1);
            } else {
                noOfMatch.put(year, 1);
            }


        }
         System.out.println(" \n\n1.) After Collect data from matches 1st time : \n"+noOfMatch);
    }

    private static void NumberOfMatchesWonOfAllTeam(List<Match> matches){
        HashMap<String, Integer> winning = new HashMap<String, Integer>();
        String winner = "";
        // 2. Number of matches won of all teams over all the years of IPL.
        for (int i=0; i<matches.size(); i++){
            winner = matches.get(i).getWinner();
            if (winning.containsKey(winner)){
                winning.put(winner, winning.get(winner)+1);
            } else{
                winning.put(winner, 1);
            }
        }
        winning.remove(""); //remove null/blank key element
        System.out.println(
                "\n2.) Number of matches won of all teams over all the years of IPL. : \n"
                        +winning);
    }

    private static void getTheExtraRunsConcededPerTeamForParticularYear(List<Match> matches, List<Delivery> deliveries, int targetYear){
        HashMap<Integer, String> winnerList = new HashMap<Integer, String>();       //for question 3 : to collect id from matches.csv
        HashMap<String, Integer> trackExtraRun = new HashMap<String, Integer>();    //for question 3
        String winner = "";
        int countExtraRun = 0, extraRun = 0, id;

        // getting season and winner list
        for (int i=0; i<matches.size(); i++) {
            int year = matches.get(i).getSeason();
            if (year == targetYear){
                winner = matches.get(i).getWinner();
                winnerList.put(matches.get(i).getId(), winner); // if year match, then put id with winner
            }
        }

        // calculate einner list
        for (int i=0; i<deliveries.size(); i++) {
            id = deliveries.get(i).getMatchId();
            if ( winnerList.containsKey(id) ) { // is winner belong from same i
                winner = winnerList.get(id);
                extraRun = deliveries.get(i).getExtraRuns();

                if(trackExtraRun.containsKey(winner)){
                    trackExtraRun.put(winner, trackExtraRun.get(winner)+extraRun);
                }else{
                    trackExtraRun.put(winner, extraRun);
                }
                countExtraRun += extraRun;
            }
        }

        System.out.println("\n3.) For the year "+targetYear+" get the extra runs conceded per team. : \n"+trackExtraRun
                +"\n Total extra run make by all team for the year "+targetYear+" : "+ countExtraRun);
    }

    private static void getTheTopEconomicalBowlersForParticularYear(List<Match> matches, List<Delivery> deliveries, int targetYear){
        List<Integer> IdList = new LinkedList<Integer>();
        HashMap<String, ArrayList<Float>> getEconomicBowler = new HashMap<String, ArrayList<Float>>();
        String bowler="";
        float over, run;

        // getting id list
        for (int i=0; i<matches.size(); i++) {
            if (year == targetYear) {
                IdList.add(matches.get(i).getId());
            }
        }

        System.out.println("Economic Bowlers:\n"+IdList);
        // calculate einner list
        for (int i=0; i<deliveries.size(); i++) {
            id = deliveries.get(i).getMatchId();
            if ( IdList.contains(id) ) {
                bowler = deliveries.get(i).getBowler();
                over = deliveries.get(i).getOver();
                run = deliveries.get(i).getTotalRuns();

                System.out.println(over+":"+run);
                if(getEconomicBowler.containsKey(bowler)){
                    over += getEconomicBowler.get(bowler).get(0);
                    run += getEconomicBowler.get(bowler).get(1);
                    ArrayList<Float> row = new ArrayList<Float>();
                    row.add(0, over);
                    row.add(1, run);
                    getEconomicBowler.put(bowler, row);
                }else{
                    ArrayList<Float> row = new ArrayList<Float>();
                    row.add(0, over);
                    row.add(1, run);
                    getEconomicBowler.put(bowler, row);

                }
            }
        }

        float res=0;
        ArrayList<Float> arrayList = new ArrayList<Float>();
        for(String key : getEconomicBowler.keySet() ){
            res = getEconomicBowler.get(key).get(1) / getEconomicBowler.get(key).get(0);
            System.out.println(":"+res);
        }



        /*
        // 4.) For the year 2015 get the top economical bowlers.

            for(String key : runHm.keySet()) {
                run = runHm.get(key);
                over = overHm.get(key);
                economicRate = run / over;

                if(economicBowler.containsKey(key)){
                    economicBowler.put(key, economicBowler.get(key)+ economicRate);
                }else{
                    economicBowler.put(key, economicRate);
                }
            }
         */

        System.out.println("\n4.) For the year 2015 get the top economical bowlers. :\n"+getEconomicBowler);

    }

    static List<Match> getMatchesData() throws FileNotFoundException {
        List<Match> matchList = new ArrayList<Match>();
        Match object;
        Scanner sc = new Scanner(new File(PATH_MATCHES));
        sc.useDelimiter("\n");   //sets the delimiter pattern to get line wise
        sc.next();//to eliminate heading, otherwise NumberFormatException happen
        while (sc.hasNext()) {
            next = sc.next();
            String values[] = next.split(","); // splitting value from line
            object = null;
            object = new Match();

            object.setId(Integer.parseInt(values[0]));
            object.setSeason(Integer.parseInt(values[1]));
            object.setCity(values[2]);
            object.setDate(values[3]);
            object.setTeam1(values[4]);
            object.setTeam2(values[5]);
            object.setTossWinner(values[6]);
            object.setTossDecision(values[7]);
            object.setResult(values[8]);
            object.setDlApplied(Integer.parseInt(values[9]));
            object.setWinner(values[10]);
            object.setWinByRuns(Integer.parseInt(values[11]));
            object.setWinByWickets(Integer.parseInt(values[12]));
            object.setPlayerOfMatch(values[13]);
            object.setVenue(values[14]);
            object.setUmpire1(values[15]);
            object.setUmpire2(values[16]);
            object.setUmpire3(values[17]);

            matchList.add(object);
        }
        sc.close();
        return matchList;
    }

    static List<Delivery> getDeliveriesData() throws FileNotFoundException {
        List<Delivery> deliveryList = new ArrayList<Delivery>();
        Delivery object;
        Scanner sc = new Scanner(new File(PATH_DELIVERY));
        sc.useDelimiter("\n");   //sets the delimiter pattern to get line wise
        sc.next(); //to eliminate heading, otherwise NumberFormatException happen
        while (sc.hasNext()) {
            next = sc.next();
            String values[] = next.split(","); // splitting value from line
            object = null;
            object = new Delivery();

            object.setMatchId(Integer.parseInt(values[0]));
            object.setInning(Integer.parseInt(values[1]));
            object.setBattingTeam(values[2]);
            object.setBowlingTeam(values[3]);
            object.setOver(Integer.parseInt(values[4]));
            object.setBall(Integer.parseInt(values[5]));
            object.setBatsMan(values[6]);
            object.setNonStriker(values[7]);
            object.setBowler(values[8]);
            object.setIsSuperOver(Integer.parseInt(values[9]));
            object.setWideRuns(Integer.parseInt(values[10]));
            object.setByeRuns(Integer.parseInt(values[11]));
            object.setLegByeRuns(Integer.parseInt(values[12]));
            object.setNoBallRuns(Integer.parseInt(values[13]));
            object.setPenaltyRuns(Integer.parseInt(values[14]));
            object.setBatsmanRuns(Integer.parseInt(values[15]));
            object.setExtraRuns(Integer.parseInt(values[16]));
            object.setTotalRuns(Integer.parseInt(values[17]));
            object.setPlayerDismissed(values[18]);
            object.setDismissalKind(values[19]);
            object.setFielder(values[20]);

            deliveryList.add(object);
        }
        sc.close();
        return deliveryList;
    }
}

